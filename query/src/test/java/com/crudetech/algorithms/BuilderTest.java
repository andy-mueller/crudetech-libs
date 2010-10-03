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
package com.crudetech.algorithms;

import com.crudetech.functional.NullaryFunction;
import org.junit.Test;

import java.util.Arrays;

import static com.crudetech.algorithms.Builder.concat;
import static com.crudetech.algorithms.Builder.generate;
import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BuilderTest {
     @Test
    public void concatTest(){
        Iterable<Integer> range1 = Arrays.asList(0,1,2);
        Iterable<Integer> range2 = Arrays.asList(3,4,5);
        Iterable<Integer> range3 = Arrays.asList(6,7,8,9);

        Iterable<Integer> actual = concat(range1, range2, range3);

        Iterable<Integer> expected = Arrays.asList(0,1,2,3,4,5,6,7,8,9);

        assertThat(actual, is(equalTo(expected)));
    }
    @Test(expected=IllegalArgumentException.class)
    public void concatWithNull(){
        concat((Iterable<Iterable<Integer>>)null);
    }
    @Test
    public void concatWithEmptyPart(){
        Iterable<Integer> range1 = Arrays.asList(0,1,2);
        Iterable<Integer> range2 = Arrays.asList();
        Iterable<Integer> range3 = Arrays.asList(6,7,8,9);

        Iterable<Integer> actual = concat(range1, range2, range3);

        Iterable<Integer> expected = Arrays.asList(0,1,2,6,7,8,9);

        assertThat(actual, is(equalTo(expected)));
    }
    @Test
    public void concatWithEmptyEndPart(){
        Iterable<Integer> range1 = Arrays.asList(0,1,2);
        Iterable<Integer> range2 = Arrays.asList(3,4,5);
        Iterable<Integer> range3 = Arrays.asList();

        Iterable<Integer> actual = concat(range1, range2, range3);

        Iterable<Integer> expected = Arrays.asList(0,1,2,3,4,5);

        assertThat(actual, is(equalTo(expected)));
    }
    @Test
    public void concatWithEmptyStartPart(){
        Iterable<Integer> range1 = Arrays.asList();
        Iterable<Integer> range2 = Arrays.asList(3,4,5);
        Iterable<Integer> range3 = Arrays.asList(6,7,8);

        Iterable<Integer> actual = concat(range1, range2, range3);

        Iterable<Integer> expected = Arrays.asList(3,4,5,6,7,8);

        assertThat(actual, is(equalTo(expected)));
    }
    @Test
    public void concatWithNullPart(){
        Iterable<Integer> range1 = Arrays.asList(0,1,2);
        Iterable<Integer> range2 = null;
        Iterable<Integer> range3 = Arrays.asList(6,7,8,9);

        Iterable<Integer> actual = concat(range1, range2, range3);

        Iterable<Integer> expected = Arrays.asList(0,1,2,6,7,8,9);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void concatSingleAdds(){
        Iterable<Integer> range1 = Arrays.asList(0,1,2);

        Iterable<Integer> actual = concat(range1, 3,4,5,6);

        Iterable<Integer> expected = Arrays.asList(0,1,2,3,4,5,6);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void generateTest(){
        NullaryFunction<Integer> creator = new NullaryFunction<Integer>(){
            private int count = 0;
            public Integer execute() {
                return count++;
            }
        };
        Iterable<Integer> actual = generate(creator, 10);
        Iterable<Integer> expected = Arrays.asList(0,1,2,3,4,5,6,7,8,9);

        assertThat(actual, is(equalTo(expected)));
    }
}
