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

import com.crudetech.lang.Compare;

/**
 * A simple pair class that references two other objects.<br/>
 *
 * This class is designed as a simple data carrier and does not
 * take any actions to be truly immutable like cloning its values.
 */
public class Pair<F, S> {
    private final F first;
    private final S second;

    public Pair(final F first, final S second) {
        this.second = second;
        this.first = first;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        return Compare.equals(first, pair.first)
            && Compare.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        int result = Compare.hashCode(first);
        result = 31 * result + Compare.hashCode(second);
        return result;
    }
}
