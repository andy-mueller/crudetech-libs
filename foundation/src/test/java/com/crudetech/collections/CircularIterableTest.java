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
package com.crudetech.collections;

import com.crudetech.lang.AbstractRunnable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.crudetech.junit.AssertThrows.assertThrows;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CircularIterableTest {
    @Test
    public void iterationWrapsAround() {
        CircularIterable<Integer> it = CircularIterable.from(0, 1, 2);
        Iterator<Integer> iterator = it.iterator();

        assertThat(iterator.next(), is(0));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(0));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(0));
    }

    @Test
    public void emptyListThrowsOnNext() {
        List<Integer> emptyList = emptyList();
        CircularIterable<Integer> it = CircularIterable.from(emptyList);
        final Iterator<Integer> iterator = it.iterator();

        Runnable emptyListThrowsOnNext = new AbstractRunnable() {
            @Override
            protected void doRun() throws Throwable {
                iterator.next();
            }
        };

        assertThrows(emptyListThrowsOnNext, NoSuchElementException.class);
    }

    @Test
    public void fromWithListThrowsOnNullInput() {
        Runnable fromThrowsOnNullInput = new AbstractRunnable() {
            @Override
            protected void doRun() throws Throwable {
                CircularIterable.from((List<Integer>) null);
            }
        };

        assertThrows(fromThrowsOnNullInput, IllegalArgumentException.class);
    }
    @Test
    public void fromWithVarArgsThrowsOnNullInput() {
        Runnable fromThrowsOnNullInput = new AbstractRunnable() {
            @Override
            protected void doRun() throws Throwable {
                CircularIterable.from((Integer[]) null);
            }
        };

        assertThrows(fromThrowsOnNullInput, IllegalArgumentException.class);
    }

    @Test
    public void removeWorksIfInnerIteratorDoesAllowRemove() {
        CircularIterable<Integer> it = CircularIterable.from(new ArrayList<>(asList(0, 1, 2)));
        Iterator<Integer> iterator = it.iterator();

        assertThat(iterator.next(), is(0));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(0));

        iterator.remove();

        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertThat(iterator.next(), is(1));
    }
}
