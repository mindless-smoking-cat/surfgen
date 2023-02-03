package generators;

/*
Copyright (c) 2009, Nerius Landys
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution. 
3. The name of the author may not be used to endorse or promote products
derived from this software without specific prior written permission. 

THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

// modified by thelionroars (2012) to write as common/caulk instead of radiant/notex

import com.nerius.math.geom.Point3D;
import com.nerius.math.xform.AffineTransform3D;
import com.nerius.math.xform.AxisRotation3D;
import com.nerius.math.xform.Translation3D;
import tools.MapFactory;

/**
 * @version $Revision: 1.8 $
 */
class GenerationUtils {

    public final static int SHINGLES_TYPE_CYL = 0; // Should treat as default.
    public final static int SHINGLES_TYPE_RAMP_SQTOP = 1;
    public final static int SHINGLES_TYPE_RAMP_TRITOP = 2;
    final static int ADJUST_FOR_OVERLAPPING_BOWL_CUTS = 2;
    final static int ADJUST_FOR_OVERLAPPING_PIPE_CUTS = 1;
    final static int ADJUST_NONE = 0;
    private static int FUDGE_DIVISIONS_QUARTER = 1000000;
    private static double FUDGE_ANGLE_INCR = (Math.PI / 2) / FUDGE_DIVISIONS_QUARTER;
    private static int brushCount = 0;

