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
 * Some methods to operate on or implement enums.
 */
public class Enums {
    /**
     * Finds the enum value for the ordinal.
     * @param enumClass
     * @param ordinal
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T ofOrdinal(Class<T> enumClass, int ordinal) {
        T[] enumConstants = enumClass.getEnumConstants();
        if (ordinal < 0 || ordinal >= enumConstants.length) {
            throw new IllegalArgumentException();
        }
        return enumConstants[ordinal];
    }

    /**
     * Creates an {@link Iterable} over all enum values.
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> Iterable<T> iterableOf(final Class<T> enumClass) {
        return iterableOf(enumClass, 0);
    }

    /**
     * Creates an {@link Iterable} over the enum values starting at a specific ordinal value.
     * @param enumClass
     * @param ordinal
     * @param <T>
     * @return
     */
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

    /**
     * Creates an {@link Iterable} over the enum values starting at the given value.
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> Iterable<T> iterableOf(final T value) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>)value.getClass();
        return iterableOf(clazz, value.ordinal());
    }
}
