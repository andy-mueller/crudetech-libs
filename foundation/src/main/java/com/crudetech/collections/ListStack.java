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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * A simple stack implementation based on a {@link java.util.List}.
 *
 * @param <T>
 */
public class ListStack<T> implements LightweightStack<T> {
    private final List<T> inner;

    /**
     * construct a stack containing the given elements in the iterable.
     *
     * @param content The elements to be copied into this stack.
     */
    public ListStack(Iterable<T> content) {
        this();
        for (T t : content) {
            inner.add(t);
        }
    }

    /**
     * Takes ownership of the given list and uses it as
     * its internal buffer;
     *
     * @param buffer The list to be used as the internal buffer.
     */
    public ListStack(List<T> buffer) {
        this.inner = buffer;
    }
    public ListStack(T... content){
        this(new ArrayList<T>(asList(content)));
    }

    public ListStack() {
        this.inner = new ArrayList<T>();
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return inner.get(inner.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public T[] toArray(Class<T> clazz) {
        T[] a = (T[]) Array.newInstance(clazz, inner.size());
        int pos = 0;
        for (T t : inner) {
            a[pos++] = t;
        }
        return a;
    }

    @Override
    public void pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        inner.remove(inner.size() - 1);
    }

    @Override
    public void push(T item) {
        inner.add(item);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final Iterator<T> it = inner.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public T next() {
                return it.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
