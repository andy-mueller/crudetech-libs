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
import com.crudetech.functional.UnaryFunction;


public interface Queryable<T> extends Iterable<T>{
    <U> Queryable<U> select(UnaryFunction<? super T, U> select);

    <U> Queryable<U> select(BinaryFunction<? super T, Integer, U> select);

    SliceFluent<T> slice(int start);

    Queryable<T> take(int amount);

    Queryable<T> where(UnaryFunction<? super T, Boolean> filter);

    T lastOr(T defaultItem);
    T last();

    boolean any();

    T firstOr(T defaultValue);
    T first();

    <U> Queryable<U> cast(Class<U> targetClass);

    public interface SliceFluent<T> {
        Queryable<T> amount(int amount);

        Queryable<T> to(int last);
    }

    T[] toArray(Class<T> clazz);
}