    static Point3D[] generatePipeCurve(final double radius,
                                       final int slices,
                                       final int extrudeTopNSlices,
                                       final double extrudeRadiusFactor,
                                       final int adjustMask) {
        // The calculations might
        // cause really bad vertex positions if there are not enough
        // slope slices.  If there are too many slope slices bad things will
        // happen too.  I didn't put that error checking into the code because
        // it takes a lot of checking to catch all errors.  extrudeRadiusFactor
        // should be greater than or equal to 1.
        final Point3D pt0 = new Point3D(radius,
                0,
                0);
        final Point3D[] curve = new Point3D[slices + 1];
        for (int i = 0; i <= slices; i++) {
            final AxisRotation3D rot =
                    new AxisRotation3D(AxisRotation3D.Y_AXIS,
                            (Math.PI * i) / (2 * slices));
            curve[i] = rot.transform(pt0);
        }
        Point3D extrudePt0 = null;
        Point3D centerExtrude = null;
        double radiansExtrudeStep = 0.0d;
        if (extrudeTopNSlices > 0) {
            extrudePt0 = curve[extrudeTopNSlices];
            centerExtrude =
                    new Point3D(extrudePt0.x - extrudeRadiusFactor * extrudePt0.x,
                            extrudePt0.y,
                            extrudePt0.z - extrudeRadiusFactor * extrudePt0.z);
            double radiansToRim = 0.0d;
            {
                double alpha =
                        (Math.PI * extrudeTopNSlices) / (2 * slices);
                double c = radius * extrudeRadiusFactor;
                double b = extrudePt0.z - extrudeRadiusFactor * extrudePt0.z;
                double gamma = Math.asin(b / c);
                radiansToRim = alpha - gamma;
            }
            radiansExtrudeStep = radiansToRim / extrudeTopNSlices;
            for (int i = extrudeTopNSlices - 1; i >= 0; i--) {
                final double radians = radiansExtrudeStep * (extrudeTopNSlices - i);
                AffineTransform3D xform =
                        new Translation3D(-centerExtrude.x, 0, -centerExtrude.z);
                xform = xform.concatenatePost(new AxisRotation3D(AxisRotation3D.Y_AXIS, -radians));
                xform = xform.concatenatePost(new Translation3D(centerExtrude.x, 0, centerExtrude.z));
                curve[i] = xform.transform(extrudePt0);
            }
        }
        if (((adjustMask & 1) != 0) || ((adjustMask & 2) != 0)) {
            int adjusted = 0;
            // TODO: Get rid of while loop in favor of direct calculation.
            while (true) {
                final int z1 = (int) Math.round(curve[1].z);
                final int z2 = (int) Math.round(curve[2].z);
                if (0 - z1 < z1 - z2) {
                    adjusted++;
                    AffineTransform3D xform;
                    final Point3D pt;
                    if (1 < extrudeTopNSlices) {
                        xform = new Translation3D(-centerExtrude.x, 0, -centerExtrude.z);
                        xform = xform.concatenatePost(new AxisRotation3D(AxisRotation3D.Y_AXIS,
                                adjusted * FUDGE_ANGLE_INCR
                                        - radiansExtrudeStep * (extrudeTopNSlices - 1)));
                        xform = xform.concatenatePost(new Translation3D(centerExtrude.x, 0, centerExtrude.z));
                        pt = extrudePt0;
                    } else {
                        xform = new AxisRotation3D(AxisRotation3D.Y_AXIS,
                                (Math.PI * 1) / (2 * slices) + FUDGE_ANGLE_INCR * adjusted);
                        pt = pt0;
                    }
                    curve[1] = xform.transform(pt);
                } else {
                    break;
                }
            }
            if (adjusted > 0) {
                System.err.println("WARNING: Adjusted vertex second from top by moving it down\n"
                        + "  " + adjusted + "/" + FUDGE_DIVISIONS_QUARTER
                        + " of a quarter circle.");
            }
        }
        if ((adjustMask & 2) != 0) {
            int adjusted = 0;
            // TODO: Get rid of while loop in favor of direct calculation.
            // This can overshoot the target and cause an infinite loop.
            while (true) {
                final int z1 = (int) Math.round(curve[1].z);
                final int z2 = (int) Math.round(curve[2].z);
                if (0 - z1 != z1 - z2) {
                    adjusted++;
                    AffineTransform3D xform;
                    final Point3D pt;
                    if (2 < extrudeTopNSlices) {
                        xform = new Translation3D(-centerExtrude.x, 0, -centerExtrude.z);
                        xform = xform.concatenatePost(new AxisRotation3D(AxisRotation3D.Y_AXIS,
                                adjusted * FUDGE_ANGLE_INCR
                                        - radiansExtrudeStep * (extrudeTopNSlices - 2)));
                        xform = xform.concatenatePost(new Translation3D(centerExtrude.x, 0, centerExtrude.z));
                        pt = extrudePt0;
                    } else {
                        xform = new AxisRotation3D(AxisRotation3D.Y_AXIS,
                                (Math.PI * 2) / (2 * slices) + FUDGE_ANGLE_INCR * adjusted);
                        pt = pt0;
                    }
                    curve[2] = xform.transform(pt);
                } else {
                    break;
                }
            }
            if (adjusted > 0) {
                System.err.println("WARNING: Adjusted vertex third from top by moving it down\n"
                        + "  " + adjusted + "/" + FUDGE_DIVISIONS_QUARTER
                        + " of a quarter circle.");
            }
            adjusted = 0;
            // TODO: Get rid of while loop if possible.
            while (true) {
                final int z2 = (int) Math.round(curve[2].z);
                final int z3 = (int) Math.round(curve[3].z);
                if ((z2 - z3) * 2 > 0 - z2) {
                    adjusted++;
                    AffineTransform3D xform;
                    final Point3D pt;
                    if (3 < extrudeTopNSlices) {
                        xform = new Translation3D(-centerExtrude.x, 0, -centerExtrude.z);
                        xform = xform.concatenatePost(new AxisRotation3D(AxisRotation3D.Y_AXIS,
                                adjusted * FUDGE_ANGLE_INCR
                                        - radiansExtrudeStep * (extrudeTopNSlices - 3)));
                        xform = xform.concatenatePost(new Translation3D(centerExtrude.x, 0, centerExtrude.z));
                        pt = extrudePt0;
                    } else {
                        xform = new AxisRotation3D(AxisRotation3D.Y_AXIS,
                                (Math.PI * 3) / (2 * slices)
                                        + FUDGE_ANGLE_INCR * adjusted);
                        pt = pt0;
                    }
                    curve[3] = xform.transform(pt0);
                } else {
                    break;
                }
            }
            if (adjusted > 0) {
                System.err.println("WARNING: Adjusted vertex fourth from top by moving it down\n"
                        + "  " + adjusted + "/" + FUDGE_DIVISIONS_QUARTER
                        + " of a quarter circle.");
            }
        }
        if ((adjustMask & 1) != 0) {
            int adjusted = 0;
            final int inx = slices - 1;
            // TODO: Get rid of while loop if possible.
            while (true) {
                final int x1 = (int) Math.round(curve[inx].x);
                final int x2 = (int) Math.round(curve[inx - 1].x);
                if (x1 - 0 < x2 - x1) {
                    adjusted++;
                    AffineTransform3D xform;
                    final Point3D pt;
                    if (inx <= extrudeTopNSlices) {
                        xform = new Translation3D(-centerExtrude.x, 0, -centerExtrude.z);
                        xform = xform.concatenatePost(new AxisRotation3D(AxisRotation3D.Y_AXIS,
                                -(adjusted * FUDGE_ANGLE_INCR)
                                        - radiansExtrudeStep * (extrudeTopNSlices - inx)));
                        xform = xform.concatenatePost(new Translation3D(centerExtrude.x, 0, centerExtrude.z));
                        pt = extrudePt0;
                    } else {
                        xform = new AxisRotation3D(AxisRotation3D.Y_AXIS,
                                (Math.PI * inx) / (2 * slices) - FUDGE_ANGLE_INCR * adjusted);
                        pt = pt0;
                    }
                    curve[inx] = xform.transform(pt);
                } else {
                    break;
                }
            }
            if (adjusted > 0) {
                System.err.println("WARNING: Adjusted vertex second from bottom by moving it up\n"
                        + "  " + adjusted + "/" + FUDGE_DIVISIONS_QUARTER
                        + " of a quarter circle.");
            }

        }
        return curve;
    }

