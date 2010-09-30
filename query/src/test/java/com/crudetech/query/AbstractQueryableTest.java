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

import org.junit.Test;

import java.util.Arrays;

import static com.crudetech.matcher.ThrowsException.doesThrow;
import static com.crudetech.query.Query.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AbstractQueryableTest {
@Test
    public void lastOrDefault() {
        final Iterable<Integer> src = Arrays.asList(0, 1, 2, 3, 4);

        final int last = from(src).lastOr(0);

        assertThat(last, is(4));
    }

    @Test
    public void last() {
        final int last = from(0, 1, 2, 3, 4).last();

        assertThat(last, is(4));
    }

    private static final Iterable<Integer> Empty = Arrays.asList();

    @Test
    public void lastOrDefaultOnEmptyIterable() {
        final int last = from(Empty).lastOr(0);
        assertThat(last, is(0));
    }
    @Test
    public void lastThrowOnEmptyRange(){
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
        assertThat(from(0, 1, 2, 3, 4).any(), is(true));
    }
    @Test
    public void firstOrDefault() {
        final Iterable<Integer> src = Arrays.asList(0, 1, 2, 3, 4);

        final int first = from(src).firstOr(42);

        assertThat(first, is(0));
    }

    @Test
    public void first() {
        final int first = from(0, 1, 2, 3, 4).first();

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
}

