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

class WhereIterable<T> implements Iterable<T>{
    private final UnaryFunction<? super T, Boolean> selector;
    private final Iterable<T> range;

    WhereIterable(Iterable<T> range, UnaryFunction<? super T, Boolean> selector) {
        this.range = range;
        this.selector = selector;
    }
    @Override
    public java.util.Iterator<T> iterator() {
        return new WhereIterator(selector, range.iterator());
    }
}
