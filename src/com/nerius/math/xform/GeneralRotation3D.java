/*
  Copyright (c) 2001, Nerius Landys
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

package com.nerius.math.xform;

/**
 * The axis of rotation is any non-zero length vector originating from
 * the origin.
 * @version $Revision: 1.2 $
 **/
public class GeneralRotation3D extends AffineTransform3D
{

  public GeneralRotation3D(double vectorX, double vectorY, double vectorZ,
                           double theta)
  {
    super(getGeneralRotationMatrix(vectorX, vectorY, vectorZ, theta));
  }

  private static Matrix4x4 getGeneralRotationMatrix(
    double x, double y, double z, double theta)
  {
    double axisLength = Math.sqrt(x * x + y * y + z * z);
    if (axisLength == 0) throw new IllegalArgumentException
      ("length of rotation axis is 0");
    x /= axisLength;
    y /= axisLength;
    z /= axisLength;
    double sinTh = Math.sin(theta);
    double oneMinCosTh = 1.0d - Math.cos(theta);
    return new Matrix4x4(new double[][] {
      { 1.0d - oneMinCosTh * (z * z + y * y), -z * sinTh + x * y * oneMinCosTh,
        y * sinTh + z * x * oneMinCosTh, 0.0d },
      { z * sinTh + x * y * oneMinCosTh, 1.0d - oneMinCosTh * (z * z + x * x),
        -x * sinTh + z * y * oneMinCosTh, 0.0d },
      { -y * sinTh + z * x * oneMinCosTh, x * sinTh + z * y * oneMinCosTh,
        1.0d - oneMinCosTh * (y * y + x * x), 0.0d },
      { 0.0d, 0.0d, 0.0d, 1.0d } } );
  }

// 1 -           (1 - cos(a)) * (z^2 + y^2)   -z * sin(a) + x * y * (1 - cos(a))          y * sin(a) +  z * x * (1 - cos(a))        0
// z * sin(a) +  x * y * (1 - cos(a))         1 -           (1 - cos(a)) * (z^2 + x^2)    -x * sin(a) + z * y * (1 - cos(a))        0
// -y * sin(a) + z * x * (1 - cos(a))         x * sin(a) +  z * y * (1 - cos(a))          1 -           (1 - cos(a)) * (y^2 + x^2)  0
// 0                                          0                                           0                                         1

}
