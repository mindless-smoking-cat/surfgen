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

/**
 * @version $Revision: 1.6 $
 */
public final class SineWaveGenerator extends GenerationUtils {

    public static void main(String[] args) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int surfaceLength = Integer.parseInt(args[0]);
        final int surfaceWidth = Integer.parseInt(args[1]);
        final int slicesLength = Integer.parseInt(args[2]);
        final int slicesWidth = Integer.parseInt(args[3]);
        final int leftHeight = Integer.parseInt(args[4]);
        final int leftAmplitude = Integer.parseInt(args[5]);
        final int leftWavelength = Integer.parseInt(args[6]);
        final double leftPhase = Double.parseDouble(args[7]);
        final int rightHeight = Integer.parseInt(args[8]);
        final int rightAmplitude = Integer.parseInt(args[9]);
        final int rightWavelength = Integer.parseInt(args[10]);
        final double rightPhase = Double.parseDouble(args[11]);
        final Point3D[][] mesh = new Point3D[slicesLength + 1][slicesWidth + 1];
        {
            final Point3D[] leftCurve = new Point3D[slicesLength + 1];
            final Point3D[] rightCurve = new Point3D[slicesLength + 1];
            {
                final double step = ((double) surfaceLength) / ((double) slicesLength);
                final double leftThetaPerStep =
                        (2.0d * Math.PI) / (leftWavelength / step);
                final double rightThetaPerStep =
                        (2.0d * Math.PI) / (rightWavelength / step);
                double currLeftTheta = leftPhase * 2.0d * Math.PI;
                double currRightTheta = rightPhase * 2.0d * Math.PI;
                for (int i = 0; i <= slicesLength; i++) {
                    leftCurve[i] = new Point3D
                            (0,
                                    i * step,
                                    leftHeight + (Math.sin(currLeftTheta) * leftAmplitude));
                    rightCurve[i] = new Point3D
                            (surfaceWidth,
                                    i * step,
                                    rightHeight + (Math.sin(currRightTheta) * rightAmplitude));
                    currLeftTheta += leftThetaPerStep;
                    currRightTheta += rightThetaPerStep;
                }
            }
            for (int i = 0; i <= slicesLength; i++) {
                for (int j = 0; j <= slicesWidth; j++) {
                    mesh[i][j] = new Point3D
                            (leftCurve[i].x + ((rightCurve[i].x - leftCurve[i].x) *
                                    (((double) j) / ((double) slicesWidth))),
                                    leftCurve[i].y + ((rightCurve[i].y - leftCurve[i].y) *
                                            (((double) j) / ((double) slicesWidth))),
                                    leftCurve[i].z + ((rightCurve[i].z - leftCurve[i].z) *
                                            (((double) j) / ((double) slicesWidth))));
                }
            }
        }
        startMap();
        for (int i = 0; i < slicesLength; i++) {
            for (int j = 0; j < slicesWidth; j++) {
                writeBrushExtendZ(0,
                        mesh[i][j + 1],
                        mesh[i][j],
                        mesh[i + 1][j],
                        false);
                writeBrushExtendZ(0,
                        mesh[i + 1][j],
                        mesh[i + 1][j + 1],
                        mesh[i][j + 1],
                        false);
            }
        }
        endMap();
    }

}
