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
 * A simple value type that has a fixed range. For instance the
 * possible values of a dice could be modeled as
 * <pre>
 *     class Dice{
 *         private final Random rand = new Random();
 *         class DiceRoll extends BoundedValue&lt;Integer&gt;{
 *             private DiceRoll(int roll){
 *                 super(roll, 1, 7);//could be between 1 and 6
 *            }
 *         }
 *         Roll roll(){
 *              return new DiceRoll(random.nextInt(6)+1);
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
public abstract class BoundedValue<T> {
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
            throw new ArgumentOutOfBoundsException("value");
        }
        if (isGreaterOrEqual(value, upper)) {
            throw new ArgumentOutOfBoundsException("value");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoundedValue that = (BoundedValue) o;

        if (!lower.equals(that.lower)) return false;
        if (!upper.equals(that.upper)) return false;
        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + lower.hashCode();
        result = 31 * result + upper.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s{%s<=%s<%s}", getClass().getSimpleName(), lower, value, upper);
    }
}
