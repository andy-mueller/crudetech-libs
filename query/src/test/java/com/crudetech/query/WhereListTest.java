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
package com.crudetech.query;

import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.ArgumentNullException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.crudetech.matcher.RangeHasSize.hasSizeOf;
import static com.crudetech.matcher.RangeIsEmpty.isEmpty;
import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static com.crudetech.query.Query.from;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class WhereListTest {
    private final static UnaryFunction<Integer, Boolean> lessThan5 = new UnaryFunction<Integer, Boolean>() {

        public Boolean execute(Integer arg) {
            if(arg == null) return false;
            return arg < 5;
        }
    };

    @Test
    public void selectWhere() {

        List<Float> collection = Arrays.asList(0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f);

        UnaryFunction<Float, Integer> selection = new UnaryFunction<Float, Integer>() {

            public Integer execute(Float arg) {
                return (int) arg.floatValue();
            }
        };

        Iterable<Integer> result = from(collection).select(selection).where(lessThan5);

        Iterable<Integer> expected = Arrays.asList(0, 1, 2, 3, 4);

        assertThat(result, is(equalTo(expected)));
    }
    @Test
    public void whereOnEmptyIsEmpty() {
        List<Integer> collection = emptyList();

        Iterable<Integer> result = from(collection).where(lessThan5);

        assertThat(result, isEmpty());
    }
    @Test
    public void whereWithNullThrows(){
        Runnable whereWithNull = new Runnable() {
            @Override
            public void run() {
                List<Integer> range = asList(1, 2, 3);
                from(range).where(null);
            }
        };
        assertThat(whereWithNull, doesThrow(ArgumentNullException.class));
    }

    @Test
    public void whereOnSequencesContainingNull(){
        List<Integer> range = asList(1, 9, null, 4, 2, null, 7);

        Iterable<Integer> result = from(range).where(lessThan5);

        assertThat(result, is(equalTo(1, 4, 2)));
    }
    @Test
    public void whereOnSequencesContainingAnsStartingWithNull(){
        List<Integer> range = asList(null, 1, 9, null, 4, 2, null, 7);

        Iterable<Integer> result = from(range).where(lessThan5);


        assertThat(range, hasSizeOf(8));
        assertThat(result, is(equalTo(1, 4, 2)));
    }

    @Test
    public void whereOnSequencesContainingNullAndCheckingForNull(){
        List<Integer> range = asList(null, 4, 2, null, 7, null);

        UnaryFunction<Object,Boolean> isNull = new UnaryFunction<Object, Boolean>() {
            @Override
            public Boolean execute(Object integer) {
                return integer == null;
            }
        };
        Iterable<Integer> result = from(range).where(isNull);

        assertThat(result, is(equalTo(new Integer[]{null, null, null})));
    }
}
