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

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CompareTest {
    private static final EqualityComparer<Integer> equalsAbs = new EqualityComparer<Integer>() {
        @Override
        public boolean equals(Integer lhs, Integer rhs) {
            return Math.abs(lhs)== Math.abs(rhs);
        }

        @Override
        public int hashCode(Integer item) {
            return Math.abs(item);
        }
    };
    @Test
    public void equalsIterablesComparesContent(){
        Iterable<Integer> lhs = asList(0,1,2);
        Iterable<Integer> rhs = asList(0,-1,2);

        assertThat(Compare.equals(lhs, rhs, equalsAbs), is(true));
    }
    @Test
    public void equalsIterablesIsFalseIfItemIsDifferent(){
        Iterable<Integer> lhs = asList(0,1,2);
        Iterable<Integer> rhs = asList(0,-2,2);

        assertThat(Compare.equals(lhs, rhs, equalsAbs), is(false));
    }
    @Test
    public void equalsIterablesIsFalseIfFirstItemOnFirstRangeIsDifferent(){
        Iterable<Integer> lhs = asList(1,1,2);
        Iterable<Integer> rhs = asList(0,-1,2);

        assertThat(Compare.equals(lhs, rhs, equalsAbs), is(false));
    }
    @Test
    public void equalsIterablesIsFalseIfFirstItemOnSecondRangeIsDifferent(){
        Iterable<Integer> lhs = asList(0,1,2);
        Iterable<Integer> rhs = asList(1,-1,2);

        assertThat(Compare.equals(lhs, rhs, equalsAbs), is(false));
    }
    @Test
    public void equalsIterablesIsFalseIfLastItemOnFirstRangeIsDifferent(){
        Iterable<Integer> lhs = asList(0,1,3);
        Iterable<Integer> rhs = asList(0,-1,2);

        assertThat(Compare.equals(lhs, rhs, equalsAbs), is(false));
    }
    @Test
    public void equalsIterablesIsFalseIfLastItemOnSecondRangeIsDifferent(){
        Iterable<Integer> lhs = asList(0,1,2);
        Iterable<Integer> rhs = asList(0,-1,3);

        assertThat(Compare.equals(lhs, rhs, equalsAbs), is(false));
    }
    @Test
    public void equalsIterablesIsTrueOnEmptyRanges(){
        Iterable<Integer> lhs = emptyList();
        Iterable<Integer> rhs = emptyList();

        assertThat(Compare.equals(lhs, rhs, equalsAbs), is(true));
    }
    @Test
    public void equalsIterablesIsFalseOnOneEmptyRange(){
        Iterable<Integer> lhs = emptyList();
        Iterable<Integer> rhs = asList(0,1,2);

        assertThat(Compare.equals(lhs, rhs, equalsAbs), is(false));
    }
    @Test
    public void equalsIterablesIsFalseOnSecondEmptyRange(){
        Iterable<Integer> lhs = asList(0,1,2);
        Iterable<Integer> rhs = emptyList();

        assertThat(Compare.equals(lhs, rhs, equalsAbs), is(false));
    }

    @Test
    public void equalCharSequenceIsTrueOnEqualStrings(){
        CharSequence lhs = new StringBuilder("01234");
        CharSequence rhs = "01234";

        assertThat(Compare.equals(lhs, rhs), is(true));
    }
    @Test
    public void equalCharSequenceIsFalseOnNonEqualStrings(){
        CharSequence lhs = new StringBuilder("01234");
        CharSequence rhs = "11234";

        assertThat(Compare.equals(lhs, rhs), is(false));
    }
    @Test
    public void equalCharSequenceIsIsFalseWhenFirstIsNull(){
        CharSequence rhs = "01234";

        assertThat(Compare.equals(null, rhs), is(false));
    }
    @Test
    public void equalCharSequenceIsIsFalseWhenFirstIsEmpty(){
        CharSequence lhs = new StringBuilder();
        CharSequence rhs = "01234";

        assertThat(Compare.equals(lhs, rhs), is(false));
    }
    @Test
    public void equalCharSequenceIsIsFalseWhen2ndIsNull(){
        CharSequence lhs = "01234";

        assertThat(Compare.equals(lhs, null), is(false));
    }
    @Test
    public void equalCharSequenceIsIsFalseWhen2ndIsEmpty(){
        CharSequence lhs = "01234";
        CharSequence rhs = new StringBuilder();

        assertThat(Compare.equals(lhs, rhs), is(false));
    }
}
