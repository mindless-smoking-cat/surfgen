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
import ulben.UlbenUtils;

/**
 * @version $Revision: 1.7 $
 */
public final class FunnelGenerator extends GenerationUtils {


    public static void main(String[] args) {
        main(args, 0);
    }

    public static void main(String[] args, int mapSetting) {

        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int topRadius = Integer.parseInt(args[0]);
        final int bottomRadius = Integer.parseInt(args[1]);
        final int height = Integer.parseInt(args[2]);
        final int slices = Integer.parseInt(args[3]);
        final int crossSection = Integer.parseInt(args[4]);
        final boolean offsetTopInsteadOfBottom = Boolean.valueOf(args[5]);
        final boolean extendDownInsteadOfSquare = Boolean.valueOf(args[6]);
        final boolean overlappingBrushes = Boolean.valueOf(args[7]);
        final boolean generateOnlySeam = Boolean.valueOf(args[8]);
        final boolean adjustSeamForJoining = Boolean.valueOf(args[9]);
        final double offsetAngle = Double.parseDouble(args[10]);
        // STEP 1: Generate the top and bottom curves through 3-space.
        // Dependent on topRadius, bottomRadius, height, slices,
        // offsetTopInsteadOfBottom, and adjustSeamForJoining.
        final Point3D[] topCurve;
         Point3D[] bottomCurve;
        {
            // NOTE TO MYSELF: Handle case of either radius being zero.
            topCurve = new Point3D[slices + 1];
            bottomCurve = new Point3D[slices + 1];
            final Point3D topPt = new Point3D(topRadius,
                    0,
                    0);
            final Point3D bottomPt = new Point3D(bottomRadius,
                    0,
                    -height);
            AxisRotation3D rot = new AxisRotation3D
                    (AxisRotation3D.Z_AXIS, Math.PI / (4 * slices));
            if (!offsetTopInsteadOfBottom) {
                topCurve[0] = topPt;
                bottomCurve[0] = rot.transform(bottomPt);
            } else {
                topCurve[0] = rot.transform(topPt);
                bottomCurve[0] = bottomPt;
            }
            for (int i = 1; i <= slices; i++) {
                rot = new AxisRotation3D(AxisRotation3D.Z_AXIS,
                        (Math.PI * i) / (2 * slices));
                topCurve[i] = rot.transform(topCurve[0]);
                bottomCurve[i] = rot.transform(bottomCurve[0]);
            }
            if (adjustSeamForJoining) {
                if (!offsetTopInsteadOfBottom) {
                    bottomCurve[slices] = new Point3D(0, bottomRadius, -height);

                } else {
                    topCurve[slices] = new Point3D(0,
                            topRadius,
                            0);
                }
            }
            if (offsetAngle != 0 && extendDownInsteadOfSquare) {
                final double radians = (Math.PI * offsetAngle) / 180;
                rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, radians);
                for (int i = 0; i <= slices; i++) {
                    topCurve[i] = rot.transform(topCurve[i]);
                    bottomCurve[i] = rot.transform(bottomCurve[i]);
                }
            }
        }
        if (mapSetting == 0 || mapSetting == 1)
            startMap();
        MapFactory.addLine("}");
        startGroup();
        for (int i = 0; i < slices * 2; i++) {
            if (generateOnlySeam && i != slices * 2 - 1) continue;
            if ((i % 2) == 0) { // i is even.
                if (!offsetTopInsteadOfBottom) {
                    if (topRadius != 0) {
                        writeBrushExtendWrapper
                                (crossSection, topCurve[i / 2], bottomCurve[i / 2],
                                        topCurve[i / 2 + 1], i >= slices, extendDownInsteadOfSquare,
                                        overlappingBrushes);
                    } // else topRadius is 0, don't write brush.
                } else { // offsetTopInsteadOfBottom is true.
                    if (bottomRadius != 0) {
                        writeBrushExtendWrapper
                                (crossSection, bottomCurve[i / 2], bottomCurve[i / 2 + 1],
                                        topCurve[i / 2], i >= slices, extendDownInsteadOfSquare,
                                        overlappingBrushes);
                    } // else bottomRadius is 0, don't write brush.
                }
            } else { // i is odd.
                if (!offsetTopInsteadOfBottom) {
                    if (bottomRadius != 0) {
                        writeBrushExtendWrapper
                                (crossSection, topCurve[i / 2 + 1], bottomCurve[i / 2],
                                        bottomCurve[i / 2 + 1], i >= slices, extendDownInsteadOfSquare,
                                        overlappingBrushes);
                    } // else bottomRadius is 0, don't write brush.
                } else { // offsetTopInsteadOfBottom is true.
                    if (topRadius != 0) {
                        writeBrushExtendWrapper
                                (crossSection, topCurve[i / 2], bottomCurve[i / 2 + 1],
                                        topCurve[i / 2 + 1], i >= slices, extendDownInsteadOfSquare,
                                        overlappingBrushes);
                    } // else topRadius is 0, don't write brush.
                }
            }
            if (i == slices && (!extendDownInsteadOfSquare) &&
                    (!overlappingBrushes)) {
                final Point3D tempTopPt, tempBottomPt;
                if ((i % 2) == 0) {
                    tempTopPt = topCurve[i / 2];
                    tempBottomPt = bottomCurve[i / 2];
                } else {
                    if (!offsetTopInsteadOfBottom) {
                        tempTopPt = topCurve[i / 2 + 1];
                        tempBottomPt = bottomCurve[i / 2];
                    } else {
                        tempTopPt = topCurve[i / 2];
                        tempBottomPt = bottomCurve[i / 2 + 1];
                    }
                }
                writeBrushExtendY(crossSection,
                        tempTopPt,
                        new Point3D(crossSection,
                                tempTopPt.y,
                                tempTopPt.z),
                        new Point3D(crossSection,
                                tempBottomPt.y,
                                tempBottomPt.z),
                        tempBottomPt);
            }
        }
        if (mapSetting == 0 || mapSetting == 2)
            endMap();
    }

    private static void writeBrushExtendWrapper(int crossSection,
                                                Point3D pt1,
                                                Point3D pt2,
                                                Point3D pt3,
                                                boolean pastHalfway,
                                                boolean extendDown,
                                                boolean overlappingBrushes) {
        if (!extendDown) {
            if (!pastHalfway) { // Not yet halfway; extend x.
                writeBrushExtendX(crossSection,
                        pt1,
                        pt2,
                        pt3,
                        overlappingBrushes);
            } else { // We're past halfway; extend y instead of x.
                writeBrushExtendY(crossSection,
                        pt1,
                        pt2,
                        pt3,
                        overlappingBrushes);
            }
        } else { // extendDown is true.
            writeBrushExtendZ(-crossSection,
                    pt1,
                    pt2,
                    pt3,
                    overlappingBrushes);
        }
    }

}
