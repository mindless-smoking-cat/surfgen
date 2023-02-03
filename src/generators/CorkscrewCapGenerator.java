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
 * @version $Revision: 1.6 $
 */
public final class CorkscrewCapGenerator extends GenerationUtils {

    private final static double OFFSET_FUDGE_FACTOR = 0.95;
    private final static double OFFSET_NUDGE_INCREMENT = 0.1;

    public static void main(String[] args) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int outerRadius = Integer.parseInt(args[0]);
        final int altitude = Integer.parseInt(args[1]);
        final int slices = Integer.parseInt(args[2]);
        final int outerThickness = Integer.parseInt(args[3]);
        final boolean adjustForOverlappingCylinderCuts = Boolean.valueOf(args[4]);
        final Point3D[][] mesh = new Point3D[slices + 1][2];
        {
            final Point3D[] outerCurve = generatePipeCurve
                    (outerRadius, slices, 0, 0.0d,
                            adjustForOverlappingCylinderCuts ? ADJUST_FOR_OVERLAPPING_PIPE_CUTS :
                                    ADJUST_NONE);
            final AxisRotation3D rot = new AxisRotation3D
                    (AxisRotation3D.X_AXIS, Math.PI / 2);
            for (int i = 0; i <= slices; i++) {
                outerCurve[i] = rot.transform(outerCurve[i]);
            }
            final double stepsPerSlice = ((double) altitude) / slices;
            for (int i = 0; i <= slices; i++) {
                mesh[i][0] = new Point3D(outerCurve[i].x,
                        outerCurve[i].y,
                        outerCurve[i].z + i * stepsPerSlice);
                mesh[i][1] = new Point3D
                        (outerCurve[i].x,
                                outerCurve[i].y,
                                outerCurve[i].z + i * stepsPerSlice - outerThickness);
            }
        }
        final Point3D[][] offsets = new Point3D[slices][2];
        {
            for (int i = 0; i < slices; i++) {
                double nudge = 0.0;
                while (true) {
                    // Possible infinite loop if first slice 0 angle.
                    Point3D candidatePoint = new Point3D(mesh[i][0].x,
                            mesh[i][0].y + nudge,
                            mesh[i][0].z);
                    if (sufficientOffset(mesh[i][0],
                            mesh[i + 1][0],
                            candidatePoint)) {
                        offsets[i][0] = candidatePoint;
                        offsets[i][1] = new Point3D(mesh[i][1].x,
                                mesh[i][1].y + nudge,
                                mesh[i][1].z);
                        break;
                    }
                    nudge += OFFSET_NUDGE_INCREMENT;
                }
            }
        }
        startMap();
        startBrush();
        writeFace(mesh[0][1],
                mesh[0][0],
                new Point3D(mesh[0][0].x + 1,
                        mesh[0][0].y,
                        mesh[0][0].z));
        writeFace(new Point3D(mesh[0][1].x + 1,
                        mesh[0][1].y,
                        mesh[0][1].z),
                new Point3D(mesh[0][0].x + 1,
                        mesh[0][0].y,
                        mesh[0][0].z),
                new Point3D(mesh[0][0].x + 1,
                        mesh[0][0].y + 1,
                        mesh[0][0].z));
        writeFace(new Point3D(mesh[0][0].x + 1,
                        mesh[0][0].y + 1,
                        mesh[0][0].z),
                offsets[0][0],
                offsets[0][1]);
        writeFace(mesh[0][0],
                mesh[0][1],
                offsets[0][1]);
        writeFace(new Point3D(mesh[0][0].x + 1,
                        mesh[0][0].y,
                        mesh[0][0].z),
                mesh[0][0],
                offsets[0][0]);
        writeFace(offsets[0][1],
                mesh[0][1],
                new Point3D(mesh[0][1].x + 1,
                        mesh[0][1].y,
                        mesh[0][1].z));
        endBrush();
        for (int i = 0; i < slices - 1; i++) {
            startBrush();
            writeFace(mesh[i][1],
                    mesh[i][0],
                    offsets[i][0]);
            writeFace(offsets[i][1],
                    offsets[i][0],
                    offsets[i + 1][0]);
            writeFace(offsets[i + 1][0],
                    mesh[i + 1][0],
                    mesh[i + 1][1]);
            writeFace(mesh[i + 1][0],
                    mesh[i][0],
                    mesh[i][1]);
            writeFace(mesh[i][0],
                    mesh[i + 1][0],
                    offsets[i + 1][0]);
            writeFace(offsets[i + 1][1],
                    mesh[i + 1][1],
                    mesh[i][1]);
            endBrush();
        }
        startBrush();
        writeFace(mesh[slices - 1][1],
                mesh[slices - 1][0],
                offsets[slices - 1][0]);
        writeFace(mesh[slices][0],
                mesh[slices - 1][0],
                mesh[slices - 1][1]);
        writeFace(offsets[slices - 1][0],
                mesh[slices][0],
                mesh[slices][1]);
        writeFace(offsets[slices - 1][0],
                mesh[slices - 1][0],
                mesh[slices][0]);
        writeFace(offsets[slices - 1][1],
                mesh[slices][1],
                mesh[slices - 1][1]);
        endBrush();
        endMap();
    }

    private static boolean sufficientOffset(Point3D base,
                                            Point3D ptA,
                                            Point3D ptB) {
        base = new Point3D(Math.round(base.x),
                Math.round(base.y),
                0);
        ptA = new Point3D(Math.round(ptA.x),
                Math.round(ptA.y),
                0);
        ptB = new Point3D(Math.round(ptB.x),
                Math.round(ptB.y),
                0);
        final Point3D vecA = ptA.subtract(base);
        final Point3D vecB = ptB.subtract(base);
        final Point3D xProd = vecA.crossProduct(vecB);
        final double xProdLen = xProd.distanceFromOrigin();
        final double vecALen = vecA.distanceFromOrigin();
        if (xProdLen / vecALen >= OFFSET_FUDGE_FACTOR) return true;
        return false;
    }

}
