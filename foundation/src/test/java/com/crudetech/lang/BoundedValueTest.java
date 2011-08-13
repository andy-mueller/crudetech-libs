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

import com.crudetech.junit.feature.Comparable;
import com.crudetech.junit.feature.Equivalent;
import com.crudetech.junit.feature.Feature;
import com.crudetech.junit.feature.Features;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.crudetech.junit.AssertThrows.assertThrows;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(Features.class)
public class BoundedValueTest {
    private static class DiceRoll extends BoundedValue<Integer, DiceRoll> {
        public DiceRoll(int value) {
            super(value, 1, 7);
        }

        static DiceRoll of(int value) {
            return new DiceRoll(value);
        }
    }

    @Test
    public void ctorWorksWhenInRange() {
        BoundedValue<Integer, DiceRoll> four = DiceRoll.of(4);
        assertThat(four.getValue(), is(4));
    }

    @Test
    public void ctorWorksWhenOnLowerBound() {
        BoundedValue<Integer, DiceRoll> one = DiceRoll.of(1);
        assertThat(one, is(notNullValue()));
    }

    @Test
    public void ctorThrowsWhenBelowLowerBound() {
        Runnable createInvalidValueBelowLowerBound = new Runnable() {
            public void run() {
                DiceRoll.of(0);
            }
        };
        assertThrows(createInvalidValueBelowLowerBound, ArgumentOutOfBoundsException.class);
    }

    @Test
    public void ctorWorksWhenBelowUpperBound() {
        BoundedValue<Integer, DiceRoll> six = DiceRoll.of(6);
        assertThat(six, is(notNullValue()));
    }

    @Test
    public void noConstructionIsPossibleWithNullValue() {
        Runnable constructWithNullValue = new Runnable() {
            public void run() {
                Object unused = new BoundedValue<Integer, DiceRoll>(null, 4, 8) {
                };
                assertThat("shutup idea!", unused, is(notNullValue()));
            }
        };
        assertThrows(constructWithNullValue, IllegalArgumentException.class);
    }

    @Test
    public void noConstructionIsPossibleWithNullLower() {
        Runnable constructWithNullValue = new Runnable() {
            public void run() {
                Object unused = new BoundedValue<Integer, DiceRoll>(5, null, 8) {
                };
                assertThat("shutup idea!", unused, is(notNullValue()));
            }
        };
        assertThrows(constructWithNullValue, IllegalArgumentException.class);
    }

    @Test
    public void noConstructionIsPossibleWithNullUpper() {
        Runnable constructWithNullValue = new Runnable() {
            public void run() {
                Object unused = new BoundedValue<Integer, DiceRoll>(5, 3, null) {
                };
                assertThat("shutup idea!", unused, is(notNullValue()));
            }
        };
        assertThrows(constructWithNullValue, IllegalArgumentException.class);
    }

    @Test
    public void ctorThrowsWhenOnUpperBound() {
        Runnable createInvalidValueBelowLowerBound = new Runnable() {
            public void run() {
                DiceRoll.of(7);
            }
        };
        assertThrows(createInvalidValueBelowLowerBound, ArgumentOutOfBoundsException.class);
    }

    @Feature(Equivalent.class)
    public static Equivalent.Factory<DiceRoll> isEquivalent() {
        return new Equivalent.Factory<DiceRoll>() {
            @Override
            public DiceRoll createItem() {
                return DiceRoll.of(1);
            }

            @Override
            public List<DiceRoll> createOtherItems() {
                return asList(DiceRoll.of(2), DiceRoll.of(3));
            }
        };
    }

    @Test
    public void isEqualToReturnsTrueIfValueIsEqual(){
        DiceRoll r = DiceRoll.of(3);
        assertThat(r.isEqualTo(3), is(true));
    }
    @Test
    public void isEqualToReturnsFalseIfValueIsNotEqual(){
        DiceRoll r = DiceRoll.of(2);
        assertThat(r.isEqualTo(3), is(false));
    }
    @Test
    public void lessThanIsTrueWhenValueIsLessThan(){
        DiceRoll r = DiceRoll.of(3);
        assertThat(r.isLessThan(DiceRoll.of(4)), is(true));
        assertThat(r.isLessThan(DiceRoll.of(3)), is(false));
        assertThat(r.isLessThan(DiceRoll.of(2)), is(false));
    }
    @Test
    public void lessEqualThanIsTrueWhenValueIsLessEqualThan(){
        DiceRoll r = DiceRoll.of(3);
        assertThat(r.isLessEqualThan(DiceRoll.of(4)), is(true));
        assertThat(r.isLessEqualThan(DiceRoll.of(3)), is(true));
        assertThat(r.isLessEqualThan(DiceRoll.of(2)), is(false));
    }
    @Test
    public void greaterThanIsTrueWhenValueIsGreaterThan(){
        DiceRoll r = DiceRoll.of(3);
        assertThat(r.isGreaterThan(DiceRoll.of(2)), is(true));
        assertThat(r.isGreaterThan(DiceRoll.of(3)), is(false));
        assertThat(r.isGreaterThan(DiceRoll.of(4)), is(false));
    }
    @Test
    public void greaterEqualThanIsTrueWhenValueIsGreaterEqualThan(){
        DiceRoll r = DiceRoll.of(3);
        assertThat(r.isGreaterEqualThan(DiceRoll.of(2)), is(true));
        assertThat(r.isGreaterEqualThan(DiceRoll.of(3)), is(true));
        assertThat(r.isGreaterEqualThan(DiceRoll.of(4)), is(false));
    }
    @Feature(Comparable.class)
    public static Comparable.Factory<DiceRoll> isComparable(){
        return new Comparable.Factory<DiceRoll>() {
            @Override
            public DiceRoll createX() {
                return DiceRoll.of(2);
            }

            @Override
            public DiceRoll createY() {
                return DiceRoll.of(3);
            }

            @Override
            public DiceRoll createZ() {
                return DiceRoll.of(4);
            }
        };
    }
}
