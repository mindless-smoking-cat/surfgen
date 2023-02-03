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
 * Generates a quarter pipe ramp.
 *
 * @version $Revision: 1.6 $
 */
public final class PipeGenerator extends GenerationUtils {

    public static void main(String[] args) {
        main(args, 0);
    }

    public static void main(String[] args, int mapSetting) {
        //Shutdown.soon();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        final int radius = Integer.parseInt(args[0]);
        final int slices = Integer.parseInt(args[1]);
        final int crossSection = Integer.parseInt(args[2]);
        final int length = Integer.parseInt(args[3]);
        final boolean overlappingBrushes = Boolean.valueOf(args[4]);
        final int extrudeTopNSlices = Integer.parseInt(args[5]);
        final double extrudeRadiusFactor = Double.parseDouble(args[6]);
        final boolean adjustForOverlappingPipeCuts = Boolean.valueOf(args[7]);
        final boolean adjustForOverlappingBowlCuts = Boolean.valueOf(args[8]);
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
        for (int i = 0; i < slices; i++) {
            final Point3D pt1, pt2, pt3, pt4;
            if (!overlappingBrushes) {
                pt1 = curve[i];
                pt2 = curve[i + 1];
                pt3 = new Point3D(pt2.x,
                        pt2.y + length,
                        pt2.z);
                pt4 = new Point3D(pt1.x,
                        pt2.y + length,
                        pt1.z);
            } else {
                final double pt1x = Math.round(curve[i].x);
                final double pt1z = Math.round(curve[i].z);
                final double pt2x = Math.round(curve[i + 1].x);
                final double pt2z = Math.round(curve[i + 1].z);
                final double y = curve[i].y;
                pt1 = new Point3D(pt1x + (pt1x - pt2x),
                        y,
                        pt1z + (pt1z - pt2z));
                pt2 = new Point3D(pt2x + (pt2x - pt1x),
                        y,
                        pt2z + (pt2z - pt1z));
                pt3 = new Point3D(pt2.x,
                        y + length,
                        pt2.z);
                pt4 = new Point3D(pt1.x,
                        y + length,
                        pt1.z);
            }
            if (i < (slices + 1) / 2) {
                writeBrushExtendX(crossSection, pt1, pt2, pt3, pt4);
            } else {
                writeBrushExtendZ(-crossSection, pt1, pt2, pt3, pt4);
                if (i == (slices + 1) / 2 && !overlappingBrushes) {
                    writeBrushExtendZ(-crossSection,
                            curve[i],
                            new Point3D(curve[i].x,
                                    curve[i].y + length,
                                    curve[i].z),
                            new Point3D(crossSection,
                                    curve[i].y + length,
                                    curve[i].z),
                            new Point3D(crossSection,
                                    curve[i].y,
                                    curve[i].z));
                }
            }
        }
        if (mapSetting == 0 || mapSetting == 2)
            endMap();
    }
}
