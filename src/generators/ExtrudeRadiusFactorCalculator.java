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

// modified by thelionroars to include a calculate method (identical to main) and made public

import com.nerius.math.geom.Point3D;
import com.nerius.math.xform.AxisRotation3D;

/**
 * @version $Revision: 1.6 $
 */
public class ExtrudeRadiusFactorCalculator {

    public static void main(String[] args) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int radius = Integer.parseInt(args[0]);
        final int slices = Integer.parseInt(args[1]);
        final int extrudeTopNSlices = Integer.parseInt(args[2]);
        final int lipOffset = Integer.parseInt(args[3]);
        Point3D pt0 = new Point3D(radius,
                0,
                0);
        AxisRotation3D rot =
                new AxisRotation3D(AxisRotation3D.Y_AXIS,
                        (Math.PI * extrudeTopNSlices) / (2 * slices));
        pt0 = rot.transform(pt0);
        Point3D pt1 = new Point3D(radius + lipOffset,
                0,
                0);
        Point3D pt2 = new Point3D(pt0.x - pt0.z,
                pt0.y,
                pt0.z + pt0.x);
        double a = Math.sqrt((pt0.x - pt1.x) * (pt0.x - pt1.x) +
                (pt0.z - pt1.z) * (pt0.z - pt1.z));
        double c = Math.sqrt((pt1.x - pt2.x) * (pt1.x - pt2.x) +
                (pt1.z - pt2.z) * (pt1.z - pt2.z));
        double b = Math.sqrt((pt2.x - pt0.x) * (pt2.x - pt0.x) +
                (pt2.z - pt0.z) * (pt2.z - pt0.z));
        double cosAlpha = (a * a + b * b - c * c) / (2 * a * b);
        double alpha = Math.acos(cosAlpha);
        double sinAlpha = Math.sin(alpha);
        double newRadius = a / (2 * sinAlpha);
        System.out.println("extrudeRadiusFactor: " + (newRadius / radius));
    }

    /**
     * Identical to main, but returns a double instead
     *
     * @param args the function variables as a String array, as per main
     * @return the extrudeRadiusFactor
     * @author thelionroars (a copy and paste job from Rambetter's work above)
     * @version 0.1 Friday 23 November 2012
     */
    public static double calculate(String[] args) {
        //  Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int radius = Integer.parseInt(args[0]);
        final int slices = Integer.parseInt(args[1]);
        final int extrudeTopNSlices = Integer.parseInt(args[2]);
        final int lipOffset = Integer.parseInt(args[3]);
        Point3D pt0 = new Point3D(radius,
                0,
                0);
        AxisRotation3D rot =
                new AxisRotation3D(AxisRotation3D.Y_AXIS,
                        (Math.PI * extrudeTopNSlices) / (2 * slices));
        pt0 = rot.transform(pt0);
        Point3D pt1 = new Point3D(radius + lipOffset,
                0,
                0);
        Point3D pt2 = new Point3D(pt0.x - pt0.z,
                pt0.y,
                pt0.z + pt0.x);
        double a = Math.sqrt((pt0.x - pt1.x) * (pt0.x - pt1.x) +
                (pt0.z - pt1.z) * (pt0.z - pt1.z));
        double c = Math.sqrt((pt1.x - pt2.x) * (pt1.x - pt2.x) +
                (pt1.z - pt2.z) * (pt1.z - pt2.z));
        double b = Math.sqrt((pt2.x - pt0.x) * (pt2.x - pt0.x) +
                (pt2.z - pt0.z) * (pt2.z - pt0.z));
        double cosAlpha = (a * a + b * b - c * c) / (2 * a * b);
        double alpha = Math.acos(cosAlpha);
        double sinAlpha = Math.sin(alpha);
        double newRadius = a / (2 * sinAlpha);
        return (newRadius / radius);
    }
}
