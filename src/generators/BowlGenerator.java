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

import com.nerius.math.geom.Point3D;
import com.nerius.math.xform.AxisRotation3D;
import tools.MapFactory;

/**
 * @version $Revision: 1.6 $
 */
public final class BowlGenerator extends GenerationUtils {

    public static void main(String[] args) {
        main(args, 0);
    }

    public static void main(String[] args, int mapSetting) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int circumferenceRadius = Integer.parseInt(args[0]);
        final int slopeRadius = Integer.parseInt(args[1]);
        final int slicesCircumference = Integer.parseInt(args[2]);
        final int slicesSlope = Integer.parseInt(args[3]);
        final int crossSection = Integer.parseInt(args[4]);
        final boolean offsetTopInsteadOfBottom = Boolean.valueOf(args[5]);
        final boolean overlappingBrushes = Boolean.valueOf(args[6]);
        final int extrudeTopNSlices = Integer.parseInt(args[7]);
        final double extrudeRadiusFactor = Double.parseDouble(args[8]);
        final boolean adjustForOverlappingPipeCuts = Boolean.valueOf(args[9]);
        final boolean adjustForOverlappingBowlCuts = Boolean.valueOf(args[10]);
        final boolean invertSlopeVertices = Boolean.valueOf(args[11]);
        final boolean generateOnlySeam = Boolean.valueOf(args[12]);
        final boolean adjustSeamForJoining = Boolean.valueOf(args[13]);
        // NOTE TO MYSELF: Don't support generation of funnels using this
        // program because they would all have 45 degree inclines; write
        // another program to do this.

