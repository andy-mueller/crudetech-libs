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
    private static class DiceRoll extends BoundedValue<Integer> {
        public DiceRoll(int value) {
            super(value, 1, 7);
        }

        static DiceRoll of(int value) {
            return new DiceRoll(value);
        }
    }

    @Test
    public void ctorWorksWhenInRange() {
        BoundedValue<Integer> four = DiceRoll.of(4);
        assertThat(four.getValue(), is(4));
    }

    @Test
    public void ctorWorksWhenOnLowerBound() {
        BoundedValue<Integer> one = DiceRoll.of(1);
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
        BoundedValue<Integer> six = DiceRoll.of(6);
        assertThat(six, is(notNullValue()));
    }

    @Test
    public void noConstructionIsPossibleWithNullValue() {
        Runnable constructWithNullValue = new Runnable() {
            public void run() {
                Object unused = new BoundedValue<Integer>(null, 4, 8) {
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
                Object unused = new BoundedValue<Integer>(5, null, 8) {
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
                Object unused = new BoundedValue<Integer>(5, 3, null) {
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
}
