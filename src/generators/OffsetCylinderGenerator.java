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
 * Generates a quarter of an upright cylinder.  All vertices are rotated
 * one half of a slice counter-clockwise.
 *
 * @version $Revision: 1.6 $
 */
// NOTE TO MYSELF: We don't need shingles for this.
public final class OffsetCylinderGenerator extends GenerationUtils {

    public static void main(String[] args) {
        main(args, 0);
    }

    public static void main(String[] args, int mapSetting) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int outerRadius = Integer.parseInt(args[0]);
        final int innerRadius = Integer.parseInt(args[1]);
        final int slices = Integer.parseInt(args[2]);
        final int height = Integer.parseInt(args[3]);
        final boolean overlappingBrushes = Boolean.valueOf(args[4]);
        final boolean generateOnlySeam = Boolean.valueOf(args[5]);
        final boolean adjustSeamForJoining = Boolean.valueOf(args[6]);
        final Point3D[] outerCurve = new Point3D[slices + 1];
        final Point3D[] innerCurve = new Point3D[slices + 1];
        final Point3D ptOuter = new Point3D(outerRadius,
                0,
                0);
        final Point3D ptInner = new Point3D(innerRadius,
                0,
                0);
        final double offsetRadians = Math.PI / (4 * slices);
        for (int i = 0; i <= slices; i++) {
            final AxisRotation3D rot = new AxisRotation3D
                    (AxisRotation3D.Z_AXIS, offsetRadians + (Math.PI * i) / (2 * slices));
            outerCurve[i] = rot.transform(ptOuter);
            innerCurve[i] = rot.transform(ptInner);
        }
        if (adjustSeamForJoining) {
            outerCurve[slices] = new Point3D(0,
                    outerRadius,
                    0);
            innerCurve[slices] = new Point3D(0,
                    innerRadius,
                    0);
        }
        if (mapSetting == 0 || mapSetting == 1)
            startMap();
        MapFactory.addLine("}");
        startGroup();
        for (int i = 0; i < slices; i++) {
            if (generateOnlySeam && i != slices - 1) continue;
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
                    final double z = innerCurve[i].z;
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
