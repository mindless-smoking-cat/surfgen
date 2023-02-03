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
 * @version $Revision: 1.9 $
 */
public final class CorkscrewShinglesGenerator extends GenerationUtils {

    public static void main(String[] args) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int innerRadius = Integer.parseInt(args[0]);
        final int altitude = Integer.parseInt(args[1]);
        final int lip = Integer.parseInt(args[2]);
        final int slices = Integer.parseInt(args[3]);
        final int innerThickness = Integer.parseInt(args[4]);
        final boolean adjustForOverlappingCylinderCuts = Boolean.valueOf(args[5]);
        final int shinglesType = Integer.parseInt(args[6]);
        final Point3D[][] mesh = new Point3D[slices + 1][2];
        {
            final Point3D[] innerCurve = generatePipeCurve
                    (innerRadius, slices, 0, 0.0d,
                            adjustForOverlappingCylinderCuts ? ADJUST_FOR_OVERLAPPING_PIPE_CUTS :
                                    ADJUST_NONE);
            final AxisRotation3D rot = new AxisRotation3D
                    (AxisRotation3D.X_AXIS, Math.PI / 2);
            for (int i = 0; i <= slices; i++) {
                innerCurve[i] = rot.transform(innerCurve[i]);
            }
            final double stepsPerSlice = ((double) altitude) / slices;
            for (int i = 0; i <= slices; i++) {
                mesh[i][0] = new Point3D
                        (innerCurve[i].x,
                                innerCurve[i].y,
                                innerCurve[i].z + i * stepsPerSlice - lip);
                mesh[i][1] = new Point3D
                        (innerCurve[i].x,
                                innerCurve[i].y,
                                innerCurve[i].z + i * stepsPerSlice - (innerThickness + lip));
            }
        }
        startMap();
        for (int i = 0; i < slices; i++) {
            final Point3D ptOffsetHigh, ptOffsetLow;
            if (shinglesType == SHINGLES_TYPE_RAMP_SQTOP ||
                    shinglesType == SHINGLES_TYPE_RAMP_TRITOP) {
                // Attempt to prevent degenerate brushes, but there are similar
                // problems in other parts of the code, for example infinite looping.
                if (mesh[i][0].y >= mesh[i + 1][0].y) continue;
                ptOffsetHigh = new Point3D(mesh[i + 1][0].x - 1,
                        mesh[i + 1][0].y,
                        mesh[i + 1][0].z);
                ptOffsetLow = new Point3D(mesh[i + 1][1].x - 1,
                        mesh[i + 1][1].y,
                        mesh[i + 1][1].z);
            }
            // else assume SHINGLES_TYPE_CYL.
            else if (i < slices / 2) {
                ptOffsetHigh = new Point3D(mesh[i + 1][0].x - 1,
                        mesh[i + 1][0].y,
                        mesh[i + 1][0].z);
                ptOffsetLow = new Point3D(mesh[i + 1][1].x - 1,
                        mesh[i + 1][1].y,
                        mesh[i + 1][1].z);
            } else {
                ptOffsetHigh = new Point3D(mesh[i + 1][0].x,
                        mesh[i + 1][0].y - 1,
                        mesh[i + 1][0].z);
                ptOffsetLow = new Point3D(mesh[i + 1][1].x,
                        mesh[i + 1][1].y - 1,
                        mesh[i + 1][1].z);
            }
            startBrush();
            writeFace(mesh[i][0],
                    mesh[i + 1][0],
                    mesh[i + 1][1]);
            if (i == 0 && shinglesType == SHINGLES_TYPE_RAMP_SQTOP) {
                Point3D ptTemp = new Point3D(mesh[i][0].x - 1,
                        mesh[i][0].y,
                        mesh[i][0].z);
                writeFace(ptTemp,
                        mesh[i][0],
                        mesh[i][1]);
                writeFace(ptOffsetLow,
                        ptOffsetHigh,
                        ptTemp);
            } else {
                writeFace(mesh[i][0],
                        mesh[i][1],
                        ptOffsetLow);
            }
            writeFace(mesh[i + 1][1],
                    mesh[i + 1][0],
                    ptOffsetHigh);
            writeFace(ptOffsetHigh,
                    mesh[i + 1][0],
                    mesh[i][0]);
            writeFace(mesh[i][1],
                    mesh[i + 1][1],
                    ptOffsetLow);
            endBrush();
        }
        endMap();
    }

}
