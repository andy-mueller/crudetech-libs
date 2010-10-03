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
package com.crudetech.algorithms;

import com.crudetech.functional.NullaryFunction;

import java.util.Iterator;

class GenerateIterator<T> implements Iterator<T> {
    private final NullaryFunction<T> generator;
    private int count;

    public GenerateIterator(NullaryFunction<T> generator, int count) {
        super();
        this.generator = generator;
        this.count = count;
    }
    @Override
    public boolean hasNext() {
        return count > 0;
    }
    @Override
    public T next() {
        --count;
        return generator.execute();
    }
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
