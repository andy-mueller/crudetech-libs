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
package com.crudetech.junit.collections;

import com.crudetech.junit.feature.FeatureFixture;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.crudetech.junit.collections.Utils.end;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

/**
 * A feature fixture for an object that is iterable.
 */
public class Iterable<T> implements FeatureFixture {
    public static interface Factory<T>{
        java.lang.Iterable<T> createIterable();
    }
    private final Factory<T> factory;

    public Iterable(Factory<T> factory) {
        this.factory = factory;
    }
    @Test
    public void iterationIsConsistent(){
        java.lang.Iterable<T> iterable = factory.createIterable();

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
    @Test
    public void iterableCreatesNeIteratorEachTime(){
        java.lang.Iterable<T> iterable = factory.createIterable();

        assertThat(iterable.iterator(), is(CoreMatchers.not(sameInstance(iterable.iterator()))));
    }
    @Test
    public void hasNextReturnsFalseWhenAtEnd(){
        java.lang.Iterable<T> iterable = factory.createIterable();
        Iterator<?> it = end(iterable);

        assertThat(it.hasNext(), is(false));
    }
    @Test(expected = NoSuchElementException.class)
    public void nextTrowsWhenAtEnd(){
        java.lang.Iterable<T> iterable = factory.createIterable();
        Iterator<?> it = end(iterable);

        it.next();
    }


}
