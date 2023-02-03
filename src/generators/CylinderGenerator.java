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
 * Generates a quarter of an upright cylinder.
 *
 * @version $Revision: 1.7 $
 */
public final class CylinderGenerator extends GenerationUtils {


    public static void main(String[] args) {
        main(args, 0);
    }

    public static void main(String[] args, int mapSetting) {
        final int outerRadius = Integer.parseInt(args[0]);
        final int innerRadius = Integer.parseInt(args[1]);
        final int slices = Integer.parseInt(args[2]);
        final int height = Integer.parseInt(args[3]);
        final boolean overlappingBrushes = Boolean.valueOf(args[4]);
        final boolean adjustForOverlappingCylinderCuts = Boolean.valueOf(args[5]);
        final double offsetAngle = Double.parseDouble(args[6]);
        final Point3D[] outerCurve = generatePipeCurve
                (outerRadius, slices, 0, 0.0d,
                        adjustForOverlappingCylinderCuts ? ADJUST_FOR_OVERLAPPING_PIPE_CUTS :
                                ADJUST_NONE);
        final Point3D[] innerCurve = generatePipeCurve
                (innerRadius, slices, 0, 0.0d,
                        (adjustForOverlappingCylinderCuts ? ADJUST_FOR_OVERLAPPING_PIPE_CUTS :
                                ADJUST_NONE));
        AxisRotation3D rot = new AxisRotation3D
                (AxisRotation3D.X_AXIS, Math.PI / 2);
        for (int i = 0; i <= slices; i++) {
            outerCurve[i] = rot.transform(outerCurve[i]);
            innerCurve[i] = rot.transform(innerCurve[i]);
        }
        if (offsetAngle != 0) {
            final double radians = (Math.PI * offsetAngle) / 180;
            rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, radians);
            for (int i = 0; i <= slices; i++) {
                outerCurve[i] = rot.transform(outerCurve[i]);
                innerCurve[i] = rot.transform(innerCurve[i]);
            }
        }
        if (mapSetting == 0 || mapSetting == 1)
            startMap();
        MapFactory.addLine("}");
        startGroup();
        for (int i = 0; i < slices; i++) {
            // When innerRadius approaches 0, this code will generate
            // degenerate brushes because consecutive inner points (rounded to
            // nearest integer) may be equal.
            if (innerRadius == 0.0d) {
                writeBrushExtendZ(-height,
                        outerCurve[i],
                        innerCurve[i],
                        outerCurve[i + 1],
                        false);
            } else {
                final Point3D pt1, pt2, pt3, pt4;
                pt1 = outerCurve[i];
                pt4 = outerCurve[i + 1];
                if (!overlappingBrushes) {
                    pt2 = innerCurve[i];
                    pt3 = innerCurve[i + 1];
                } else {
                    final double pt2x = Math.round(innerCurve[i].x);
                    final double pt2y = Math.round(innerCurve[i].y);
                    final double pt3x = Math.round(innerCurve[i + 1].x);
                    final double pt3y = Math.round(innerCurve[i + 1].y);
                    final double z = innerCurve[i].z; // Also innerCurve[i + 1].z.
                    pt2 = new Point3D(pt2x + (pt2x - pt3x),
                            pt2y + (pt2y - pt3y),
                            z);
                    pt3 = new Point3D(pt3x + (pt3x - pt2x),
                            pt3y + (pt3y - pt2y),
                            z);
                }
                writeBrushExtendZ(-height, pt1, pt2, pt3, pt4);
            }
        }
        if (mapSetting == 0 || mapSetting == 2)
            endMap();
    }

}
