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
import com.crudetech.lang.ArgumentNullException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.crudetech.junit.AssertThrows.assertThrows;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ForwardIteratorTest {
    @Test
    public void standardIterationRunsLikeIterator() {
        ForwardIterator<Integer> fwd = ForwardIterator.Builder.from(asList(1, 2, 3).iterator());

        List<Integer> actual = new ArrayList<Integer>();
        while (fwd.hasNext()) {
            assertThat(fwd.moveNext(), is(true));
            actual.add(fwd.getCurrent());
        }
        assertThat(fwd.moveNext(), is(false));
        assertThat(actual, is(asList(1, 2, 3)));
    }

    @Test
    public void emptyIteratorDoesNothing() {
        List<Integer> empty = emptyList();
        final ForwardIterator<Integer> fwd = ForwardIterator.Builder.from(empty);

        assertThat(fwd.hasNext(), is(false));
        assertThat(fwd.moveNext(), is(false));

        Runnable getCurrent = new AbstractRunnable() {
            @Override
            protected void doRun() throws Throwable {
                fwd.getCurrent();
            }
        };

        assertThrows(getCurrent, NoSuchElementException.class);
    }

    @Test
    public void nullIteratorThrowsOnConstruction() {
        Runnable nullIteratorThrowsOnConstruction = new AbstractRunnable() {
            @Override
            protected void doRun() throws Throwable {
                ForwardIterator.Builder.from((Iterable<Integer>) null);
            }
        };
        assertThrows(nullIteratorThrowsOnConstruction, ArgumentNullException.class);
    }
}
