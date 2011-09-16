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

/**
 * Extension of {@link BoundedValue} to implement {@link Comparable}.
 * <pre>
 *     class DiceRoll extends ComparableBoundedValue&lt;Integer, DiceRoll&gt;{
 *
 *     }
 * </pre>
 * @param <T> The type of that is wrapped by this bounded value
 * @param <Derived> The concrete deriving class
 */
public abstract class ComparableBoundedValue<T extends Comparable<T>, Derived extends ComparableBoundedValue<T, Derived>> extends BoundedValue<T> implements Comparable<Derived> {

    public ComparableBoundedValue(T value, Comparable<? super T> lower, Comparable<? super T> upper) {
        super(value, lower, upper);
    }


    public boolean isEqualTo(Derived rhs) {
        return compareTo(rhs) == 0;
    }

    public boolean isNotEqualTo(Derived rhs) {
        return compareTo(rhs) != 0;
    }

    public boolean isLessThan(Derived rhs) {
        return compareTo(rhs) < 0;
    }

    public boolean isGreaterThan(Derived rhs) {
        return compareTo(rhs) > 0;
    }

    public boolean isGreaterEqualThan(Derived rhs) {
        return compareTo(rhs) >= 0;
    }

    public boolean isLessEqualThan(Derived rhs) {
        return compareTo(rhs) <= 0;
    }

    @Override
    public int compareTo(Derived rhs) {
        if (getClass() != rhs.getClass()) {
            throw new IllegalArgumentException("rhs");
        }

        return getValue().compareTo(rhs.getValue());
    }

}
