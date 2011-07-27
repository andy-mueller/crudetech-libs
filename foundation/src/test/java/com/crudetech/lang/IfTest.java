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

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.crudetech.lang.If.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;

public class IfTest {
    private static Matcher<Boolean> is(boolean b){
        return Matchers.is(Boolean.valueOf(b));
    }
    @Test
    public void isNullIsTrueWhenArgumentIsNull(){
        assertThat(isNull(null), is(true));
    }
    @Test
    public void isNullIsFalseWhenArgumentIsNotNull(){
        assertThat(isNull(this), is(false));
    }
     @Test
    public void isNotNullIsFalseWhenArgumentIsNull(){
        assertThat(isNotNull(null), is(false));
    }
    @Test
    public void isNullIsTrueWhenArgumentIsNotNull(){
        assertThat(isNotNull(this), is(true));
    }

    @Test
    public void charSequenceIsEmptyIsTrueOnEmptyString(){
        assertThat(isEmpty(""), is(true));
    }
    @Test
    public void charSequenceIsNullOrEmptyIsTrueOnEmptyString(){
        assertThat(isNullOrEmpty(""), is(true));
    }
    @Test
    public void charSequenceIsNullOrEmptyIsTrueOnNullString(){
        assertThat(isNullOrEmpty(""), is(true));
    }
    @Test
    public void charSequenceIsNotNullOrEmptyIsFalseTrueOnNullString(){
        assertThat(isNotNullOrEmpty(""), is(false));
    }
    @Test
    public void charSequenceIsEmptyIsFalseOnNonEmptyString(){
        assertThat(isEmpty("not empty"), is(false));
    }
    @Test
    public void charSequenceIsNotEmptyIsFalseOnEmptyString(){
        assertThat(isNotEmpty(""), is(false));
    }
    @Test
    public void charSequenceIsNotEmptyIstTrueOnNonEmptyString(){
        assertThat(isNotEmpty("not empty"), is(true));
    }


    @Test
    public void arrayIsEmptyIsTrueOnEmptyArray(){
        assertThat(isEmpty(new Object[0]), is(true));
    }
    @Test
    public void arrayIsEmptyIsFalseOnNonEmptyArray(){
        assertThat(isEmpty(new Object[1]), is(false));
    }
    @Test
    public void arrayIsNotEmptyIsFalseOnEmptyArray(){
        assertThat(isNotEmpty(new Object[0]), is(false));
    }
    @Test
    public void arrayIsNotEmptyIstTrueOnNonEmptyArray(){
        assertThat(isNotEmpty(new Object[1]), is(true));
    }
    @Test
    public void arrayIsNotNullOrEmptyIstTrueOnNonEmptyArray(){
        assertThat(isNotNullOrEmpty(new Object[1]), is(true));
    }
    @Test
    public void arrayIsNotNullOrEmptyIstFalseOnNullArray(){
        Object[] nullArray = null;
        assertThat(isNotNullOrEmpty(nullArray), is(false));
    }
    @Test
    public void arrayIsNullOrEmptyIstTrueOnNullArray(){
        Object[] nullArray = null;
        assertThat(isNullOrEmpty(nullArray), is(true));
    }

    @Test
    public void iterableIsEmptyIsTrueOnEmptyIterable(){
        assertThat(isEmpty(asIterable(new Object[0])), is(true));
    }

    private <T> Iterable<T> asIterable(T[] ts) {
        return asList(ts);
    }

    @Test
    public void iterableIsEmptyIsFalseOnNonEmptyIterable(){
        assertThat(isEmpty(asIterable(new Object[1])), is(false));
    }
    @Test
    public void iterableIsNotEmptyIsFalseOnEmptyIterable(){
        assertThat(isNotEmpty(asIterable(new Object[0])), is(false));
    }
    @Test
    public void iterableIsNotEmptyIstTrueOnNonEmptyIterable(){
        assertThat(isNotEmpty(asIterable(new Object[1])), is(true));
    }

    @Test
    public void iterableIsNotNullOrEmptyIsTrueOnNonEmptyIterable(){
        assertThat(isNotNullOrEmpty(asIterable(new Object[1])), is(true));
    }
    @Test
    public void iterableIsNullOrEmptyIsTrueOnNonEmptyIterable(){
        assertThat(isNullOrEmpty(asIterable(new Object[0])), is(true));
    }
    @Test
    public void iterableIsNotNullOrEmptyIsFalseOnNonNullIterable(){
        assertThat(isNotNullOrEmpty((Iterable<?>) null), is(false));
    }

    @Test
    public void isIsBoolean(){
        assertThat(If.is(true), is(true));
    }
    @Test
    public void isNotIsBoolean(){
        assertThat(If.isNot(true), is(false));
    }
}
