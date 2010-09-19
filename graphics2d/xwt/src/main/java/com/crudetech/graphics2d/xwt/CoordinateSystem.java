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
package com.crudetech.graphics2d.xwt;

import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.geometry.geom.ToleranceComparable;
import com.crudetech.geometry.geom2d.Matrix2d;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.geometry.geom2d.Transformable2d;
import com.crudetech.geometry.geom2d.Vector2d;

/**
 * Represents a coordinate system in 2d space.
 * <p>
 * Please nte that this class is not.
 */
public class CoordinateSystem implements ToleranceComparable<CoordinateSystem> {
    private Matrix2d mx = Matrix2d.Identity;

    public CoordinateSystem(Point2d location, double rotationInRadians) {
        setLocationAndRotationInRadians(location, rotationInRadians);
    }

    /**
     * The position of this coordinate system.
     *
     * @return
     */
    public Point2d getLocation() {
        return mx.getTranslation().toPoint2d();
    }

    /**
     * The rotation around the zAxis of this coordinate system.
     *
     * @return
     */
    public double getRotation() {
        Vector2d xAxis = mx.getXAxis();
        return Vector2d.xAxis.angleTo(xAxis);
    }

    /**
     * Transforms the given {@link com.crudetech.geometry.geom2d.Transformable2d} into the
     * world coordinate system.
     *
     * @param transformableInCoordinateSystem
     *
     * @param <T>
     * @return
     */
    public <T extends Transformable2d<T>> T fromCoordinateSystemToWorld(T transformableInCoordinateSystem) {
        return transformableInCoordinateSystem.transformBy(mx);
    }

    /**
     * Transforms the given {@link com.crudetech.geometry.geom2d.Transformable2d} from the world
     * into this coordinate system.
     *
     * @param transformableInWcs
     * @param <T>
     * @return
     */
    public <T extends Transformable2d<T>> T fromWorldToCoordinateSystem(T transformableInWcs) {
        return transformableInWcs.transformBy(mx.inverse());
    }

    public void setLocation(Point2d location) {
        setLocationAndRotationInRadians(location, getRotation());
    }

    public void setRotationInRadians(double rotationInRadians) {
        setLocationAndRotationInRadians(getLocation(), rotationInRadians);
    }

    public void setLocationAndRotationInRadians(Point2d location, double rotationInRadians) {
        mx = Matrix2d.createTranslationAndRotationInRadians(location.toVector2d(), rotationInRadians);
    }

    @Override
    public int hashCode(Tolerance tol) {
        return mx.hashCode(tol);
    }

    @Override
    public boolean equals(CoordinateSystem rhs, Tolerance tol) {
        return mx.equals(rhs.mx, tol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoordinateSystem that = (CoordinateSystem) o;

        if (!mx.equals(that.mx)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mx.hashCode();
    }

    public Matrix2d asMatrix() {
        return mx;
    }
}
