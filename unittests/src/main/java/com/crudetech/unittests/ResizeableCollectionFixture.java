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

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * A base fixture for a collection that can be resized, i.e. a full blown collection.
 * Refer to the collection framework documentation for more details of fixed size vs.
 * resizeable collection
 */
public abstract class ResizeableCollectionFixture<T> extends IterableFixture<T> {
    protected abstract Collection<T> createCollection();

    protected abstract Collection<T> createEmptyCollection();

    protected abstract T createNewUniqueItem();

    @Override
    protected final Iterable<T> createIterable() {
        return createCollection();
    }

    @Override
    protected final Iterable<T> createEmptyIterable() {
        return createEmptyCollection();
    }

    @Test
    public void collectionIsNotEmpty() {
        Collection<T> collection = createCollection();
        assertThat(collection.isEmpty(), is(false));
    }

    @Test
    public void add() {
        T uniqueItem = createNewUniqueItem();
        Collection<T> collection = createCollection();

        assertThat(collection.contains(uniqueItem), is(false));
        assertThat(collection.add(uniqueItem), is(true));
        assertThat(collection.contains(uniqueItem), is(true));
    }

    @Test
    public void addAll() {
        Collection<T> uniqueItems = asList(createNewUniqueItem(), createNewUniqueItem(), createNewUniqueItem());
        Collection<T> collection = createCollection();

        for (T uniqueItem : uniqueItems) {
            assertThat(collection.contains(uniqueItem), is(false));
        }
        assertThat(collection.addAll(uniqueItems), is(true));

        for (T uniqueItem : uniqueItems) {
            assertThat(collection.contains(uniqueItem), is(true));
        }
    }

    @Test
    public void clear(){
        Collection<T> collection = createCollection();

        collection.clear();
        assertThat(collection.isEmpty(), is(true));
    }
    @Test
    public void contains(){
        Collection<T> collection = createCollection();

        for(T item : collection){
            assertThat(collection.contains(item), is(true));
        }
     }
}