    static void startMap() {
        brushCount = 0;
        MapFactory.newText();
        MapFactory.add("//GENERATED");
        MapFactory.addLine("{");
        MapFactory.addLine("\"classname\" \"worldspawn\"");
    }

    static void startGroup() {
        MapFactory.addLine("{");
        MapFactory.addLine("\"classname\" \"func_group\"");
    }

    static void endGroup() {
        MapFactory.addLine("}");
    }

    static void endMap() {
        MapFactory.addLine("}");
        System.err.println("Wrote " + brushCount + " brushes.");
        Shutdown.abort();
    }

    static void startBrush() {
        brushCount++;
        MapFactory.addLine("{");
    }

    static void endBrush() {
        MapFactory.addLine("}");
    }

    static void writeFace(final Point3D pt1,
                          final Point3D pt2,
                          final Point3D pt3) {
        MapFactory.addLine("");
        writePoint(pt1);
        MapFactory.add(" ");
        writePoint(pt2);
        MapFactory.add(" ");
        writePoint(pt3);
        MapFactory.add(" common/caulk 0 0 0 0.500000 0.500000 0 0 0");
    }

    static void writeFace(final Point3D pt1,
                          final Point3D pt2,
                          final Point3D pt3,
                          final String texture) {
        MapFactory.addLine("");
        writePoint(pt1);
        MapFactory.add(" ");
        writePoint(pt2);
        MapFactory.add(" ");
        writePoint(pt3);
        MapFactory.add(" common/caulk 0 0 0 0.500000 0.500000 0 0 0");
    }

    private static void writePoint(final Point3D pt) {
        MapFactory.add("( " + Math.round(pt.x) + " " + Math.round(pt.y) + " " + Math.round(pt.z) + " )");
    }

    // Writes a triangular prism, with front face specified and projected onto
    // the plane specified by constant x.
    static void writeBrushExtendX(int x,
                                  Point3D pt1,
                                  Point3D pt2,
                                  Point3D pt3,
                                  boolean extrude) {
        if (extrude) {
            pt1 = new Point3D(Math.round(pt1.x),
                    Math.round(pt1.y),
                    Math.round(pt1.z));
            pt2 = new Point3D(Math.round(pt2.x),
                    Math.round(pt2.y),
                    Math.round(pt2.z));
            pt3 = new Point3D(Math.round(pt3.x),
                    Math.round(pt3.y),
                    Math.round(pt3.z));
            Point3D newPt1 = new Point3D(pt1.x + (pt1.x - pt2.x) + (pt1.x - pt3.x),
                    pt1.y + (pt1.y - pt2.y) + (pt1.y - pt3.y),
                    pt1.z + (pt1.z - pt2.z) + (pt1.z - pt3.z));
            Point3D newPt2 = new Point3D(pt2.x + (pt2.x - pt1.x) + (pt2.x - pt3.x),
                    pt2.y + (pt2.y - pt1.y) + (pt2.y - pt3.y),
                    pt2.z + (pt2.z - pt1.z) + (pt2.z - pt3.z));
            Point3D newPt3 = new Point3D(pt3.x + (pt3.x - pt1.x) + (pt3.x - pt2.x),
                    pt3.y + (pt3.y - pt1.y) + (pt3.y - pt2.y),
                    pt3.z + (pt3.z - pt1.z) + (pt3.z - pt2.z));
            pt1 = newPt1;
            pt2 = newPt2;
            pt3 = newPt3;
        }
        startBrush();
        writeFace(pt1, pt2, pt3);
        writeFace(pt1,
                new Point3D(x,
                        pt1.y,
                        pt1.z),
                pt2);
        writeFace(pt2,
                new Point3D(x,
                        pt2.y,
                        pt2.z),
                pt3);
        writeFace(pt3,
                new Point3D(x,
                        pt3.y,
                        pt3.z),
                pt1);
        writeFace(new Point3D(x,
                        pt3.y,
                        pt3.z),
                new Point3D(x,
                        pt2.y,
                        pt2.z),
                new Point3D(x,
                        pt1.y,
                        pt1.z));
        endBrush();
    }

