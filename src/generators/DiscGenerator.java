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
 * Generates half of a disc.
 *
 * @version $Revision: 1.6 $
 */
public final class DiscGenerator extends GenerationUtils {

    public static void main(String[] args) {

        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int radius = Integer.parseInt(args[0]);
        final int faces = Integer.parseInt(args[1]);
        final int height = Integer.parseInt(args[2]);
        final boolean adjustForOverlappingCylinderCuts = Boolean.valueOf(args[3]);
        final double offsetAngle = Double.parseDouble(args[4]);
        final Point3D[] curve;
        if (adjustForOverlappingCylinderCuts) {
            if ((faces % 2) != 0) {
                System.err.println("WARNING: Cannot adjust for overlapping " +
                        "cylinder cuts when number of faces\n" +
                        "in half-disc is odd.");
                curve = generateCurveNormally(radius, faces);
            } else {
                final Point3D[] curveTemp = generatePipeCurve
                        (radius, faces / 2, 0, 0.0d, ADJUST_FOR_OVERLAPPING_PIPE_CUTS);
                curve = new Point3D[faces + 1];
                AxisRotation3D rot = new AxisRotation3D
                        (AxisRotation3D.X_AXIS, Math.PI / 2);
                for (int i = 0; i < curveTemp.length; i++) {
                    curve[i] = rot.transform(curveTemp[i]);
                }
                rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, Math.PI / 2);
                for (int i = curveTemp.length; i <= faces; i++) {
                    curve[i] = rot.transform(curve[i - curveTemp.length + 1]);
                }
            }
        } else {
            curve = generateCurveNormally(radius, faces);
        }
        if (offsetAngle != 0) {
            final double radians = (Math.PI * offsetAngle) / 180;
            AxisRotation3D rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, radians);
            for (int i = 0; i <= faces; i++) {
                curve[i] = rot.transform(curve[i]);
            }
        }

        startMap();
        MapFactory.addLine("}");
        startGroup();
        for (int i = 0; i < faces / 2; i++) {
            if (i + 1 == faces / 2) {
                if ((faces % 2) == 1) {
                    writeBrushExtendZ(-height,
                            curve[i],
                            curve[i + 3],
                            curve[i + 2],
                            curve[i + 1]);
                } else {
                    writeBrushExtendZ(-height,
                            curve[i],
                            curve[i + 2],
                            curve[i + 1],
                            false);
                }
            } else {
                final int vert = faces - i;
                writeBrushExtendZ(-height,
                        curve[i],
                        curve[vert],
                        curve[vert - 1],
                        curve[i + 1]);
            }
        }
        endMap();
    }

    private static Point3D[] generateCurveNormally(final int radius,
                                                   final int faces) {
        final Point3D pt = new Point3D(radius, 0, 0);
        final Point3D[] curve = new Point3D[faces + 1];
        for (int i = 0; i <= faces; i++) {
            final AxisRotation3D rot = new AxisRotation3D
                    (AxisRotation3D.Z_AXIS, (Math.PI * i) / faces);
            curve[i] = rot.transform(pt);
        }
        return curve;
    }

}
