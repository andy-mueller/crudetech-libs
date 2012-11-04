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


import java.io.Serializable;

/**
 * A simple value type that has a fixed range defined by an an open interval,
 * where the lower bound is inside and the upper bound is outside of the range.
 * <p/>
 * For instance the possible values of a dice could be modeled as:
 * <pre>
 *     class Dice{
 *         private final Random rand = new Random();
 *         static class Roll extends BoundedValue&lt;Integer&gt;{
 *             private Roll(int roll){
 *                 super(roll, 1, 7);//could be between 1 and 6
 *            }
 *         }
 *         Roll roll(){
 *              return new Roll(random.nextInt(6)+1);
 *         }
 *     }
 *
 *     Dice dice = new Dice();
 *     Dice.Roll roll = dice.roll();
 *
 *     // will throw ArgumentOutOfBoundsException:
 *     Dice.Roll four = new Dice.Roll(7);
 * </pre>
 * In contrast to an enum, this class is useful when you want
 * to model a specific range that has semantics to your domain,
 * but you are not interested in the actual values.
 * <b>
 * This class is serializable, as long as the wrapped class is
 * serializable.
 */
public abstract class BoundedValue<T> implements Serializable {
    private final T value;

    public BoundedValue(T value, Comparable<? super T> lower, Comparable<? super T> upper) {
        if (value == null) {
            throw new ArgumentNullException("value");
        }
        if (lower == null) {
            throw new ArgumentNullException("lower");
        }
        if (upper == null) {
            throw new ArgumentNullException("upper");
        }
        if (isNotInsideLowerBound(value, lower)) {
            throw new ArgumentOutOfBoundsException("value", "The argument is below the lower bound!");
        }
        if (isNotInUpperBound(value, upper)) {
            throw new ArgumentOutOfBoundsException("value", "The argument is on or above the upper bound!");
        }

        this.value = value;
    }
    private boolean isNotInUpperBound(T value, Comparable<? super T> upper) {
        return upper.compareTo(value) <= 0;
    }

    private boolean isNotInsideLowerBound(T value, Comparable<? super T> lower) {
        return lower.compareTo(value) > 0;
    }

    public T getValue() {
        return value;
    }

    /**
     * Checks if this objects value is equal to the passed in parameter.
     *
     * @param rhs The argument to test against.
     * @return TRUE if this objects value is equal to the passed in parameter.
     */
    public boolean isEqualTo(T rhs) {
        return Compare.equals(value, rhs);
    }

    public boolean isNotEqualTo(T rhs) {
        return !isEqualTo(rhs);
    }

    public boolean isLessThan(Comparable<? super T> rhs) {
        return rhs.compareTo(getValue()) > 0;
    }

    public boolean isLessEqualThan(Comparable<? super T> rhs) {
        return rhs.compareTo(getValue()) >= 0;
    }

    public boolean isGreaterThan(Comparable<? super T> rhs) {
        return rhs.compareTo(getValue()) < 0;
    }


    public boolean isGreaterEqualThan(Comparable<? super T> rhs) {
        return rhs.compareTo(getValue()) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoundedValue<?> that = (BoundedValue<?>) o;

        return Compare.equals(value, that.value);

    }

    @Override
    public int hashCode() {
        return Compare.hashCode(value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
                "value=" + value +
                '}';
    }
}
