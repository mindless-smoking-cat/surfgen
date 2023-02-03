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

import com.nerius.math.geom.Point2D;
import com.nerius.math.geom.Point3D;

/**
 * Projection from 3-space onto a 2D plane.  The projection of a line is
 * itself a line.
 * @version $Revision: 1.2 $
 **/
public interface Projection
{

  public Point2D project(Point3D pt3D);

//    /**
//     * Returns the positive distance between the point in 3-space and its
//     * projection onto the projection plane, if the point in 3-space is
//     * "in front" of the
//     * plane.  Returns a negative distance if the 3-space point lies "behind"
//     * the plane.  For most projections,
//     * a point "in front" if the projection plane exactly when
//     * that point and the center of projection lie of opposite sides of
//     * the projection plane. 
//     **/
//    public abstract float getDistance(Point3D pt3D);

//    /**
//     * The z-coord of the returned point will be exactly
//     * <code>getDistance(pt3D)</code> and the x and y coordinates will be
//     * exactly <code>project(pt3D)</code>.
//     **/
//    public abstract Point3D projectWithDistance(Point3D pt3D);

}
