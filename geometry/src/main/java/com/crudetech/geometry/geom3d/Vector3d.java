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


public final class Vector3d implements ToleranceComparable<Vector3d>, Transformable3d<Vector3d> {
    private final double x;
    private final double y;
    private final double z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getLength() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public double getZ() {
        return z;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "Vector3d{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public int hashCode(Tolerance tol) {
        return FloatCompare.hashCode(new double[]{x, y, z}, tol.getVectorTolerance());
    }

    @Override
    public boolean equals(Vector3d rhs, Tolerance tol) {
         if (rhs == null) return false;
        if (tol == null) throw new ArgumentNullException("tol");
        return Math.abs(subtract(rhs).getLength()) < tol.getVectorTolerance();
    }

    private Vector3d subtract(Vector3d rhs) {
        return new Vector3d(x - rhs.x, y - rhs.y, z - rhs.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3d vector3d = (Vector3d) o;

        return equals(vector3d, Tolerance3d.getGlobalTolerance());
    }

    @Override
    public int hashCode() {
        return hashCode(Tolerance3d.getGlobalTolerance());
    }

    @Override
    public Vector3d transformBy(Matrix3d xform) {
        return xform.multiply(this);
    }
}
