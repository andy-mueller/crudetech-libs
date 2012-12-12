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
package com.crudetech.algorithms;

import com.crudetech.collections.AbstractIterable;
import com.crudetech.collections.Iterables;
import com.crudetech.functional.NullaryFunction;

import java.util.Arrays;
import java.util.Iterator;

public class Builder {
    private Builder() {
    }

    public static <T> Iterable<T> concat(final Iterable<T>... ranges) {
        return Iterables.concat(ranges);
    }

    public static <T> Iterable<T> concat(final Iterable<Iterable<T>> ranges) {
        return Iterables.concat(ranges);
    }

    @SuppressWarnings("unchecked")
    public static <T> Iterable<T> concat(final Iterable<T> range, final T... additional) {
        return Builder.concat(range, Arrays.asList(additional));
    }


    public static <T> Iterable<T> generate(final NullaryFunction<T> creator, final int count) {
        return new AbstractIterable<T>() {
            public Iterator<T> iterator() {
                return new GenerateIterator<T>(creator, count);
            }
        };
    }
}
