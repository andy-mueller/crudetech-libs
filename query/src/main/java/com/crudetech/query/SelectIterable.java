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

import com.crudetech.collections.Iterables;
import com.crudetech.functional.UnaryFunction;

import java.util.Iterator;

class SelectIterable<From, To> implements Iterable<To>{
    private final Iterable<From> range;
    private final UnaryFunction<? super From, To> select;

    public SelectIterable(Iterable<From> range, UnaryFunction<? super From, To> select) {
        this.range = range;
        this.select = select;
    }

    @Override
    public Iterator<To> iterator() {
        return Iterables.transform(range.iterator(), select);
    }
}