    // Writes a quadrilateral prism, with front face specified and projected onto
    // the plane specified by constant x.  THE FOUR POINTS MUST BE COPLANAR AND
    // NO THREE POINTS CAN BE COLLINEAR.
    static void writeBrushExtendX(int x,
                                  Point3D pt1,
                                  Point3D pt2,
                                  Point3D pt3,
                                  Point3D pt4) {
        startBrush();
        writeFace(pt1, pt2, pt3);
        writeFace(pt1,
                new Point3D(x,
                        pt1.y,
                        pt1.z),
                pt2);
        writeFace(pt2,
                new Point3D(x,
                        pt2.y,
                        pt2.z),
                pt3);
        writeFace(pt3,
                new Point3D(x,
                        pt3.y,
                        pt3.z),
                pt4);
        writeFace(pt4,
                new Point3D(x,
                        pt4.y,
                        pt4.z),
                pt1);
        writeFace(new Point3D(x,
                        pt3.y,
                        pt3.z),
                new Point3D(x,
                        pt2.y,
                        pt2.z),
                new Point3D(x,
                        pt1.y,
                        pt1.z));
        endBrush();
    }

    // Writes a triangular prism, with front face specified and projected onto
    // the plane specified by constant y.
    static void writeBrushExtendY(int y,
                                  Point3D pt1,
                                  Point3D pt2,
                                  Point3D pt3,
                                  boolean extrude) {
        if (extrude) {
            pt1 = new Point3D(Math.round(pt1.x),
                    Math.round(pt1.y),
                    Math.round(pt1.z));
            pt2 = new Point3D(Math.round(pt2.x),
                    Math.round(pt2.y),
                    Math.round(pt2.z));
            pt3 = new Point3D(Math.round(pt3.x),
                    Math.round(pt3.y),
                    Math.round(pt3.z));
            Point3D newPt1 = new Point3D(pt1.x + (pt1.x - pt2.x) + (pt1.x - pt3.x),
                    pt1.y + (pt1.y - pt2.y) + (pt1.y - pt3.y),
                    pt1.z + (pt1.z - pt2.z) + (pt1.z - pt3.z));
            Point3D newPt2 = new Point3D(pt2.x + (pt2.x - pt1.x) + (pt2.x - pt3.x),
                    pt2.y + (pt2.y - pt1.y) + (pt2.y - pt3.y),
                    pt2.z + (pt2.z - pt1.z) + (pt2.z - pt3.z));
            Point3D newPt3 = new Point3D(pt3.x + (pt3.x - pt1.x) + (pt3.x - pt2.x),
                    pt3.y + (pt3.y - pt1.y) + (pt3.y - pt2.y),
                    pt3.z + (pt3.z - pt1.z) + (pt3.z - pt2.z));
            pt1 = newPt1;
            pt2 = newPt2;
            pt3 = newPt3;
        }
        startBrush();
        writeFace(pt1, pt2, pt3);
        writeFace(pt1,
                new Point3D(pt1.x,
                        y,
                        pt1.z),
                pt2);
        writeFace(pt2,
                new Point3D(pt2.x,
                        y,
                        pt2.z),
                pt3);
        writeFace(pt3,
                new Point3D(pt3.x,
                        y,
                        pt3.z),
                pt1);
        writeFace(new Point3D(pt3.x,
                        y,
                        pt3.z),
                new Point3D(pt2.x,
                        y,
                        pt2.z),
                new Point3D(pt1.x,
                        y,
                        pt1.z));
        endBrush();
    }

    // Writes a quadrilateral prism, with front face specified and projected onto
    // the plane specified by constant y.  THE FOUR POINTS MUST BE COPLANAR AND
    // NO THREE POINTS CAN BE COLLINEAR.
    static void writeBrushExtendY(int y,
                                  Point3D pt1,
                                  Point3D pt2,
                                  Point3D pt3,
                                  Point3D pt4) {
        startBrush();
        writeFace(pt1, pt2, pt3);
        writeFace(pt1,
                new Point3D(pt1.x,
                        y,
                        pt1.z),
                pt2);
        writeFace(pt2,
                new Point3D(pt2.x,
                        y,
                        pt2.z),
                pt3);
        writeFace(pt3,
                new Point3D(pt3.x,
                        y,
                        pt3.z),
                pt4);
        writeFace(pt4,
                new Point3D(pt4.x,
                        y,
                        pt4.z),
                pt1);
        writeFace(new Point3D(pt3.x,
                        y,
                        pt3.z),
                new Point3D(pt2.x,
                        y,
                        pt2.z),
                new Point3D(pt1.x,
                        y,
                        pt1.z));
        endBrush();
    }

