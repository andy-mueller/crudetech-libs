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
package com.crudetech.junit.feature;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;

/**
 * Simple feature fixture, that ensures that a given objects overrides
 * {@link Object#hashCode()}  and {@link Object#equals(Object)} in a way that
 * they check vor equivalence.
 * <p/>
 * Check Bloch, Effective Java, Chapter 3, Item 7 and 8 for more details on how
 * to override {@link Object#hashCode()} and {@link Object#equals(Object)}
 * properly.
 */
public class Equivalent<T> implements FeatureFixture{
    /**
     * Factory that is used by the {@link Equivalent} fixture to construct
     * the objects needed for the tests.
     *
     * @param <T>
     */
    public interface Factory<T> {
        /**
         * When implemented, each call creates a unique item that is
         * equal to other instances created by this method before.
         *
         * @return As unique instance as described.
         */
        T createItem();

        /**
         * When implemented, it returns a set of objects that are not equal
         * to the ones created by {@link #createItem()}.
         * <p/>
         * When the class under test consist has several properties that are
         * compared in it {@link #equals(Object)} method, this method will return
         * a set of object that are permutations of these properties.
         *
         * @return A list of instances as described.
         */
        List<T> createOtherItems();
    }

    private final Factory<T> factory;

    public Equivalent(Factory<T> factory) {
        this.factory = factory;
    }

    private T first;
    private T second;
    private T third;
    private List<T> others;

    @Before
    public void before() {
        first = factory.createItem();
        second = factory.createItem();
        third = factory.createItem();

        others = factory.createOtherItems();
    }

    @Test
    public final void prerequisiteUniqueInstances() {
        assertThat(first, is(not(sameInstance(second))));
        assertThat(first, is(not(sameInstance(third))));
        assertThat(third, is(not(sameInstance(second))));

        int count = 0;
        for (T other : others) {
            assertThat("first is not different instance that other #" + count++, first, is(not(sameInstance(other))));
            assertThat("second is not different instance that other #" + count++, second, is(not(sameInstance(other))));
            assertThat("third is not different instance that other #" + count++, third, is(not(sameInstance(other))));
        }
    }

    @Test
    public void reflexivity() {
        assertThat(first, is(equalTo(first)));
        assertThat(second, is(equalTo(second)));
        assertThat(third, is(equalTo(third)));

        Handler<T> assertReflexivity = new Handler<T>() {
            @Override
            public void handle(int idx, T item) {
                assertThat("Item #" + idx + "is not reflexive!", item, is(equalTo(item)));
            }
        };
        forEach(others, assertReflexivity);
    }


    private interface Handler<T> {
        void handle(int idx, T item);
    }

    private static <T> void forEach(List<T> items, Handler<? super T> handler) {
        for (int i = 0; i < items.size(); ++i) {
            handler.handle(i, items.get(i));
        }
    }

    @Test
    public void equalsIsTransitive() {
        T t1 = first;
        T t2 = second;
        T t3 = third;

        assertThat(t1, is(equalTo(t2)));
        assertThat(t1, is(equalTo(t3)));
        assertThat(t2, is(equalTo(t3)));
    }


    @Test
    public void equalsIsCommutative() {
        T lhs = first;
        T rhs = second;
        assertThat(rhs, is(equalTo(lhs)));
        assertThat(lhs, is(equalTo(rhs)));
    }

    @Test
    public void equalsIsConsistent() {
        assertThat(first, is(equalTo(second)));
        assertThat(first, is(equalTo(second)));
        assertThat(first, is(equalTo(second)));

        Handler<T> assertNotEqualTo = new Handler<T>() {
            @Override
            public void handle(int idx, T item) {
                assertThat("Item # " + idx + " is not consistent", first, is(not(equalTo(item))));
                assertThat("Item # " + idx + " is not consistent", first, is(not(equalTo(item))));
                assertThat("Item # " + idx + " is not consistent", first, is(not(equalTo(item))));
            }
        };
        forEach(others, assertNotEqualTo);

    }

    @Test
    public void equalsWithNull() {
        assertThat(first, is(not(equalTo(null))));
        assertThat(second, is(not(equalTo(null))));
        assertThat(third, is(not(equalTo(null))));

        Handler<T> isNotNull = new Handler<T>() {
            @Override
            public void handle(int idx, T item) {
                assertThat("Item #" + idx + "is not equal to null", item, is(not(equalTo(null))));

            }
        };
        forEach(others, isNotNull);
    }
    @Test
    public void equalsFailsOnDifferentObjects() {

        Handler<T> assertNotEqual= new Handler<T>() {
            @Override
            public void handle(int idx, T item) {
                assertThat("Item #" + idx + "is not equal to first", first, is(not(equalTo(item))));
            }
        };
        forEach(others, assertNotEqual);
    }

    @Test
    public void hashcode() {
        assertThat(first.hashCode(), is(equalTo(second.hashCode())));
        assertThat(third.hashCode(), is(equalTo(second.hashCode())));
        assertThat(third.hashCode(), is(equalTo(first.hashCode())));
    }

    @Test
    public void hashcodeMultipleTimes() {
        assertThat(first.hashCode(), is(equalTo(first.hashCode())));
    }

    @Test
    public void hashcodeDiffersOnDifferentObjects() {
        Handler<T> hashcodeDiffersOnDifferentObjects = new Handler<T>() {
            @Override
            public void handle(int idx, T item) {
                assertThat("Item #" + idx + "has different hash code", first.hashCode(), is(not(equalTo(item.hashCode()))));
            }
        };
        forEach(others, hashcodeDiffersOnDifferentObjects);
    }

}
