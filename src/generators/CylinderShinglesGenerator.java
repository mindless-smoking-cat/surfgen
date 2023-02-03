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
 * @version $Revision: 1.6 $
 */
public final class CylinderShinglesGenerator extends GenerationUtils {

    public static void main(String[] args) {
        main(args, 0);
    }

    public static void main(String[] args, int mapSetting) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int radius = Integer.parseInt(args[0]);
        final int slices = Integer.parseInt(args[1]);
        final int height = Integer.parseInt(args[2]);
        final boolean adjustForOverlappingCylinderCuts = Boolean.valueOf(args[3]);
        final int shinglesType = Integer.parseInt(args[4]);
        final Point3D[] curve = generatePipeCurve
                (radius, slices, 0, 0.0d,
                        adjustForOverlappingCylinderCuts ? ADJUST_FOR_OVERLAPPING_PIPE_CUTS :
                                ADJUST_NONE);
        final AxisRotation3D rot = new AxisRotation3D
                (AxisRotation3D.X_AXIS, Math.PI / 2);
        for (int i = 0; i <= slices; i++) {
            curve[i] = rot.transform(curve[i]);
        }
        if (mapSetting == 0 || mapSetting == 1)
            startMap();
        MapFactory.addLine("}");
        startGroup();
        // I am going to employ the lazy man's approach here: copy and paste code,
        // enter totally independent code block for each of the three different
        // types of shingles.
        if (shinglesType == SHINGLES_TYPE_RAMP_SQTOP) {
            writeBrushExtendZ(-height,
                    curve[0],
                    new Point3D(curve[0].x - 1,
                            curve[0].y,
                            curve[0].z),
                    new Point3D(curve[1].x - 1,
                            curve[1].y,
                            curve[1].z),
                    curve[1]);
            for (int i = 1; i < slices; i++) {
                if (curve[i].y >= curve[i + 1].y) continue;
                writeBrushExtendZ(-height,
                        curve[i],
                        new Point3D(curve[i + 1].x - 1,
                                curve[i + 1].y,
                                curve[i + 1].z),
                        curve[i + 1],
                        false);
            }
        } else if (shinglesType == SHINGLES_TYPE_RAMP_TRITOP) {
            for (int i = 0; i < slices; i++) {
                if (curve[i].y >= curve[i + 1].y) continue;
                writeBrushExtendZ(-height,
                        curve[i],
                        new Point3D(curve[i + 1].x - 1,
                                curve[i + 1].y,
                                curve[i + 1].z),
                        curve[i + 1],
                        false);
            }
        } else {
            for (int i = 0; i < slices / 2; i++) {
                writeBrushExtendZ(-height,
                        curve[i],
                        new Point3D(curve[i + 1].x - 1,
                                curve[i + 1].y,
                                curve[i + 1].z),
                        curve[i + 1],
                        false);
            }
            for (int i = slices / 2; i < slices; i++) {
                writeBrushExtendZ(-height,
                        curve[i],
                        new Point3D(curve[i + 1].x,
                                curve[i + 1].y - 1,
                                curve[i + 1].z),
                        curve[i + 1],
                        false);
            }
        }
        if (mapSetting == 0 || mapSetting == 2)
            endMap();
    }

}