    // Writes a triangular prism, with front face specified and projected onto
    // the plane specified by constant z.
    static void writeBrushExtendZ(int z,
                                  Point3D pt1,
                                  Point3D pt2,
                                  Point3D pt3,
                                  boolean extrude) {
        if (extrude) {
            pt1 = new Point3D(Math.round(pt1.x),
                    Math.round(pt1.y),
                    Math.round(pt1.z));
            pt2 = new Point3D(Math.round(pt2.x),
                    Math.round(pt2.y),
                    Math.round(pt2.z));
            pt3 = new Point3D(Math.round(pt3.x),
                    Math.round(pt3.y),
                    Math.round(pt3.z));
            Point3D newPt1 = new Point3D(pt1.x + (pt1.x - pt2.x) + (pt1.x - pt3.x),
                    pt1.y + (pt1.y - pt2.y) + (pt1.y - pt3.y),
                    pt1.z + (pt1.z - pt2.z) + (pt1.z - pt3.z));
            Point3D newPt2 = new Point3D(pt2.x + (pt2.x - pt1.x) + (pt2.x - pt3.x),
                    pt2.y + (pt2.y - pt1.y) + (pt2.y - pt3.y),
                    pt2.z + (pt2.z - pt1.z) + (pt2.z - pt3.z));
            Point3D newPt3 = new Point3D(pt3.x + (pt3.x - pt1.x) + (pt3.x - pt2.x),
                    pt3.y + (pt3.y - pt1.y) + (pt3.y - pt2.y),
                    pt3.z + (pt3.z - pt1.z) + (pt3.z - pt2.z));
            pt1 = newPt1;
            pt2 = newPt2;
            pt3 = newPt3;
        }
        startBrush();
        writeFace(pt1, pt2, pt3);
        writeFace(pt1,
                new Point3D(pt1.x,
                        pt1.y,
                        z),
                pt2);
        writeFace(pt2,
                new Point3D(pt2.x,
                        pt2.y,
                        z),
                pt3);
        writeFace(pt3,
                new Point3D(pt3.x,
                        pt3.y,
                        z),
                pt1);
        writeFace(new Point3D(pt3.x,
                        pt3.y,
                        z),
                new Point3D(pt2.x,
                        pt2.y,
                        z),
                new Point3D(pt1.x,
                        pt1.y,
                        z));
        endBrush();
    }

    // Writes a quadrilateral prism, with front face specified and projected onto
    // the plane specified by constant z.  THE FOUR POINTS MUST BE COPLANAR AND
    // NO THREE POINTS CAN BE COLLINEAR.
    static void writeBrushExtendZ(int z,
                                  Point3D pt1,
                                  Point3D pt2,
                                  Point3D pt3,
                                  Point3D pt4) {
        startBrush();
        writeFace(pt1, pt2, pt3);
        writeFace(pt1,
                new Point3D(pt1.x,
                        pt1.y,
                        z),
                pt2);
        writeFace(pt2,
                new Point3D(pt2.x,
                        pt2.y,
                        z),
                pt3);
        writeFace(pt3,
                new Point3D(pt3.x,
                        pt3.y,
                        z),
                pt4);
        writeFace(pt4,
                new Point3D(pt4.x,
                        pt4.y,
                        z),
                pt1);
        writeFace(new Point3D(pt3.x,
                        pt3.y,
                        z),
                new Point3D(pt2.x,
                        pt2.y,
                        z),
                new Point3D(pt1.x,
                        pt1.y,
                        z));
        endBrush();
    }

    static void writeWedge(Point3D topTri1,
                           Point3D topTri2,
                           Point3D topTri3,
                           Point3D bottomTri1,
                           Point3D bottomTri2,
                           Point3D bottomTri3) {
        startBrush();
        writeFace(topTri1, topTri2, topTri3);
        writeFace(bottomTri3, bottomTri2, bottomTri1);
        writeFace(topTri2, topTri1, bottomTri1);
        writeFace(topTri3, topTri2, bottomTri2);
        writeFace(topTri1, topTri3, bottomTri3);
        endBrush();
    }

    static boolean equals(Point3D pt1,
                          Point3D pt2) {
        return ((Math.round(pt1.x) == Math.round(pt2.x))
                && (Math.round(pt1.y) == Math.round(pt2.y))
                && (Math.round(pt1.z) == Math.round(pt2.z)));
    }
}
