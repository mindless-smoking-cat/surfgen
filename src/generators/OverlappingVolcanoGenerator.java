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

/**
 * @version $Revision: 1.4 $
 */
public final class OverlappingVolcanoGenerator extends GenerationUtils {

    public static void main(String[] args) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int topCircumferenceRadius = Integer.parseInt(args[0]);
        final int slopeRadius = Integer.parseInt(args[1]);
        final int slicesCircumference = Integer.parseInt(args[2]);
        final int slicesSlope = Integer.parseInt(args[3]);
        final int crossSection = Integer.parseInt(args[4]);
        final boolean offsetTopInsteadOfBottom = Boolean.valueOf(args[5]);
        final int extrudeTopNSlices = Integer.parseInt(args[6]);
        final double extrudeRadiusFactor = Double.parseDouble(args[7]);
        final boolean adjustForOverlappingPipeCuts = Boolean.valueOf(args[8]);
        final boolean adjustForOverlappingBowlCuts = Boolean.valueOf(args[9]);
        final boolean invertSlopeVertices = Boolean.valueOf(args[10]);
        final boolean generateOnlySeam = Boolean.valueOf(args[11]);
        final boolean adjustSeamForJoining = Boolean.valueOf(args[12]);
        final boolean cutTop = Boolean.valueOf(args[13]);
        final boolean cutBottom = Boolean.valueOf(args[14]);
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
            curve[i] = new Point3D(curve[i].x - (topCircumferenceRadius + slopeRadius),
                    curve[i].y,
                    curve[i].z);
        }
        final Point3D[][] extCurve = new Point3D[slicesSlope][2];
        final Point3D[][][] mesh =
                new Point3D[slicesCircumference + 1][slicesSlope][2];
        for (int i = 0; i < slicesSlope; i++) {
            if (i == 0 && cutTop) {
                extCurve[i][0] = curve[i];
            } else {
                extCurve[i][0] = new Point3D(curve[i].x + (curve[i].x - curve[i + 1].x),
                        curve[i].y + (curve[i].y - curve[i + 1].y),
                        curve[i].z + (curve[i].z - curve[i + 1].z));
            }
            mesh[0][i][0] = extCurve[i][0];
            if (i == slicesSlope - 1 && cutBottom) {
                extCurve[i][1] = curve[i + 1];
            } else {
                extCurve[i][1] = new Point3D(curve[i + 1].x + (curve[i + 1].x - curve[i].x),
                        curve[i + 1].y + (curve[i + 1].y - curve[i].y),
                        curve[i + 1].z + (curve[i + 1].z - curve[i].z));
            }
            mesh[0][i][1] = extCurve[i][1];
        }
        AxisRotation3D rot = new AxisRotation3D
                (AxisRotation3D.Z_AXIS, Math.PI / (4 * slicesCircumference));
        for (int i = 0; i < slicesSlope; i++) {
            if (!offsetTopInsteadOfBottom) {
                mesh[0][i][(i + 1) % 2] = rot.transform(mesh[0][i][(i + 1) % 2]);
            } else {
                mesh[0][i][(i + 0) % 2] = rot.transform(mesh[0][i][(i + 0) % 2]);
            }
        }
        for (int i = 1; i <= slicesCircumference; i++) {
            rot = new AxisRotation3D(AxisRotation3D.Z_AXIS,
                    (Math.PI * i) / (2 * slicesCircumference));
            for (int j = 0; j < slicesSlope; j++) {
                mesh[i][j][0] = rot.transform(mesh[0][j][0]);
                mesh[i][j][1] = rot.transform(mesh[0][j][1]);
            }
        }
        if (adjustSeamForJoining) {
            for (int i = 0; i < slicesSlope; i++) {
                if (!offsetTopInsteadOfBottom) {
                    mesh[slicesCircumference][i][(i + 1) % 2] =
                            new Point3D(0,
                                    extCurve[i][(i + 1) % 2].x,
                                    extCurve[i][(i + 1) % 2].z);
                } else {
                    mesh[slicesCircumference][i][(i + 0) % 2] =
                            new Point3D(0,
                                    extCurve[i][(i + 0) % 2].x,
                                    extCurve[i][(i + 1) % 2].z);
                }
            }
        }
        startMap();
        for (int i = 0; i < slicesCircumference * 2; i++) {
            if (generateOnlySeam && i != slicesCircumference * 2 - 1) continue;
            for (int j = 0; j < slicesSlope; j++) {
                // pt1 and pt2 will always be at the same elevation (z value).
                final Point3D pt1, pt2, pt3;
                if ((i % 2) == 0) {
                    if (((!offsetTopInsteadOfBottom) && (j % 2) == 0) ||
                            (offsetTopInsteadOfBottom && (j % 2) == 1)) {
                        pt1 = mesh[i / 2][j][0];
                        pt2 = mesh[i / 2 + 1][j][0];
                        pt3 = mesh[i / 2][j][1];
                    } else {
                        pt1 = mesh[i / 2 + 1][j][1];
                        pt2 = mesh[i / 2][j][1];
                        pt3 = mesh[i / 2][j][0];
                    }
                } else {
                    if (((!offsetTopInsteadOfBottom) && (j % 2) == 0) ||
                            (offsetTopInsteadOfBottom && (j % 2) == 1)) {
                        pt1 = mesh[i / 2 + 1][j][1];
                        pt2 = mesh[i / 2][j][1];
                        pt3 = mesh[i / 2 + 1][j][0];
                    } else {
                        pt1 = mesh[i / 2][j][0];
                        pt2 = mesh[i / 2 + 1][j][0];
                        pt3 = mesh[i / 2 + 1][j][1];
                    }
                }
                if (j >= (slicesSlope + 1) / 2) {
                    writeBrushExtendZ(-crossSection, pt1, pt2, pt3, false);
                } else {
                    final Point3D pt4, pt5, pt6;
                    if (i < slicesCircumference - 1) {
                        pt4 = new Point3D(pt1.y, pt1.y, pt1.z);
                        pt5 = new Point3D(pt2.y, pt2.y, pt2.z);
                        pt6 = new Point3D(pt3.y, pt3.y, pt3.z);
                    } else if (i > slicesCircumference - 1) {
                        pt4 = new Point3D(pt1.x, pt1.x, pt1.z);
                        pt5 = new Point3D(pt2.x, pt2.x, pt2.z);
                        pt6 = new Point3D(pt3.x, pt3.x, pt3.z);
                    } else { // Middle piece.
                        pt6 = null;
                        final int offset = (offsetTopInsteadOfBottom ? 1 : 0);
                        final int oddRow = (((j % 2) == 0) ? 0 : 1);
                        final boolean oddSlices = ((slicesCircumference % 2) != 0);
                        if ((offset ^ oddRow) != 0) {
                            if (oddSlices) {
                                pt4 = mesh[slicesCircumference / 2 + 1][j][1];
                            } else {
                                pt4 = mesh[slicesCircumference / 2][j][0];
                            }
                        } else {
                            if (oddSlices) {
                                pt4 = mesh[slicesCircumference / 2 + 1][j][0];
                            } else {
                                pt4 = mesh[slicesCircumference / 2][j][1];
                            }
                        }
                        pt5 = new Point3D(pt4.x, pt4.x, pt4.z);
                        startBrush();
                        writeFace(pt1, pt2, pt3);
                        writeFace(pt1, pt5, pt2);
                        writeFace(pt2, pt5, pt3);
                        writeFace(pt3, pt5, pt1);
                        endBrush();
                    }
                    if (pt6 != null) {
                        startBrush();
                        writeFace(pt1, pt2, pt3);
                        writeFace(pt4, pt6, pt5);
                        if (equals(pt1, pt4)) {
                            writeFace(pt2, pt1, pt5);
                        } else {
                            writeFace(pt2, pt1, pt4);
                        }
                        if (equals(pt2, pt5)) {
                            writeFace(pt3, pt2, pt6);
                        } else {
                            writeFace(pt3, pt2, pt5);
                        }
                        if (equals(pt3, pt6)) {
                            writeFace(pt1, pt3, pt4);
                        } else {
                            writeFace(pt1, pt3, pt6);
                        }
                        if (i == slicesCircumference * 2 - 1 && !adjustSeamForJoining) {
                            // I don't know if this is necessary, but it probably won't hinder anything.
                            writeFace(new Point3D(128, -128, 0),
                                    new Point3D(0, 0, 0),
                                    new Point3D(0, 0, -128));
                        }
                        endBrush();
                    }
                }
            }
        }
        endMap();
    }

}
