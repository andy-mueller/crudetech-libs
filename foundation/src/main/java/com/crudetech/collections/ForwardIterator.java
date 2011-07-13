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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple GoF compatible iterator interface. While the jdk
 * {@link java.util.Iterator} interface is designed to write short
 * loop code, this interface is intended to be used when multiple access to
 * the current value is required. A typical use case to implement
 * a cursor to the current element of a collection.
 * <p/>
 * If you need an implementation based on a jdk collection, consider
 * using the embedded {@link ForwardIterator#Builder}.
 *
 * @param <T>
 */
public interface ForwardIterator<T> {
    boolean hasNext();

    boolean moveNext();

    T getCurrent();

    public static class Builder {
        public <T> ForwardIterator<T> from(final Iterable<? extends T> values) {
            if (values == null) {
                throw new ArgumentNullException("values");
            }
            return from(values.iterator());
        }

        public <T> ForwardIterator<T> from(final Iterator<? extends T> values) {
            if (values == null) {
                throw new ArgumentNullException("values");
            }
            return new ForwardIterator<T>() {
                private final Iterator<? extends T> inner = values;
                private T current;
                private boolean done = false;

                @Override
                public T getCurrent() {
                    if (done) {
                        throw new NoSuchElementException();
                    }
                    return current;
                }

                public boolean hasNext() {
                    return inner.hasNext();
                }

                public boolean moveNext() {
                    if (!hasNext()) {
                        done = true;
                        return false;
                    }
                    current = inner.next();
                    return true;
                }
            };
        }
    }

    public static final Builder Builder = new Builder();
}
