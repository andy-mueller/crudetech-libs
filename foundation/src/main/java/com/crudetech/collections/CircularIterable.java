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
import com.crudetech.lang.VerifyArgument;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;

/**
 * A collection api compatible circular {@link java.lang.Iterable} that lets you
 * iterate endlessly over a given {@link java.lang.Iterable} implementation.
 * When it reaches the end of the collection it will wrap around and
 * continue at the beginning again.
 */
public class CircularIterable<T> extends AbstractIterable<T> {
    private final Iterable<? extends T> iterable;

    public CircularIterable(Iterable<? extends T> iterable) {
        this.iterable = iterable;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Iterator<? extends T> iterator = iterable.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T rv = iterator.next();

                if (!hasNext()) {
                    iterator = iterable.iterator();
                }
                return rv;
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

    public static <T> CircularIterable<T> from(List<T> items) {
        VerifyArgument.isNotNull("items", items);
        return new CircularIterable<T>(items);
    }

    public static <T> CircularIterable<T> from(T... items) {
        if (items == null) {
            throw new ArgumentNullException("items");
        }
        return from(asList(items));
    }
}
