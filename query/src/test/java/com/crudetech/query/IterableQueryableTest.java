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

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.crudetech.matcher.RangeIsEmpty.isEmpty;
import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static com.crudetech.query.Query.from;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IterableQueryableTest {
    @Test
    public void lastOrDefault() {
        final Iterable<Integer> src = asList(0, 1, 2, 3, 4);

        final int last = from(src).lastOr(0);

        assertThat(last, is(4));
    }

    @Test
    public void last() {
        Iterable<Integer> range = asList(0, 1, 2, 3, 4);
        final int last = from(range).last();

        assertThat(last, is(4));
    }

    private static final Iterable<Integer> Empty = Collections.emptyList();

    @Test
    public void lastOrDefaultOnEmptyIterable() {
        final int last = from(Empty).lastOr(0);
        assertThat(last, is(0));
    }

    @Test
    public void lastThrowOnEmptyRange() {
        Runnable lastThrowsOnEmptyRange = new Runnable() {
            @Override
            public void run() {
                from(Empty).last();
            }
        };
        assertThat(lastThrowsOnEmptyRange, doesThrow(IllegalStateException.class));
    }

    @Test
    public void anyOnEmptyRange() {
        assertThat(from(Empty).any(), is(false));
    }

    @Test
    public void any() {
        Iterable<Integer> range = asList(0, 1, 2, 3, 4);
        assertThat(from(range).any(), is(true));
    }

    @Test
    public void firstOrDefault() {
        final Iterable<Integer> src = asList(0, 1, 2, 3, 4);

        final int first = from(src).firstOr(42);

        assertThat(first, is(0));
    }

    @Test
    public void first() {
        Iterable<Integer> range = asList(0, 1, 2, 3, 4);
        final int first = from(range).first();

        assertThat(first, is(0));
    }

    @Test
    public void firstOrDefaultOnEmptyIterable() {
        final int first = from(Empty).firstOr(42);
        assertThat(first, is(42));
    }

    @Test
    public void firstOnEmptyIterable() {
        Runnable firstThrows = new Runnable() {
            @Override
            public void run() {
                from(Empty).first();
            }
        };

        assertThat(firstThrows, doesThrow(IllegalStateException.class));
    }

    @Test
    public void toArrayCreatesSimilarRange() {
        Iterable<Integer> src = asList(1, 2, 3, 4);

        Queryable<Integer> range = from(src);

        assertThat(Arrays.equals(range.toArray(Integer.class), new Number[]{1, 2, 3, 4}), is(true));
    }

    @Test
    public void toArrayOnEmptyRangeCreatesEmptyArray() {
        Queryable<Integer> range = from(Empty);

        assertThat(range.toArray(Integer.class).length, is(0));
    }

    @Test
    public void toListCreatesSimilarRange() {
        Iterable<Integer> src = asList(1, 2, 3, 4);
        Queryable<Integer> range = from(src);

        assertThat(range.toList(), is(equalTo(1, 2, 3, 4)));
    }

    @Test
    public void toListOnEmptyRangeCreatesEmptyList() {
        Queryable<Integer> range = from(Empty);

        assertThat(range.toList().size(), is(0));
        assertThat(range.toList(), isEmpty());
    }
}

