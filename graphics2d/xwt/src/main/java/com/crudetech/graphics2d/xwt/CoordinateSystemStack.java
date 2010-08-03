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

import com.crudetech.geometry.geom2d.Matrix2d;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.geometry.geom2d.Vector2d;


/**
 * When implemented, it represents a transformation stack. Its main difference to
 * a normal stack like {@link com.crudetech.collections.LightweightStack} is, that
 * pushing a new transformation on top results the pushed matrix being multiplied
 * with the peek matrix. Therefore a call to {@link CoordinateSystemStack#peek()} will
 * give you the overall transform of all former transformations.
 * <p>
 * Every pushed transformation is applied to the top most coordinate system
 * by post multiplying the pushed transformation. Therefore the stack represents
 * a series of coordinate system transforms, where the top most is the basis for
 * the next step. This behaviour is the same as in object coordinate system
 * using frameworks such as OpenGL.
 */
public interface CoordinateSystemStack extends Iterable<Matrix2d>{
    /**
     * Accesses the top most entry of the stack representing all merged
     * transformation on the stack.
     * 
     * @return The topmost entry on this stack.
     */
    Matrix2d peek();

    /**
     * Removes the top entry from this stack.
     */
    void pop();

    /**
     * Pushes an element on the top of this stack. The passed matrix will
     * be post multiplied to the former peek matrix.
     *
     * @param item The element to be pushed on the stack.
     */
    void pusXForm(Matrix2d item);

    boolean isEmpty();

    Matrix2d[] toArray();

    void pushTranslation(Vector2d translation);
    void pushTranslation(double dx, double dy);

    void pushRotationInRadians(double angle);

    /**
     * Pushes a scale operation with the stacks current translation as a center point
     * @param scaleX
     * @param scaleY
     */
    void pushScale(double scaleX, double scaleY);
    void pushScale(double scaleX, double scaleY, Point2d center);

}
