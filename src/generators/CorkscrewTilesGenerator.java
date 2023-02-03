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
 * @version $Revision: 1.10 $
 */
public final class CorkscrewTilesGenerator extends GenerationUtils {

    public static void main(String[] args) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int outerRadius = Integer.parseInt(args[0]);
        final int innerRadius = Integer.parseInt(args[1]);
        final int altitude = Integer.parseInt(args[2]);
        final int lip = Integer.parseInt(args[3]);
        final int slices = Integer.parseInt(args[4]);
        final boolean adjustForOverlappingCylinderCuts = Boolean.valueOf(args[5]);
        final boolean reverseTileDirection = Boolean.valueOf(args[6]);
        final double offsetAngle = Double.parseDouble(args[7]);
        final Point3D[][] mesh = new Point3D[slices + 1][2];
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
                mesh[i][0] = new Point3D(outerCurve[i].x,
                        outerCurve[i].y,
                        outerCurve[i].z + i * stepsPerSlice);
                mesh[i][1] = new Point3D(innerCurve[i].x,
                        innerCurve[i].y,
                        innerCurve[i].z + i * stepsPerSlice - lip);
            }
        }
        startMap();
        if (!reverseTileDirection) {
            for (int i = 0; i < slices; i++) {
                startBrush();
                Point3D raisedPt = new Point3D(mesh[i + 1][0].x,
                        mesh[i + 1][0].y,
                        mesh[i + 1][0].z + 1);
                writeFace(raisedPt,
                        mesh[i][0],
                        mesh[i][1]);
                writeFace(mesh[i][1],
                        mesh[i][0],
                        mesh[i + 1][0]);
                writeFace(mesh[i][1],
                        mesh[i + 1][0],
                        raisedPt);
                writeFace(raisedPt,
                        mesh[i + 1][0],
                        mesh[i][0]);
                endBrush();
                startBrush();
                raisedPt = new Point3D(mesh[i + 1][1].x,
                        mesh[i + 1][1].y,
                        mesh[i + 1][1].z + 1);
                writeFace(mesh[i][1],
                        raisedPt,
                        mesh[i + 1][0]);
                writeFace(mesh[i + 1][0],
                        mesh[i + 1][1],
                        mesh[i][1]);
                writeFace(mesh[i][1],
                        mesh[i + 1][1],
                        raisedPt);
                writeFace(raisedPt,
                        mesh[i + 1][1],
                        mesh[i + 1][0]);
                endBrush();
            }
        }
        // Poor man's approach, copy and paste code.
        else { // reverseTileDirection.
            for (int i = 0; i < slices; i++) {
                startBrush();
                Point3D raisedPt = new Point3D(mesh[i][0].x,
                        mesh[i][0].y,
                        mesh[i][0].z + 1);
                writeFace(mesh[i + 1][0],
                        raisedPt,
                        mesh[i][1]);
                writeFace(mesh[i][1],
                        mesh[i][0],
                        mesh[i + 1][0]);
                writeFace(raisedPt,
                        mesh[i][0],
                        mesh[i][1]);
                writeFace(mesh[i + 1][0],
                        mesh[i][0],
                        raisedPt);
                endBrush();
                startBrush();
                raisedPt = new Point3D(mesh[i][1].x,
                        mesh[i][1].y,
                        mesh[i][1].z + 1);
                writeFace(raisedPt,
                        mesh[i + 1][1],
                        mesh[i + 1][0]);
                writeFace(mesh[i + 1][0],
                        mesh[i + 1][1],
                        mesh[i][1]);
                writeFace(raisedPt,
                        mesh[i][1],
                        mesh[i + 1][1]);
                writeFace(mesh[i + 1][0],
                        mesh[i][1],
                        raisedPt);
                endBrush();
            }
        }
        endMap();
    }

}
