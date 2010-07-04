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
package com.crudetech.unittests;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class IterableFixture<T> {
    protected abstract Iterable<T> createIterable();
    protected abstract Iterable<T> createEmptyIterable();

    @Test
    public void createReturnsDifferentObjects(){
        Iterable<T> iterable1 = createIterable();
        Iterable<T> iterable2 = createIterable();
        assertThat(iterable1, is(not(sameInstance(iterable2))));
    }

    @Test
    public void iteratorReturnsNotNull(){
        Iterable<T> iterable = createIterable();
        assertThat(iterable.iterator(), is(not(nullValue())));
    }

    @Test
    public void iteratorReturnsDifferentIterators(){
        Iterable<T> iterable = createIterable();
        assertThat(iterable.iterator(), is(not(sameInstance(iterable.iterator()))));
    }
    @Test
    public void hasNextReturnsFalseOnEmptyIterable(){
        Iterable<T> empty = createEmptyIterable();
        assertThat(empty.iterator().hasNext(), is(false));
    }
    @Test(expected = NoSuchElementException.class)
    public void nextThrowsOnEmptyIterable(){
        final Iterable<T> empty = createEmptyIterable();

        empty.iterator().next();
    }
    @Test
    public void iterate(){
        Iterable<T> iterable = createIterable();

        List<T> l1 = new ArrayList<T>();
        for(T t : iterable) {
            l1.add(t);
        }

        List<T> l2 = new ArrayList<T>();
        for(T t : iterable) {
            l2.add(t);
        }

        assertThat(l1.size(), is(not(0)));
        assertThat(l1, is(equalTo(l2)));
    }
}
