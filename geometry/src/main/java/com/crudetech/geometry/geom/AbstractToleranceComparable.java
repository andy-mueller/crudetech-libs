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
 * Convenience base class that takes care of implementing {@link Object#hashCode()}
 * and {@link Object#equals(Object)} correctly for a {@link com.crudetech.geometry.geom.ToleranceComparable}
 * implementation.
 */
public abstract class AbstractToleranceComparable<TolComp extends ToleranceComparable<TolComp>> implements ToleranceComparable<TolComp> {
    @Override
    public abstract int hashCode(Tolerance tol);

    @Override
    public abstract boolean equals(TolComp rhs, Tolerance tol);

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TolComp that = (TolComp) o;
        return equals(that, getGlobalTolerance());
    }

    @Override
    public final int hashCode() {
        return hashCode(getGlobalTolerance());
    }

    /**
     * gives access to the correct global {@link Tolerance} instance
     * for this objects type.
     * @return
     */
    protected abstract Tolerance getGlobalTolerance();
}
