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
 * @version $Revision: 1.7 $
 */
public final class CorkscrewGenerator extends GenerationUtils {

    public static void main(String[] args) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        // NOTE TO MYSELF: Don't support offset.  It's too complicated.
        final int outerRadius = Integer.parseInt(args[0]);
        final int innerRadius = Integer.parseInt(args[1]);
        final int altitude = Integer.parseInt(args[2]);
        final int lip = Integer.parseInt(args[3]);
        final int slices = Integer.parseInt(args[4]);
        final int outerThickness = Integer.parseInt(args[5]);
        final int innerThickness = Integer.parseInt(args[6]);
        final boolean adjustForOverlappingCylinderCuts = Boolean.valueOf(args[7]);
        final double offsetAngle = Double.parseDouble(args[8]);
        final Point3D[][][] mesh = new Point3D[slices + 1][2][2];
        {
            final Point3D[] outerCurve = generatePipeCurve
                    (outerRadius, slices, 0, 0.0d,
                            adjustForOverlappingCylinderCuts ? ADJUST_FOR_OVERLAPPING_PIPE_CUTS :
                                    ADJUST_NONE);
            final Point3D[] innerCurve = generatePipeCurve
                    (innerRadius, slices, 0, 0.0d,
                            adjustForOverlappingCylinderCuts ? ADJUST_FOR_OVERLAPPING_PIPE_CUTS :
                                    ADJUST_NONE);
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
            final double stepsPerSlice = ((double) altitude) / slices;
            for (int i = 0; i <= slices; i++) {
                mesh[i][0][0] = new Point3D
                        (outerCurve[i].x,
                                outerCurve[i].y,
                                outerCurve[i].z + i * stepsPerSlice);
                mesh[i][0][1] = new Point3D
                        (outerCurve[i].x,
                                outerCurve[i].y,
                                outerCurve[i].z + i * stepsPerSlice - outerThickness);
                mesh[i][1][0] = new Point3D
                        (innerCurve[i].x,
                                innerCurve[i].y,
                                innerCurve[i].z + i * stepsPerSlice - lip);
                mesh[i][1][1] = new Point3D
                        (innerCurve[i].x,
                                innerCurve[i].y,
                                innerCurve[i].z + i * stepsPerSlice - (innerThickness + lip));
            }
        }
        startMap();
        for (int i = 0; i < slices; i++) {
            startBrush();
            writeFace(mesh[i][0][0],
                    mesh[i][1][0],
                    mesh[i + 1][0][0]);
            if (outerThickness != 0) {
                writeFace(mesh[i][0][0],
                        mesh[i][0][1],
                        mesh[i][1][1]);
            } else {
                writeFace(mesh[i][0][0],
                        mesh[i][1][1],
                        mesh[i][1][0]);
            }
            if (outerThickness != 0) {
                writeFace(mesh[i][0][0],
                        mesh[i + 1][0][0],
                        mesh[i + 1][0][1]);
            }
            if (innerThickness != 0) {
                writeFace(mesh[i + 1][0][0],
                        mesh[i][1][0],
                        mesh[i][1][1]);
            } else {
                writeFace(mesh[i + 1][0][0],
                        mesh[i][1][0],
                        mesh[i + 1][0][1]);
            }
            writeFace(mesh[i][0][1],
                    mesh[i + 1][0][1],
                    mesh[i][1][1]);
            endBrush();
            startBrush();
            writeFace(mesh[i + 1][0][0],
                    mesh[i][1][0],
                    mesh[i + 1][1][0]);
            if (outerThickness != 0) {
                writeFace(mesh[i + 1][0][0],
                        mesh[i + 1][0][1],
                        mesh[i][1][1]);
            } else {
                writeFace(mesh[i + 1][0][0],
                        mesh[i][1][1],
                        mesh[i][1][0]);
            }
            if (innerThickness != 0) {
                writeFace(mesh[i + 1][0][0],
                        mesh[i + 1][1][0],
                        mesh[i + 1][1][1]);
            } else {
                writeFace(mesh[i + 1][0][0],
                        mesh[i + 1][1][0],
                        mesh[i + 1][0][1]);
            }
            if (innerThickness != 0) {
                writeFace(mesh[i][1][0],
                        mesh[i][1][1],
                        mesh[i + 1][1][1]);
            }
            writeFace(mesh[i + 1][0][1],
                    mesh[i + 1][1][1],
                    mesh[i][1][1]);
            endBrush();
        }
        endMap();
    }

}
