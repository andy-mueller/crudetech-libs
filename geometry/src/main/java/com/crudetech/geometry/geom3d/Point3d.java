////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//     Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.geometry.geom3d;

import com.crudetech.geometry.geom.FloatCompare;
import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.geometry.geom.ToleranceComparable;
import com.crudetech.lang.ArgumentNullException;


public final class Point3d implements ToleranceComparable<Point3d> {
    private double x;
    private double y;
    private double z;

    public Point3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point3d point3d = (Point3d) o;

        return equals(point3d, Tolerance3d.getGlobalTolerance());
    }

    @Override
    public int hashCode() {
        return hashCode(Tolerance3d.getGlobalTolerance());
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "Point3d{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public int hashCode(Tolerance tol) {
        return FloatCompare.hashCode(new double[]{x, y, z}, tol.getPointTolerance());
    }

    @Override
    public boolean equals(Point3d rhs, Tolerance tol) {
        if (rhs == null) return false;
        if (tol == null) throw new ArgumentNullException("tol");
        return Math.abs(distanceTo(rhs)) < tol.getPointTolerance();
    }
    public double distanceTo(Point3d other) {
        return other.subtract(this).getLength();
    }


    public Vector3d subtract(Point3d from) {
        return new Vector3d(x - from.x, y - from.y, z - from.z);
    }

}
