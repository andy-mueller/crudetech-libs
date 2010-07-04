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

import com.crudetech.lang.EqualityComparer;

public class ToleranceComparer<T extends ToleranceComparable<T>> implements EqualityComparer<T> {
    private final Tolerance tolerance;

    public ToleranceComparer(Tolerance tolerance) {
        this.tolerance = tolerance;
    }

    @Override
    public boolean equals(T lhs, T rhs) {
        if(lhs == null) return rhs == null;
        return lhs.equals(rhs, tolerance);
    }

    @Override
    public int hashCode(T item) {
        return item.hashCode(tolerance);
    }
}
