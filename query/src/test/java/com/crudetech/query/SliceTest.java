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

import com.crudetech.lang.ArgumentOutOfBoundsException;
import org.junit.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static com.crudetech.query.Query.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class SliceTest {
    @Test
    public void sliceAmount() {
        Iterable<Integer> collection = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        Iterable<Integer> coll5 = from(collection).slice(2).amount(5);

        Iterable<Integer> expected = Arrays.asList(2, 3, 4, 5, 6);

        assertThat(coll5, is(equalTo(expected)));
    }

    @Test
    public void sliceWithNegativeStartThrows() {

        Runnable sliceWithNegativeStart = new Runnable() {
            @Override
            public void run() {
                from(0, 1, 2).slice(-1).amount(2);
            }
        };

        assertThat(sliceWithNegativeStart, doesThrow(ArgumentOutOfBoundsException.class));
    }

    @Test
    public void sliceStartingBehindRange() {

        final Iterable<Integer> r = from(0, 1, 2).slice(8).amount(2);

        Runnable iterateSliceWithStartBehindRange = new Runnable() {
            @Override
            public void run() {
                r.iterator().next();
            }
        };

        assertThat(r.iterator().hasNext(), is(false));
        assertThat(iterateSliceWithStartBehindRange, doesThrow(NoSuchElementException.class));
    }

    @Test
    public void sliceWithNegativeAmountThrows() {

        Runnable sliceWithNegativeStart = new Runnable() {
            @Override
            public void run() {
                from(0, 1, 2).slice(1).amount(-1);
            }
        };

        assertThat(sliceWithNegativeStart, doesThrow(ArgumentOutOfBoundsException.class));
    }

    @Test
    public void sliceThrough() {
        Iterable<Integer> collection = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        Iterable<Integer> coll5 = from(collection).slice(2).to(5);

        Iterable<Integer> expected = Arrays.asList(2, 3, 4);

        assertThat(coll5, is(equalTo(expected)));
    }

    @Test
    public void take() {
        Iterable<Integer> collection = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        Iterable<Integer> coll5 = from(collection).take(5);

        Iterable<Integer> expected = Arrays.asList(0, 1, 2, 3, 4);

        assertThat(coll5, is(equalTo(expected)));
    }
}
