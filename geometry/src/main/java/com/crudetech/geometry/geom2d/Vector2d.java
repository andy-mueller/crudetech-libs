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
package com.crudetech.geometry.geom2d;

import com.crudetech.geometry.geom.FloatCompare;
import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.geometry.geom.ToleranceComparable;

public final class Vector2d implements ToleranceComparable<Vector2d> {
    private final double x;
    private final double y;
    public static final Vector2d xAxis = new Vector2d(1.0, 0);
    public static final Vector2d yAxis = new Vector2d(0, 1.0);

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d() {
        this(0.0, 0.0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2d vector2d = (Vector2d) o;

        return equals(vector2d, Tolerance2d.getGlobalTolerance());
    }

    @Override
    public int hashCode(Tolerance tol) {
        double tolFactor = 1 / tol.getVectorTolerance();
        int result = !FloatCompare.equals(x, 0.0, tol.getVectorTolerance()) ? double2int(x * tolFactor) : 0;
        result = 31 * result + (!FloatCompare.equals(y, 0.0, tol.getVectorTolerance()) ? double2int(y * tolFactor) : 0);
        return result;

//        int result;
//        long temp;
//        temp = !FloatCompare.equals(x, 0.0, getTolerance()) ? Double.doubleToLongBits(x) : 0L;
//        result = (int) (temp ^ (temp >>> 32));
//        temp = !FloatCompare.equals(y, 0.0, getTolerance()) ? Double.doubleToLongBits(y) : 0L;
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        return result;
    }

    public boolean equals(Vector2d rhs, Tolerance tol) {
        if (rhs == null) return false;
        if (tol == null) throw new IllegalArgumentException();
        return FloatCompare.equals(x, rhs.x, tol.getPointTolerance())
                && FloatCompare.equals(y, rhs.y, tol.getPointTolerance());
    }

    private int long2int(long l) {
        return (int) (l ^ (l >>> 32));
    }

    private int double2int(double d) {
        return long2int((long) d);
    }

    @Override
    public int hashCode() {
        return hashCode(Tolerance2d.getGlobalTolerance());
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "Vector2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Vector2d add(Vector2d rhs) {
        return new Vector2d(x + rhs.x, y + rhs.y);
    }

    public Point2d toPoint2d() {
        return new Point2d(x, y);
    }

    public Vector2d scalarProductWith(int scalar) {
        return new Vector2d(x * scalar, y * scalar);
    }

    public double getLength() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector2d negate() {
        return new Vector2d(-x, -y);
    }
}
