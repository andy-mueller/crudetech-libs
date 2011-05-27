////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2011, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//      Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.collections;

import com.crudetech.lang.ArgumentNullException;
import com.crudetech.lang.EqualityComparer;


/**
 * A simple decorator that uses the supplied {@link EqualityComparer}
 * implementation to compare the wrapped object. This class
 * is usually not used directly, but rather as a key wrapper
 * of {@link EqualityComparableMap}.
 * @param <T>
 */
public class EqualityComparable<T> {
    private final EqualityComparer<? super T> equComp;
    private final T wrapped;

    public EqualityComparable(EqualityComparer<? super T> equComp, T wrapped) {
        if (equComp == null) throw new ArgumentNullException("equComp");
        this.equComp = equComp;
        this.wrapped = wrapped;
    }

    public T getWrapped() {
        return wrapped;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        @SuppressWarnings("unchecked")
        EqualityComparable<T> that = (EqualityComparable<T>) o;
        return equComp.equals(this.wrapped, that.wrapped);
    }

    @Override
    public int hashCode() {
        return equComp.hashCode(wrapped);
    }
}
