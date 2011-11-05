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
package com.crudetech.lang;

import com.crudetech.collections.AbstractIterable;
import java.util.Iterator;

/**
 *
 */
public class Enums {
    public static <T extends Enum<T>> T ofOrdinal(Class<T> enumClass, int ordinal) {
        T[] enumConstants = enumClass.getEnumConstants();
        if (ordinal < 0 || ordinal >= enumConstants.length) {
            throw new IllegalArgumentException();
        }
        return enumConstants[ordinal];
    }

    public static <T extends Enum<T>> Iterable<T> iterableOf(final Class<T> enumClass) {
        return iterableOf(enumClass, 0);
    }
    public static <T extends Enum<T>> Iterable<T> iterableOf(final Class<T> enumClass, final int ordinal) {
        return new AbstractIterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    int currentOrdinal = ordinal;
                    @Override
                    public boolean hasNext() {
                        return currentOrdinal < enumClass.getEnumConstants().length;
                    }

                    @Override
                    public T next() {
                        return enumClass.getEnumConstants()[currentOrdinal++];
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
    public static <T extends Enum<T>> Iterable<T> iterableOf(final T value) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>)value.getClass();
        return iterableOf(clazz, value.ordinal());
    }
}
