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

import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.geometry.geom.ToleranceComparable;
import com.crudetech.lang.ArgumentNullException;

/**
 * A bondingBox defines a rectangular interval in 2d coordinate system.
 */
public class BoundingBox2d implements ToleranceComparable<BoundingBox2d>, Transformable2d<BoundingBox2d> {
    private final Point2d lowerLeft;
    private final Point2d upperRight;

    public BoundingBox2d(Point2d lowerLeft, Point2d upperRight) {
        if (lowerLeft == null) throw new ArgumentNullException("lowerLeft");
        if (upperRight == null) throw new ArgumentNullException("lowerLeft");
        if (lowerLeft.getX() >= upperRight.getX() || lowerLeft.getY() >= upperRight.getY()) {
            throw new IllegalArgumentException();
        }

        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public BoundingBox2d(double x, double y, double width, double height) {
        this(new Point2d(x, y), new Point2d(x + width, y + height));
    }

    Point2d getBottomLeft() {
        return lowerLeft;
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "BoundingBox2d{" +
                "lowerLeft=" + lowerLeft +
                ", upperRight=" + upperRight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoundingBox2d that = (BoundingBox2d) o;

        return equals(that, Tolerance2d.getGlobalTolerance());
    }

    @Override
    public int hashCode() {
        return hashCode(Tolerance2d.getGlobalTolerance());
    }

    @Override
    public int hashCode(Tolerance tol) {
        int result = lowerLeft.hashCode(tol);
        result = 31 * result + upperRight.hashCode(tol);
        return result;
    }

    @Override
    public boolean equals(BoundingBox2d rhs, Tolerance tol) {
        if (!lowerLeft.equals(rhs.lowerLeft, tol)) return false;
        if (!upperRight.equals(rhs.upperRight, tol)) return false;

        return true;
    }

    public double getWidth() {
        return upperRight.getX() - lowerLeft.getX();
    }

    public double getHeight() {
        return upperRight.getY() - lowerLeft.getY();
    }

    public Point2d getLowerLeft() {
        return lowerLeft;
    }

    public Point2d getUpperRight() {
        return upperRight;
    }

    @Override
    public BoundingBox2d transformBy(Matrix2d xform) {
        return new BoundingBox2d(lowerLeft.transformBy(xform), upperRight.transformBy(xform));
    }

    public BoundingBox2d add(Point2d point2d) {
        final double x1 = point2d.getX() < lowerLeft.getX() ? point2d.getX() : lowerLeft.getX();
        final double y1 = point2d.getY() < lowerLeft.getY() ? point2d.getY() : lowerLeft.getY();

        final double x2 = point2d.getX() > upperRight.getX() ? point2d.getX() : upperRight.getX();
        final double y2 = point2d.getY() > upperRight.getY() ? point2d.getY() : upperRight.getY();

        return new BoundingBox2d(new Point2d(x1, y1), new Point2d(x2, y2));
    }

    public boolean contains(Point2d point) {
        return !doesNotContain(point);

    }
    private boolean doesNotContain(Point2d point) {
        return (point.getX() < lowerLeft.getX() || point.getX() > upperRight.getX())
            || (point.getY() < lowerLeft.getY() || point.getY() > upperRight.getY());
    }

    public Point2d getUpperLeft() {
        return new Point2d(lowerLeft.getX(), upperRight.getY());
    }
    public Point2d getLowerRight() {
        return new Point2d(upperRight.getX(), lowerLeft.getY());
    }
}
