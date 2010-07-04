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
package com.crudetech.geometry.geom;

/**
 * Represents the tolerance for all necessary floating point comparisons. All {@link Object#equals(Object)}
 * implementations in this library use a global instance of this object. The global tolrances are implementd
 * by {@link com.crudetech.geometry.geom2d.Tolerance2d} and {@link com.crudetech.geometry.geom3d.Tolerance3d}
 * Furthermore all comparison methods are overloaded taking an instance of this class.
 * <p/>
 * Changing the global tolerance will affect all object comparison and hashing operations inside this library, e.g.
 * you will not be able to find your objects inside a hash table anymore.
 * <p/>
 * This detail makes most classes in this package  extremely unsuitable as hash keys. If you
 * need to store instances of these objects in such a container, consider using
 * {@link com.crudetech.collections.EqualityComparable} or one of
 * the convenience wrappers like {@link com.crudetech.collections.EqualityComparableMap}
 * in conjunction with a {@link ToleranceComparer} instance.
 * <p/>
 * Setting the global tolerance is not thread safe!
 */
public class Tolerance {
    private final double vectorTolerance;
    private final double pointTolerance;

    /**
     * Constructs a tolerance instance.
     *
     * @param pointTolerance  The tolerance used to compare {@link com.crudetech.geometry.geom2d.Point2d} objects.
     * @param vectorTolerance The tolerance used to compare {@link com.crudetech.geometry.geom2d.Vector2d} objects.
     */
    public Tolerance(double pointTolerance, double vectorTolerance) {
        this.pointTolerance = pointTolerance;
        this.vectorTolerance = vectorTolerance;
    }

    /**
     * The tolerance used to compare {@link com.crudetech.geometry.geom2d.Vector2d} objects.
     *
     * @return
     */
    public double getVectorTolerance() {
        return vectorTolerance;
    }

    /**
     * The tolerance used to compare {@link com.crudetech.geometry.geom2d.Point2d} objects.
     *
     * @return
     */
    public double getPointTolerance() {
        return pointTolerance;
    }

}
