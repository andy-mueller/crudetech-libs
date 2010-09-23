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
package com.crudetech.collections;

import com.crudetech.lang.Compare;

/**
 * Simple base class for {@link Iterable}s that provides an {@link java.util.Arrays} like
 * implementation of {@link Object#toString()}, {@link Object#hashCode()} and {@link Object#equals(Object)}.
 * @param <T>
 */
public abstract class AbstractIterable<T> implements Iterable<T> {
    @Override
    public int hashCode() {
        return Iterables.hashCode(this);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractIterable that = (AbstractIterable) o;

        return Compare.equals(this, that);
    }
    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return Iterables.toString(this);
    }
}
