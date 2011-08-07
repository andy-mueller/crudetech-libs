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
import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.ArgumentNullException;
import com.crudetech.lang.ArgumentOutOfBoundsException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ListQueryable<T> extends AbstractQueryable<T> {
    private final List<T> list;

    protected ListQueryable(List<T> list) {
        this.list = list;
    }

    private ListQueryable() {
        this(Collections.<T>emptyList());
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    protected Queryable<T> slice(int start, int amount) {
        if (amount < 0) {
            throw new ArgumentOutOfBoundsException("amount");
        }
        if (start < 0) {
            throw new ArgumentOutOfBoundsException("start");
        }
        final int end = start + amount;
        if (end >= list.size()) {
            return new ListQueryable<>();
        }
        return new ListQueryable<>(list.subList(start, end));
    }

    @Override
    public <U> Queryable<U> select(UnaryFunction<? super T, U> select) {
        return new ListQueryable<>(new SelectList<>(list, select));
    }

    @Override
    public <U> Queryable<U> select(BinaryFunction<? super T, Integer, U> select) {
        return new ListQueryable<>(new SelectWithIndexList<>(list, select));
    }

    @Override
    public Queryable<T> where(final UnaryFunction<? super T, Boolean> filter) {
        if (filter == null) {
            throw new ArgumentNullException("filter");
        }
        return new IterableQueryable<T>(new WhereIterable(list, filter));
    }

    @Override
    public <U> Queryable<U> cast(Class<U> targetClass) {
        UnaryFunction<T, U> cast = new UnaryFunction<T, U>() {
            @Override
            public U execute(T t) {
                @SuppressWarnings("unchecked") U u = (U) t;
                return u;
            }
        };
        return new ListQueryable<>(new SelectList<>(list, cast));
    }

    @Override
    public T last() {
        if (list.isEmpty()) {
            throw new IllegalStateException();
        }
        return list.get(list.size() - 1);
    }

    @Override
    public T[] toArray(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(clazz, list.size());
        return list.toArray(array);
    }

    @Override
    public List<T> toList() {
        return new ArrayList<>(list);
    }
}
