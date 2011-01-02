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
import com.crudetech.collections.Iterables;
import com.crudetech.functional.BinaryFunction;
import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.ArgumentNullException;
import com.crudetech.lang.ArgumentOutOfBoundsException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.crudetech.collections.Iterables.copy;


abstract class AbstractQueryable<T> extends AbstractIterable<T> implements Queryable<T> {
    public abstract Iterator<T> iterator();

    @Override
    public <U> Queryable<U> select(final UnaryFunction<? super T, U> select) {
        if (select == null) throw new ArgumentNullException("select");
        return new AbstractQueryable<U>() {
            @Override
            public Iterator<U> iterator() {
                return Iterables.transform(AbstractQueryable.this.iterator(), select);
            }
        };
    }

    @Override
    public <U> Queryable<U> select(final BinaryFunction<? super T, Integer, U> select) {
        if (select == null) throw new ArgumentNullException("select");
        return new AbstractQueryable<U>() {
            @Override
            public Iterator<U> iterator() {
                return new SelectWithIndexIterator<T, U>(AbstractQueryable.this.iterator(), select);
            }
        };
    }

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

    public Queryable<T> slice(final int start, final int amount) {
        if (start < 0) throw new ArgumentOutOfBoundsException("start");
        if (amount < 0) throw new ArgumentOutOfBoundsException("amount");

        return new AbstractQueryable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new SliceIterator<T>(AbstractQueryable.this.iterator(), start, amount);
            }
        };
    }

    @Override
    public Queryable<T> take(int amount) {
        return slice(0, amount);
    }

    @Override
    public Queryable<T> where(final UnaryFunction<? super T, Boolean> filter) {
        if (filter == null) throw new ArgumentNullException("filter");
        return new AbstractQueryable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new WhereIterator<T>(filter, AbstractQueryable.this.iterator());
            }
        };
    }


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
    public T last() {
        if (!any()) {
            throw new IllegalStateException();
        }
        T val = null;
        for (T t : this) {
            val = t;
        }
        return val;
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

    @Override
    public <U> Queryable<U> cast(Class<U> targetClass) {
        UnaryFunction<T, U> cast = new UnaryFunction<T, U>() {
            @Override
            public U execute(T t) {
                @SuppressWarnings("unchecked") U u = (U)t;
                return u;
            }
        };
        return select(cast);
    }

    @Override
    public T[] toArray(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T[] rv = (T[]) Array.newInstance(clazz, Iterables.size(this));
        int pos = 0;

        for (T item : this) {
            rv[pos++] = item;
        }
        return rv;
    }

    @Override
    public List<T> toList() {
        ArrayList<T> targetList = new ArrayList<T>();

        copy(this, targetList);

        targetList.trimToSize();
        return targetList;
    }
}
