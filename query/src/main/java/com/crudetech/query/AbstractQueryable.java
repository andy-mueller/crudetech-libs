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

import com.crudetech.collections.AbstractIterable;
import com.crudetech.functional.UnaryFunction;

import java.util.Iterator;


abstract class AbstractQueryable<T> extends AbstractIterable<T> implements Queryable<T> {
    public abstract Iterator<T> iterator();

    @Override
    public SliceFluent<T> slice(final int start) {
        return new SliceFluent<T>() {
            @Override
            public Queryable<T> amount(int amount) {
                return slice(start, amount);
            }

            @Override
            public Queryable<T> to(int last) {
                return slice(start, last - start);
            }
        };
    }

    @Override
    public Queryable<T> take(int amount) {
        return slice(0, amount);
    }

    protected abstract Queryable<T> slice(int start, int amount);


    @Override
    public boolean any() {
        return iterator().hasNext();
    }

    @Override
    public T lastOr(T defaultValue) {
        if (!any()) {
            return defaultValue;
        }
        return last();
    }

    @Override
    public T first() {
        if (!any()) {
            throw new IllegalStateException();
        }
        return iterator().next();
    }

    @Override
    public T firstOr(T defaultValue) {
        if (!any()) {
            return defaultValue;
        }
        return first();
    }
}
