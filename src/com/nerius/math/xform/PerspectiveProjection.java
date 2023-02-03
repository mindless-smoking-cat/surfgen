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
 * Perspective projection.  The projection plane is orthogonal to
 * the z axis and intersects z at <code>zPlane</code>.
 * The center of projection lies at <code>eyeball</code>.
 * @version $Revision: 1.2 $
 **/
public class PerspectiveProjection implements Projection
{

  public final Point3D eyeball;
  public final double zPlane;

  /**
   * The <code>eyeball</code> should not lie directly on the projection plane
   * (that would be <code>eyeball.z == zPlane</code>).
   **/
  public PerspectiveProjection(Point3D eyeball, double zPlane)
  {
    if (eyeball == null)
      throw new NullPointerException("eyeball is null");
    this.eyeball = eyeball;
    this.zPlane = zPlane;
  }

  public Point2D project(Point3D pt3D)
  {
    return new Point2D(getXProjection(pt3D.x, pt3D.z),
                       getYProjection(pt3D.y, pt3D.z));
  }

//    public float getDistance(Point3D pt3D)
//    {
//      final Point2D proj = project(pt3D);
//      return (new Point3D(proj.x, proj.y, zPlane)).distance(pt3D) *
//        (float) sign((zPlane - eyeball.z) * (pt3D.z - zPlane));
//    }

//    public Point3D projectWithDistance(Point3D pt3D)
//    {
//      final float x = getXProjection(pt3D.x, pt3D.z);
//      final float y = getYProjection(pt3D.y, pt3D.z);
//      return new Point3D
//        (x, y,
//         (float) Math.sqrt(square(x - pt3D.x) + square(y - pt3D.y) +
//                           square(zPlane - pt3D.z)) *
//         (float) sign((zPlane - eyeball.z) * (pt3D.z - zPlane)));
//    }

//    private float square(float f)
//    {
//      return f * f;
//    }

//    private int sign(float f)
//    {
//      if (f < 0) return -1;
//      else return 1;
//    }


  /**
   * Projects the 3D-line defined by x=<code>xCoord</code> and
   * z=<code>zCoord</code> onto the projection plane; the return value
   * defines the fixed x value of the line as it is projected onto the
   * plane.  If <code>zCoord == eyeball.z</code>, then bad things will happen.
   **/
  private double getXProjection(double xCoord, double zCoord)
  {
    double f = xCoord - eyeball.x;
    f /= (eyeball.z - zCoord) / (eyeball.z - zPlane);
    return f + eyeball.x;
  }

  /**
   * Projects the 3D-line defined by y=<code>yCoord</code> and
   * z=<code>zCoord</code> onto the projection plane; the return value
   * defines the fixed y value of the line as it is projected onto the
   * plane.  If <code>zCoord == eyeball.z</code>, then bad things will happen.
   **/
  private double getYProjection(double yCoord, double zCoord)
  {
    double f = yCoord - eyeball.y;
    f /= (eyeball.z - zCoord) / (eyeball.z - zPlane);
    return f + eyeball.y;
  }

}
