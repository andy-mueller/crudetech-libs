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
 */
public abstract class BoundedValue<T extends Comparable<T>, Derived extends BoundedValue<T, Derived>> implements Comparable<Derived> {
    private final T value;
    private final Comparable<? super T> lower;
    private final Comparable<? super T> upper;

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
        if (isLess(value, lower)) {
            throw new ArgumentOutOfBoundsException("value", "The argument is below the lower bound!");
        }
        if (isGreaterOrEqual(value, upper)) {
            throw new ArgumentOutOfBoundsException("value", "The argument is on or above the upper bound!");
        }
        this.value = value;
        // just for debugging
        this.lower = lower;
        this.upper = upper;
    }

    private boolean isGreaterOrEqual(T value, Comparable<? super T> upper) {
        return upper.compareTo(value) <= 0;
    }

    private boolean isLess(T value, Comparable<? super T> lower) {
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

    public boolean isEqualTo(Derived rhs) {
        return Compare.equals(value, rhs.getValue());
    }

    public boolean isNotEqualTo(Derived rhs) {
        return !isEqualTo(rhs);
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
        BoundedValue<?, ?> that = rhs;
        if (rhs == null) {
            throw new ArgumentNullException("rhs");
        }
        if (!Compare.equals(lower, that.lower)) {
            throw new IllegalArgumentException("rhs has invalid lower bounds");
        }
        if (!Compare.equals(upper, that.upper)) {
            throw new IllegalArgumentException("rhs has invalid upper bounds");
        }

        return getValue().compareTo(rhs.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoundedValue that = (BoundedValue) o;

        if (!Compare.equals(lower, that.lower)) return false;
        if (!Compare.equals(upper, that.upper)) return false;
        if (!Compare.equals(value, that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Compare.hashCode(value);
        result = 31 * result + Compare.hashCode(lower);
        result = 31 * result + Compare.hashCode(upper);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s{%s<=%s<%s}", getClass().getSimpleName(), lower, value, upper);
    }
}
