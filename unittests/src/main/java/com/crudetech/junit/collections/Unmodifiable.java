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
import org.junit.Test;

import java.util.Collection;

import static com.crudetech.junit.collections.Utils.first;
import static com.crudetech.junit.collections.Utils.sizeOf;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Unmodifiable<T> implements FeatureFixture {
    private final Factory<T> factory;

    public Unmodifiable(Factory<T> factory) {
        this.factory = factory;
    }

    public interface Factory<T> {
        Collection<T> createCollection();

        T createUniqueItem(int id);
    }

    @Test
    public void prerequisites() {
        Collection<T> unmodifiable = factory.createCollection();
        assertThat(unmodifiable.isEmpty(), is(false));
        assertThat(unmodifiable, is(is(not(sameInstance(factory.createCollection())))));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void addIsUnsupported() {
        Collection<T> unmodifiable = factory.createCollection();
        unmodifiable.add(factory.createUniqueItem(42));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addAllIsUnsupported() {
        Collection<T> unmodifiable = factory.createCollection();
        @SuppressWarnings("unchecked")
        Collection<T> toAdd = asList(factory.createUniqueItem(42), factory.createUniqueItem(43), factory.createUniqueItem(44));
        unmodifiable.addAll(toAdd);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void retainAllIsUnsupported() {
        Collection<T> unmodifiable = factory.createCollection();
        @SuppressWarnings("unchecked")
        Collection<T> toRetain = asList(factory.createUniqueItem(42), factory.createUniqueItem(43), factory.createUniqueItem(44));
        unmodifiable.retainAll(toRetain);
    }

    @Test
    public void retainAllDoesNotThrowIfCollectionIsNotModified() {
        Collection<T> unmodifiable = factory.createCollection();

        // this should leave the collection untouched!!
        unmodifiable.retainAll(factory.createCollection());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void clearIsUnsupported() {
        Collection<T> unmodifiable = factory.createCollection();
        unmodifiable.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeContainedElementIsUnsupported() {
        Collection<T> unmodifiable = factory.createCollection();
        unmodifiable.remove(unmodifiable.iterator().next());
    }

    @Test
    public void removeOfNotContainedElementIsFalse() {
        Collection<T> unmodifiable = factory.createCollection();
        assertThat(unmodifiable.remove(factory.createUniqueItem(42)), is(false));
    }

    @Test
    public void removeAllIsFalseIfNonContainedElementsAreRemoved() {
        Collection<T> unmodifiable = factory.createCollection();
        @SuppressWarnings("unchecked")
        Collection<?> items = asList(factory.createUniqueItem(42));
        assertThat(unmodifiable.removeAll(items), is(false));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeAllIsNotSupportedIfContainedElementsAreRemoved() {
        Collection<T> unmodifiable = factory.createCollection();
        @SuppressWarnings("unchecked")
        Collection<T> items = asList(first(unmodifiable));

        unmodifiable.removeAll(items);
    }


    //boolean removeAll(Collection<?> c);

    //boolean equals(Object o);->extra
    //int hashCode();->extra

    // toArray()
    //toArray(T[] t);
    //boolean containsAll(Collection<?> c);
    //boolean contains(Object o);
    @Test
    public void sizeReflectsContent() {
        Collection<T> unmodifiable = factory.createCollection();

        int count = sizeOf(unmodifiable);

        assertThat(unmodifiable.size(), is(count));
    }

    @Test
    public void isEmptyReflectsSize() {
        Collection<T> unmodifiable = factory.createCollection();

        assertThat(unmodifiable.size() == 0, is(unmodifiable.isEmpty()));
    }
}
