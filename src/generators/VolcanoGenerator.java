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
 * @version $Revision: 1.12 $
 */
public final class VolcanoGenerator extends GenerationUtils {

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
        // NOTE TO MYSELF: Don't support generation of cones using this
        // program because they would all have 45 degree inclines; write
        // another program to do this.
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
        final Point3D[][] mesh =
                new Point3D[slicesCircumference + 1][slicesSlope + 1];
        for (int i = 0; i <= slicesSlope; i++) {
            mesh[0][i] = curve[i];
        }
        AxisRotation3D rot = new AxisRotation3D
                (AxisRotation3D.Z_AXIS, Math.PI / (4 * slicesCircumference));
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
                if (((!offsetTopInsteadOfBottom) && (i % 2) == 1) ||
                        (offsetTopInsteadOfBottom && (i % 2) == 0)) {
                    mesh[slicesCircumference][i] =
                            new Point3D(0,
                                    curve[i].x,
                                    curve[i].z);
                }
            }
        }
        startMap();
        for (int i = 0; i < slicesCircumference * 2; i++) {
            if (generateOnlySeam && i != slicesCircumference * 2 - 1) continue;
            for (int j = 0; j < slicesSlope; j++) {
                boolean bottomPiece = false;
                // pt1 and pt2 will always be at the same elevation (z value).
                // In the case of bottomPiece, pt1 is right of pt2.
                final Point3D pt1, pt2, pt3;
                if ((i % 2) == 0) {
                    if (((!offsetTopInsteadOfBottom) && (j % 2) == 0) || (offsetTopInsteadOfBottom && (j % 2) == 1)) {
                        pt1 = mesh[i / 2][j];
                        pt2 = mesh[i / 2 + 1][j];
                        pt3 = mesh[i / 2][j + 1];
                    } else {
                        pt1 = mesh[i / 2 + 1][j + 1];
                        pt2 = mesh[i / 2][j + 1];
                        pt3 = mesh[i / 2][j];
                        bottomPiece = true;
                    }
                } else {
                    if (((!offsetTopInsteadOfBottom) && (j % 2) == 0) || (offsetTopInsteadOfBottom && (j % 2) == 1)) {
                        pt1 = mesh[i / 2 + 1][j + 1];
                        pt2 = mesh[i / 2][j + 1];
                        pt3 = mesh[i / 2 + 1][j];
                        bottomPiece = true;
                    } else {
                        pt1 = mesh[i / 2][j];
                        pt2 = mesh[i / 2 + 1][j];
                        pt3 = mesh[i / 2 + 1][j + 1];
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
                                pt4 = mesh[slicesCircumference / 2 + 1][j + 1];
                            } else {
                                pt4 = mesh[slicesCircumference / 2][j];
                            }
                        } else {
                            if (oddSlices) {
                                pt4 = mesh[slicesCircumference / 2 + 1][j];
                            } else {
                                pt4 = mesh[slicesCircumference / 2][j + 1];
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
                            writeFace(new Point3D(128, -128, 0),
                                    new Point3D(0, 0, 0),
                                    new Point3D(0, 0, -128));
                        }
                        endBrush();
                    }
                    if (bottomPiece && j == ((slicesSlope + 1) / 2) - 1) {
                        if (i < slicesCircumference - 2 || i > slicesCircumference) {
                            if (i == slicesCircumference * 2 - 1 &&
                                    !adjustSeamForJoining) {
                                startBrush();
                                writeFace(pt1, pt2, pt5);
                                writeFace(pt1, new Point3D(pt1.x, pt1.y, -crossSection), pt2);
                                writeFace(pt2, new Point3D(pt2.x, pt2.y, -crossSection), pt5);
                                writeFace(pt5, new Point3D(pt5.x, pt5.y, -crossSection), pt4);
                                writeFace(pt4, new Point3D(pt4.x, pt4.y, -crossSection), pt1);
                                writeFace(new Point3D(pt5.x, pt5.y, -crossSection),
                                        new Point3D(pt2.x, pt2.y, -crossSection),
                                        new Point3D(pt1.x, pt1.y, -crossSection));
                                writeFace(new Point3D(128, -128, 0),
                                        new Point3D(0, 0, 0),
                                        new Point3D(0, 0, -128));
                                endBrush();
                            } else {
                                writeBrushExtendZ(-crossSection, pt1, pt2, pt5, pt4);
                            }
                        } else if (i == slicesCircumference - 1) { // Middle piece.
                            writeBrushExtendZ(-crossSection, pt1, pt2, pt5, false);
                        } else if (i == slicesCircumference - 2) {
                            writeBrushExtendZ(-crossSection, pt1, pt2, pt5, false);
                        } else { // i == slicesCircumference.
                            writeBrushExtendZ(-crossSection, pt1, pt2, pt4, false);
                        }
                    }
                }
            }
        }
        endMap();
    }

}
