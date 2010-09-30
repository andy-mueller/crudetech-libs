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
package com.crudetech.query;

import java.util.Iterator;
import java.util.NoSuchElementException;


class SliceIterator<T> implements Iterator<T> {
    private final Iterator<T> inner;
    private int amount;
    private int start;

    public SliceIterator(Iterator<T> inner, int start, int amount) {
        this.inner = inner;
        this.amount = amount;
        this.start = start;
    }

    public boolean hasNext() {
        return validate() && amount > 0 && inner.hasNext();
    }

    public T next() {
        if(!validate()){
            throw new NoSuchElementException();
        }
        --amount;
        return inner.next();
    }

    private boolean validate() {
        if (start >= 0) {
            while ((--start) >= 0) {
                if(!inner.hasNext()){
                    return false;
                }
                inner.next();
            }
        }
        return true;
    }

    public void remove() {
        inner.remove();
    }
}
