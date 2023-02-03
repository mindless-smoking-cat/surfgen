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
public final class GearGenerator extends GenerationUtils {

    public static void main(String[] args) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int outerRadius = Integer.parseInt(args[0]);
        final int innerRadius = Integer.parseInt(args[1]);
        final int centerRadius = Integer.parseInt(args[2]);
        final int outerThickness = Integer.parseInt(args[3]);
        final int innerThickness = Integer.parseInt(args[4]);
        final int centerThickness = Integer.parseInt(args[5]);
        final int teeth = Integer.parseInt(args[6]);
        final double outerToothLengthRatio = Double.parseDouble(args[7]);
        final double innerToothLengthRatio = Double.parseDouble(args[8]);
        final double offsetAngle = Double.parseDouble(args[9]);
        final Point3D[][] outerPts = new Point3D[teeth * 2][2];
        final Point3D[][] innerPts = new Point3D[teeth * 2][2];
        final Point3D[][] centerPts = new Point3D[teeth * 2][2];
        {
            innerPts[0][0] = new Point3D(innerRadius,
                    0,
                    ((double) innerThickness) / 2.0);
            innerPts[0][1] = new Point3D(innerRadius,
                    0,
                    -((double) innerThickness) / 2.0);
            final double theta = (Math.PI * 2) / teeth;
            double alpha;
            if (innerToothLengthRatio < 0.0) {
                alpha = theta;
            } else {
                alpha = (innerToothLengthRatio * theta) / (innerToothLengthRatio + 1);
            }
            AxisRotation3D rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, alpha);
            innerPts[1][0] = rot.transform(innerPts[0][0]);
            innerPts[1][1] = rot.transform(innerPts[0][1]);
            outerPts[0][0] = new Point3D(outerRadius,
                    0,
                    ((double) outerThickness) / 2.0);
            outerPts[0][1] = new Point3D(outerRadius,
                    0,
                    -((double) outerThickness) / 2.0);
            double beta;
            if (outerToothLengthRatio < 0.0) {
                beta = theta;
            } else {
                beta = (outerToothLengthRatio * theta) / (outerToothLengthRatio + 1);
            }
            rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, (alpha - beta) / 2);
            outerPts[0][0] = rot.transform(outerPts[0][0]);
            outerPts[0][1] = rot.transform(outerPts[0][1]);
            rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, beta);
            outerPts[1][0] = rot.transform(outerPts[0][0]);
            outerPts[1][1] = rot.transform(outerPts[0][1]);
            centerPts[0][0] = new Point3D(centerRadius,
                    0,
                    ((double) centerThickness) / 2.0);
            centerPts[0][1] = new Point3D(centerRadius,
                    0,
                    -((double) centerThickness) / 2.0);
            rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, theta / 2);
            centerPts[1][0] = rot.transform(centerPts[0][0]);
            centerPts[1][1] = rot.transform(centerPts[0][1]);
            for (int i = 1; i < teeth; i++) {
                rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, theta * i);
                outerPts[i * 2][0] = rot.transform(outerPts[0][0]);
                outerPts[i * 2][1] = rot.transform(outerPts[0][1]);
                outerPts[i * 2 + 1][0] = rot.transform(outerPts[1][0]);
                outerPts[i * 2 + 1][1] = rot.transform(outerPts[1][1]);
                innerPts[i * 2][0] = rot.transform(innerPts[0][0]);
                innerPts[i * 2][1] = rot.transform(innerPts[0][1]);
                innerPts[i * 2 + 1][0] = rot.transform(innerPts[1][0]);
                innerPts[i * 2 + 1][1] = rot.transform(innerPts[1][1]);
                centerPts[i * 2][0] = rot.transform(centerPts[0][0]);
                centerPts[i * 2][1] = rot.transform(centerPts[0][1]);
                centerPts[i * 2 + 1][0] = rot.transform(centerPts[1][0]);
                centerPts[i * 2 + 1][1] = rot.transform(centerPts[1][1]);
            }
            if (offsetAngle != 0) {
                final double radians = (Math.PI * offsetAngle) / 180;
                rot = new AxisRotation3D(AxisRotation3D.Z_AXIS, radians);
                for (int i = 0; i < teeth * 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        outerPts[i][j] = rot.transform(outerPts[i][j]);
                        innerPts[i][j] = rot.transform(innerPts[i][j]);
                        centerPts[i][j] = rot.transform(centerPts[i][j]);
                    }
                }
            }
        }
        startMap();
        for (int i = 0; i < teeth; i++) {
            if (outerToothLengthRatio != 0) {
                writeWedge(outerPts[i * 2 + 1][0],
                        outerPts[i * 2][0],
                        innerPts[i * 2][0],
                        outerPts[i * 2 + 1][1],
                        outerPts[i * 2][1],
                        innerPts[i * 2][1]);
            }
            if (innerToothLengthRatio != 0) {
                writeWedge(outerPts[i * 2 + 1][0],
                        innerPts[i * 2][0],
                        innerPts[i * 2 + 1][0],
                        outerPts[i * 2 + 1][1],
                        innerPts[i * 2][1],
                        innerPts[i * 2 + 1][1]);
                writeWedge(innerPts[i * 2 + 1][0],
                        innerPts[i * 2][0],
                        centerPts[i * 2][0],
                        innerPts[i * 2 + 1][1],
                        innerPts[i * 2][1],
                        centerPts[i * 2][1]);
            }
            if (centerRadius != 0) {
                writeWedge(innerPts[i * 2 + 1][0],
                        centerPts[i * 2][0],
                        centerPts[i * 2 + 1][0],
                        innerPts[i * 2 + 1][1],
                        centerPts[i * 2][1],
                        centerPts[i * 2 + 1][1]);
            }
            if (!(innerToothLengthRatio < 0.0)) {
                writeWedge(innerPts[(i == teeth - 1) ? 0 : (i * 2 + 2)][0],
                        innerPts[i * 2 + 1][0],
                        centerPts[i * 2 + 1][0],
                        innerPts[(i == teeth - 1) ? 0 : (i * 2 + 2)][1],
                        innerPts[i * 2 + 1][1],
                        centerPts[i * 2 + 1][1]);
            }
            if (centerRadius != 0) {
                writeWedge(centerPts[i * 2 + 1][0],
                        centerPts[(i == teeth - 1) ? 0 : (i * 2 + 2)][0],
                        innerPts[(i == teeth - 1) ? 0 : (i * 2 + 2)][0],
                        centerPts[i * 2 + 1][1],
                        centerPts[(i == teeth - 1) ? 0 : (i * 2 + 2)][1],
                        innerPts[(i == teeth - 1) ? 0 : (i * 2 + 2)][1]);
            }
        }
        endMap();
    }

}
