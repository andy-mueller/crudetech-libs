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

/**
 * When implemented it represents a entity that can be transformed using a
 * {@link com.crudetech.geometry.geom2d.Matrix2d}.
 */
public interface Transformable2d<T extends Transformable2d<T>> {
    T transformBy(Matrix2d xform);
}