        try {
            int adjustMask = 0;
            if (adjustForOverlappingBowlCuts) {
                adjustMask |= ADJUST_FOR_OVERLAPPING_BOWL_CUTS;
            }
            if (adjustForOverlappingPipeCuts) {
                adjustMask |= ADJUST_FOR_OVERLAPPING_PIPE_CUTS;
            }
            Point3D[] curve = generatePipeCurve(slopeRadius,
                    slicesSlope,
                    extrudeTopNSlices,
                    extrudeRadiusFactor,
                    adjustMask);
            if (invertSlopeVertices) {
                final Point3D[] newCurve = new Point3D[curve.length];
                for (int i = 0; i < curve.length; i++) {
                    newCurve[curve.length - i - 1] =
                            new Point3D(-curve[i].z,
                                    curve[i].y,
                                    -curve[i].x);
                }
                curve = newCurve;
            }
            for (int i = 0; i < curve.length; i++) {
                curve[i] = new Point3D(curve[i].x + (circumferenceRadius - slopeRadius),
                        curve[i].y,
                        curve[i].z);
            }
            final Point3D[][] mesh =
                    new Point3D[slicesCircumference + 1][slicesSlope + 1];
            for (int i = 0; i <= slicesSlope; i++) {
                mesh[0][i] = curve[i];
            }
            AxisRotation3D rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, Math.PI / (4 * slicesCircumference));
            for (int i = 0; i <= slicesSlope; i++) {
                if ((!offsetTopInsteadOfBottom) && (i % 2) == 1) {
                    mesh[0][i] = rot.transform(mesh[0][i]);
                }
                if (offsetTopInsteadOfBottom && (i % 2) == 0) {
                    mesh[0][i] = rot.transform(mesh[0][i]);
                }
            }
            for (int i = 1; i <= slicesCircumference; i++) {
                rot = new AxisRotation3D(AxisRotation3D.Z_AXIS,
                        (Math.PI * i) / (2 * slicesCircumference));
                for (int j = 0; j <= slicesSlope; j++) {
                    mesh[i][j] = rot.transform(mesh[0][j]);
                }
            }
            if (adjustSeamForJoining) {
                for (int i = 0; i < curve.length; i++) {
                    if (((!offsetTopInsteadOfBottom) && (i % 2) == 1) || (offsetTopInsteadOfBottom && (i % 2) == 0)) {
                        mesh[slicesCircumference][i] =
                                new Point3D(0,
                                        curve[i].x,
                                        curve[i].z);
                    }
                }
            }
            if (mapSetting == 0 || mapSetting == 1)
                startMap();
            MapFactory.addLine("}");
            startGroup();
            for (int i = 0; i < slicesCircumference * 2; i++) {
                if (generateOnlySeam && i != slicesCircumference * 2 - 1) {
                    continue;
                }
                for (int j = 0; j < slicesSlope; j++) {
                    // When circumferenceRadius approaches slopeRadius, this code may
                    // generate degenerate brushes.
                    if (j == slicesSlope - 1 && circumferenceRadius == slopeRadius
                            && (((!offsetTopInsteadOfBottom) && (slicesSlope % 2) == (i % 2))
                            || (offsetTopInsteadOfBottom && (slicesSlope % 2) != (i % 2)))) {
                        continue;
                    }
                    final Point3D pt1, pt2, pt3;
                    if ((i % 2) == 0) {
                        if (((!offsetTopInsteadOfBottom) && (j % 2) == 0) || (offsetTopInsteadOfBottom && (j % 2) == 1)) {
                            pt1 = mesh[i / 2][j];
                            pt2 = mesh[i / 2][j + 1];
                            pt3 = mesh[i / 2 + 1][j];
                        } else {
                            pt1 = mesh[i / 2][j];
                            pt2 = mesh[i / 2][j + 1];
                            pt3 = mesh[i / 2 + 1][j + 1];
                        }
                    } else {
                        if (((!offsetTopInsteadOfBottom) && (j % 2) == 0) || (offsetTopInsteadOfBottom && (j % 2) == 1)) {
                            pt1 = mesh[i / 2 + 1][j];
                            pt2 = mesh[i / 2][j + 1];
                            pt3 = mesh[i / 2 + 1][j + 1];
                        } else {
                            pt1 = mesh[i / 2][j];
                            pt2 = mesh[i / 2 + 1][j + 1];
                            pt3 = mesh[i / 2 + 1][j];
                        }
                    }
                    final int extendXY = circumferenceRadius - slopeRadius + crossSection;
                    writeBrushExtendWrapper(extendXY, -crossSection, pt1, pt2, pt3, i >= slicesCircumference,
                            j >= (slicesSlope + 1) / 2, overlappingBrushes);
                    if (!overlappingBrushes) {
                        if (i == slicesCircumference && j < (slicesSlope + 1) / 2) {
                            writeBrushExtendY(extendXY,
                                    pt1,
                                    new Point3D(extendXY, pt1.y, pt1.z),
                                    new Point3D(extendXY, pt2.y, pt2.z),
                                    pt2);
                            if (j == (slicesSlope + 1) / 2 - 1) {
                                writeBrushExtendZ(-crossSection,
                                        pt2,
                                        new Point3D(pt2.x, extendXY, pt2.z),
                                        new Point3D(extendXY, extendXY, pt2.z),
                                        new Point3D(extendXY, pt2.y, pt2.z));
                            }
                        }
                        if (j == (slicesSlope + 1) / 2 - 1) {
                            if (((!offsetTopInsteadOfBottom) && (i % 2) != (j % 2))
                                    || (offsetTopInsteadOfBottom && (i % 2) == (j % 2))) {
                                if (i < slicesCircumference) {
                                    writeBrushExtendX(extendXY,
                                            pt2,
                                            new Point3D(pt2.x, pt2.y, -crossSection),
                                            new Point3D(pt3.x, pt3.y, -crossSection),
                                            pt3);
                                } else {
                                    writeBrushExtendY(extendXY,
                                            pt2,
                                            new Point3D(pt2.x, pt2.y, -crossSection),
                                            new Point3D(pt3.x, pt3.y, -crossSection),
                                            pt3);
                                }
                            }
                        }
                    }
                }
            }
            if (mapSetting == 0 || mapSetting == 2)
                endMap();

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    private static void writeBrushExtendWrapper(int extendXY,
                                                int extendZ,
                                                Point3D pt1,
                                                Point3D pt2,
                                                Point3D pt3,
                                                boolean pastHalfwayCircum,
                                                boolean pastHalfwaySlope,
                                                boolean overlappingBrushes) {
        if (!pastHalfwaySlope) {
            if (!pastHalfwayCircum) {
                writeBrushExtendX(extendXY, pt1, pt2, pt3, overlappingBrushes);
            } else {
                writeBrushExtendY(extendXY, pt1, pt2, pt3, overlappingBrushes);
            }
        } else {
            writeBrushExtendZ(extendZ, pt1, pt2, pt3, overlappingBrushes);
        }
    }
}
