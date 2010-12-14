////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
// Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.query;

import com.crudetech.functional.BinaryFunction;

import java.util.Iterator;


class SelectWithIndexIterator<From, To> implements Iterator<To> {
    private final Iterator<From> inner;
    private final BinaryFunction<? super From, Integer, To> select;
    private int idx = 0;

    public SelectWithIndexIterator(Iterator<From> inner, BinaryFunction<? super From, Integer, To> select) {
        this.inner = inner;
        this.select = select;
    }

    public boolean hasNext() {
        return inner.hasNext();
    }

    public To next() {
        return select.execute(inner.next(), idx++);
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }}
