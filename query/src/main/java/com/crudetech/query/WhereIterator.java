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
package com.crudetech.query;

import com.crudetech.functional.UnaryFunction;

import java.util.Iterator;
import java.util.NoSuchElementException;


class WhereIterator<T> implements Iterator<T> {

    public WhereIterator(UnaryFunction<? super T, Boolean> selector, Iterator<T> inner) {
        this.selector = selector;
        this.inner = inner;
        moveNext();
    }
    private final UnaryFunction<? super T, Boolean> selector;
    private final Iterator<T> inner;
    private T next;
    private boolean hasNext = true;

    public boolean hasNext() {
        return hasNext;
    }

    public T next() {
        if (hasNext()) {
            T cur = next;
            moveNext();
            return cur;
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void moveNext() {
        while (inner.hasNext()) {
            T cur = inner.next();
            if (selector.execute(cur)) {
                next = cur;
                return;
            }
        }
        hasNext = false;
    }
}
