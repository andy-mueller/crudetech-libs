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
package com.crudetech.collections;

import org.junit.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;


public class IterablesFixture {
    @Test
    public void toStringIsSimilarToArraysClass(){
        Integer[] ints = new Integer[]{0,1,2,3,4};
        Iterable<Integer> it = Arrays.asList(ints);

        assertThat(Arrays.toString(ints), is(equalTo(Iterables.toString(it))));
    }
    @Test
    public void hashcodeIsSimilarToArraysClass(){
        Integer[] ints = new Integer[]{0,1,2,3,4};
        Iterable<Integer> it = Arrays.asList(ints);

        assertThat(Arrays.hashCode(ints), is(equalTo(Iterables.hashCode(it))));
    }
    @Test
    public void contains(){
        Iterable<Integer> range = Arrays.asList(0,1,2,3);

        assertThat(Iterables.contains(range, 1), is(true));
    }
    @Test
    public void containsNot(){
        Iterable<Integer> range = Arrays.asList(0,1,2,3);

        assertThat(Iterables.contains(range, 42), is(false));
    }
    @Test
    public void size(){
        Iterable<Integer> range = Arrays.asList(0,1,2,3);

        assertThat(Iterables.size(range), is(4));
    }
    @Test
    public void sizeOnEmptyRange(){
        Iterable<Integer> range = Arrays.asList();

        assertThat(Iterables.size(range), is(0));
    }

    @Test
    public void lastOf(){
        Iterable<Integer> range = Arrays.asList(0,1,2,3);

        assertThat(Iterables.lastOf(range), is(3));
    }
    @Test
    public void lastOfWhenLastIsNull(){
        Iterable<Integer> range = Arrays.asList(0,1,2,3, null);

        assertThat(Iterables.lastOf(range), is(nullValue()));
    }
    @Test(expected = NoSuchElementException.class)
    public void lastOfEmptyRange(){
        final Iterable<Integer> range = Arrays.asList();

        Iterables.lastOf(range);
    }
    @Test
    public void firstOf(){
        Iterable<Integer> range = Arrays.asList(0,1,2,3);

        assertThat(Iterables.firstOf(range), is(0));
    }
    @Test
    public void firstOfWhenFirstIsNull(){
        Iterable<Integer> range = Arrays.asList(null,1,2,3);

        assertThat(Iterables.firstOf(range), is(nullValue()));
    }
    @Test(expected = NoSuchElementException.class)
    public void firstOfEmptyRange(){
        final Iterable<Integer> range = Arrays.asList();

        Iterables.firstOf(range);
    }
    @Test
    public void isNotEmpty(){
        Iterable<Integer> range = Arrays.asList(0,1,2,3);

        assertThat(Iterables.isEmpty(range), is(false));
    }
    @Test
    public void isEmpty(){
        Iterable<Integer> range = Arrays.asList();

        assertThat(Iterables.isEmpty(range), is(true));
    }
}
