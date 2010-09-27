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

import com.crudetech.functional.UnaryFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ConcatIterable<T> extends AbstractIterable<T> {
    private final Iterable<Iterable<T>> iterables;

    public ConcatIterable(Iterable<Iterable<T>> iterables) {
        this.iterables = iterables;

    }

    private UnaryFunction<Iterable<T>, Iterator<T>> extractIterators() {
        return new UnaryFunction<Iterable<T>, Iterator<T>>() {
            @Override
            public Iterator<T> execute(Iterable<T> iterable) {
                return iterable != null ? iterable.iterator() : new EmptyIterator<T>();
            }
        };
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final Iterator<Iterator<T>> iters = Iterables.transform(iterables, extractIterators()).iterator();
            private Iterator<T> current;

            @Override
            public boolean hasNext() {
                if (!hasNext(current)) {
                    while (iters.hasNext()) {
                        current = iters.next();
                        if (hasNext(current)) {
                            break;
                        }
                    }
                    return hasNext(current);
                }
                return hasNext(current);
            }

            private boolean hasNext(Iterator<T> iterator) {
                return iterator != null && iterator.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return current.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove is not supported yet!");
            }
        };
    }
}
