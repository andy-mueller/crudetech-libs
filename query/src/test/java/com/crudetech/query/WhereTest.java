////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//     Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.query;

import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.ArgumentNullException;
import org.junit.Test;

import java.util.Arrays;

import static com.crudetech.matcher.RangeHasSize.isEmpty;
import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static com.crudetech.query.Query.from;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;



public class WhereTest {
    private final static UnaryFunction<Integer, Boolean> lessThan5 = new UnaryFunction<Integer, Boolean>() {

        public Boolean execute(Integer arg) {
            return arg < 5;
        }
    };

    @Test
    public void selectWhere() {

        Iterable<Float> collection = Arrays.asList(0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f);

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
        Iterable<Integer> collection = Arrays.asList();

        Iterable<Integer> result = from(collection).where(lessThan5);

        assertThat(result, isEmpty());
    }
    @Test
    public void whereWithNullThrows(){
        Runnable whereWithNull = new Runnable() {
            @Override
            public void run() {
                from(1, 2, 3).where(null);
            }
        };
        assertThat(whereWithNull, doesThrow(ArgumentNullException.class));        
    }
}
