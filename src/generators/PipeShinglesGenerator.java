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
import tools.MapFactory;

/**
 * Generates shingles for a quarter pipe ramp.
 *
 * @version $Revision: 1.6 $
 */
public final class PipeShinglesGenerator extends GenerationUtils {

    public static void main(String[] args) {
        main(args, 0);
    }

    public static void main(String[] args, int mapSetting) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int radius = Integer.parseInt(args[0]);
        final int slices = Integer.parseInt(args[1]);
        final int length = Integer.parseInt(args[2]);
        final int extrudeTopNSlices = Integer.parseInt(args[3]);
        final double extrudeRadiusFactor = Double.parseDouble(args[4]);
        final boolean adjustForOverlappingPipeCuts = Boolean.valueOf(args[5]);
        final boolean adjustForOverlappingBowlCuts = Boolean.valueOf(args[6]);
        final int shinglesType = Integer.parseInt(args[7]);
        int adjustMask = 0;
        if (adjustForOverlappingBowlCuts) {
            adjustMask |= ADJUST_FOR_OVERLAPPING_BOWL_CUTS;
        }
        if (adjustForOverlappingPipeCuts) {
            adjustMask |= ADJUST_FOR_OVERLAPPING_PIPE_CUTS;
        }
        final Point3D[] curve = generatePipeCurve(radius,
                slices,
                extrudeTopNSlices,
                extrudeRadiusFactor,
                adjustMask);
        if (mapSetting == 0 || mapSetting == 1)
            startMap();
        MapFactory.addLine("}");
        startGroup();

        if (shinglesType == SHINGLES_TYPE_RAMP_SQTOP) {
            writeBrushExtendY(length,
                    curve[0],
                    curve[1],
                    new Point3D(curve[1].x - 1,
                            curve[1].y,
                            curve[1].z),
                    new Point3D(curve[0].x - 1,
                            curve[0].y,
                            curve[0].z));
            for (int i = 1; i < slices; i++) {
                if (curve[i].z > curve[i + 1].z) { // Attempt to prevent degenerate
                    // brushes, but there are similar problems in many other parts of
                    // the code.
                    writeBrushExtendY(length,
                            curve[i],
                            curve[i + 1],
                            new Point3D(curve[i + 1].x - 1,
                                    curve[i + 1].y,
                                    curve[i + 1].z),
                            false);
                }
            }
        } else if (shinglesType == SHINGLES_TYPE_RAMP_TRITOP) {
            for (int i = 0; i < slices; i++) {
                if (curve[i].z > curve[i + 1].z) { // Attempt to prevent degenerate
                    // brushes, but there are similar problems in many other parts of
                    // the code.
                    writeBrushExtendY(length,
                            curve[i],
                            curve[i + 1],
                            new Point3D(curve[i + 1].x - 1,
                                    curve[i + 1].y,
                                    curve[i + 1].z),
                            false);
                }
            }
        } else { // SHINGLES_TYPE_CYL.
            for (int i = 0; i < slices / 2; i++) {
                writeBrushExtendY(length,
                        curve[i],
                        curve[i + 1],
                        new Point3D(curve[i + 1].x - 1,
                                curve[i + 1].y,
                                curve[i + 1].z),
                        false);
            }
            for (int i = slices / 2; i < slices; i++) {
                writeBrushExtendY(length,
                        curve[i],
                        curve[i + 1],
                        new Point3D(curve[i + 1].x,
                                curve[i + 1].y,
                                curve[i + 1].z + 1),
                        false);
            }
        }
        if (mapSetting == 0 || mapSetting == 2)
            endMap();
    }

}
