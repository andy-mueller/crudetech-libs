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

import com.crudetech.collections.Pair;
import com.crudetech.functional.BinaryFunction;
import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.ArgumentNullException;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static com.crudetech.matcher.RangeHasSize.isEmpty;
import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static com.crudetech.query.Query.from;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class SelectTest {
    private static UnaryFunction<Float, Integer> float2Int = new UnaryFunction<Float, Integer>() {

        public Integer execute(Float arg) {
            return (int) arg.floatValue();
        }
    };

    @Test
    public void select() {
        Iterable<Float> collection = asList(0f, 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f);

        Iterable<Integer> i2 = from(collection).select(float2Int);

        Iterable<Integer> expected = asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
        assertThat(i2, is(equalTo(expected)));
    }

    @Test
    public void selectOnEmptyRangeProducesEmptyRange() {
        Iterable<Float> collection = asList();

        Iterable<Integer> i2 = from(collection).select(float2Int);

        assertThat(i2, isEmpty());
    }

    @Test
    public void selectWithNullThrows() {
        final Iterable<Float> collection = asList(1f, 2f);

        Runnable selectWithNull = new Runnable() {
            @Override
            public void run() {
                from(collection).select((UnaryFunction<Float,Object>) null);
            }
        };

        assertThat(selectWithNull, doesThrow(ArgumentNullException.class));
    }
    @Test
    public void select2WithNullThrows() {
        final Iterable<Float> collection = asList(1f, 2f);

        Runnable selectWithNull = new Runnable() {
            @Override
            public void run() {
                from(collection).select((BinaryFunction<Float,Integer,Object>) null);
            }
        };

        assertThat(selectWithNull, doesThrow(ArgumentNullException.class));
    }

    @Test
    public void selectWithIndex() {

        final Iterable<String> collection = asList("apple", "banana", "mango");



        final BinaryFunction<String, Integer, Pair<String, Integer>> xform =
                new BinaryFunction<String, Integer, Pair<String, Integer>>(){
                    @Override
                    public Pair<String, Integer> execute(String s, Integer index) {
                        return new Pair<String, Integer>(s, index);
                    }
                };

        final Iterable<Pair<String, Integer>> i2 = from(collection).select(xform);

        final Iterable<Pair<String, Integer>> expected = asList(
                new Pair<String, Integer>("apple", 0),
                new Pair<String, Integer>("banana", 1),
                new Pair<String, Integer>("mango", 2)
                );

        assertThat(i2, CoreMatchers.is(equalTo(expected)));
    }

}
