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
import com.crudetech.junit.feature.Feature;
import com.crudetech.junit.feature.Features;
import com.crudetech.junit.feature.Serializable;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@RunWith(Features.class)
public class ComparableBoundedValueTest {
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

    @Feature(Serializable.class)
    public static Serializable.Factory<DiceRoll> isSerializable(){
        return new Serializable.Factory<DiceRoll>() {
            @Override
            public DiceRoll createObject() {
                return DiceRoll.of(3);
            }
        };
    }
}
