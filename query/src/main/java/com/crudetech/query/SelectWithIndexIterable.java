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


import com.crudetech.functional.BinaryFunction;

import java.util.Iterator;

public class SelectWithIndexIterable<From, To> implements Iterable<To> {
    private final Iterable<From> range;
    private final BinaryFunction<? super From, Integer, To> select;

    public SelectWithIndexIterable(Iterable<From> range, BinaryFunction<? super From, Integer, To> select) {
        this.range = range;
        this.select = select;
    }

    @Override
    public Iterator<To> iterator() {
        return new Iterator<To>() {
            private final Iterator<From> inner = range.iterator();
            private int idx = 0;

            public boolean hasNext() {
                return inner.hasNext();
            }

            public To next() {
                return select.execute(inner.next(), idx++);
            }

            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
}
