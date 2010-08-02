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


/**
 * Represents a point in 2D space.
 * <p/>
 * This class is not very suitable to serve as a hash key. For more details refer to the
 * {@link com.crudetech.geometry.geom2d.Point2d#equals(Object)} method.
 */
public final class Point2d implements ToleranceComparable<Point2d> {
    private final double x;
    private final double y;
    public static final Point2d Origin = new Point2d(0.0, 0.0);

    public Point2d() {
        this(0.0, 0.0);
    }

    public Point2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Two points are considered equal when their distance is less than
     * the point tolerance of the global tolerance object. This means that
     * changing the global tolerance object influences the results of
     * equals and {@link Point2d#hashCode()}.
     * <p/>
     * This detail makes this class extremely unsuitable as a hash keys. If you
     * need to store instances of these objects in such a container, consider using
     * {@link com.crudetech.collections.EqualityComparable} or one of
     * the convenience collection wrappers like {@link com.crudetech.collections.EqualityComparableMap}
     * in conjunction with a {@link com.crudetech.geometry.geom.ToleranceComparer} instance.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2d point2d = (Point2d) o;

        return equals(point2d, Tolerance2d.getGlobalTolerance());
    }
    /**
     * Two points are considered equal when their distance is less than
     * the point tolerance of the passed in tolerance object.
     *
     * @param rhs
     * @param tol
     * @return
     */
    public boolean equals(Point2d rhs, Tolerance tol) {
        if (rhs == null) return false;
        if (tol == null) throw new IllegalArgumentException();
        return distanceTo(rhs) < tol.getPointTolerance();
    }

    /**
     * Computes the hash code based on the global tolerance object.
     * Computes the hash code based on the global tolerance object.
     * @return
     */
    @Override
    public int hashCode() {
        return hashCode(Tolerance2d.getGlobalTolerance());
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "Point2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int hashCode(Tolerance tol) {
        return FloatCompare.hashCode(new double[]{x, y}, tol.getPointTolerance());
        //IEqualityComparer<PointF>
//        int result;
//        long temp;
//        temp = !FloatCompare.equals(x, 0.0, getTolerance()) ? Double.doubleToLongBits(x) : 0L;
//        result = (int) (temp ^ (temp >>> 32));
//        temp = !FloatCompare.equals(y, 0.0, getTolerance()) ? Double.doubleToLongBits(y) : 0L;
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        return result;
    }

    public double distanceTo(Point2d other) {
        return other.subtract(this).getLength();
    }

    public Point2d add(Vector2d v) {
        return new Point2d(x + v.getX(), y + v.getY());
    }

    public Vector2d subtract(Point2d from) {
        return new Vector2d(x - from.x, y - from.y);
    }

    public Vector2d toVector2d() {
        return new Vector2d(x, y);
    }

    public Point2d transformBy(Matrix2d matrix2d) {
        return matrix2d.multiply(this);
    }
}
