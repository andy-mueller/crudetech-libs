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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;

/**
 * Simple base fixture, that ensures that a given objects overrides
 * {@link Object#hashCode()}  and {@link Object#equals(Object)} in a way that
 * they check vor equivalence.
 * <p>
 * It is intended to be used as a parametrized test. This simplifies to test all the different
 * permutation of a classes members for equality. For instance a Pair class consisting of two values
 * that are independent, can be tested in the following way:
 * <p>
 * <pre>
 * import com.crudetech.unittests.EquivalentObjectFixture;
 * import org.junit.runner.RunWith;
 * import org.junit.runners.Parameterized;
 * import java.util.Collection;
 * import static java.util.Arrays.asList;
 *
 * &#64RunWith(Parameterized.class)
 * public class PairEquivalenceFixture extends EquivalentObjectFixture<Pair<Integer, String>> {
 *     public PairEquivalenceFixture(Pair<Integer, String> first, Pair<Integer, String> second, Pair<Integer, String> third, Pair<Integer, String> other) {
 *         super(first, second, third, other);
 *     }
 *
 *     &#64Parameterized.Parameters
 *     public static Collection<Pair[]> parameters(){
 *         final Pair<Integer, String> first = new Pair<Integer, String>(2, "default");
 *         final Pair<Integer, String> second = new Pair<Integer, String>(2, "default");
 *         final Pair<Integer, String> third = new Pair<Integer, String>(2, "default");
 *         return asList(new Pair[][]{
 *                 {first, second, third, new Pair<Integer, String>(2, "other") },
 *                 {first, second, third, new Pair<Integer, String>(5, "default") },
 *                 {first, second, third, new Pair<Integer, String>(5, "other") },
 *         });
 *     }
 * }
 * </pre>
 * <p>
 * Check Bloch, Effective Java, Chapter 3, Item 7 and 8 for more details on how
 *  to override {@link Object#hashCode()}  and {@link Object#equals(Object)}
 * properly.
 */
public abstract class EquivalentObjectFixture<T> {
    private final T first;
    private final T second;
    private final T third;
    private final T other;

    /**
     * Constructs the test
     * @param first An object of type T that is expected to be equal to the second and third object.
     * @param second An object of type T that is expected to be equal to the first and third object.
     * @param third An object of type T that is expected to be equal to the first and second object.
     * @param other An object of type T that is expected not to be equal to either the first, second and third object.
     */
    protected EquivalentObjectFixture(T first, T second, T third, T other) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.other = other;
    }

    @Test
    public final void uniqueInstances() {
        assertThat(first, is(not(sameInstance(second))));
        assertThat(first, is(not(sameInstance(third))));
        assertThat(third, is(not(sameInstance(second))));

        assertThat(first, is(not(sameInstance(other))));
        assertThat(second, is(not(sameInstance(other))));
        assertThat(second, is(not(sameInstance(other))));
    }

    @Test
    public void reflexivity() {
        assertThat(first, is(equalTo(first)));
        assertThat(second, is(equalTo(second)));
        assertThat(third, is(equalTo(third)));
    }

    @Test
    public void equalsIsTransitive() {
        T t1 = first;
        T t2 = second;
        T t3 = third;

        assertThat(t1, is(not(sameInstance(t2))));
        assertThat(t1, is(not(sameInstance(t3))));
        assertThat(t2, is(not(sameInstance(t3))));


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

        assertThat(first, is(not(equalTo(other))));
        assertThat(first, is(not(equalTo(other))));
        assertThat(first, is(not(equalTo(other))));
    }

    @Test
    public void equalsWithNull() {
        assertThat(first, is(not(equalTo(null))));
    }


    @Test
    public void hashcode() {
        assertThat(first.hashCode(), is(equalTo(second.hashCode())));
        assertThat(third.hashCode(), is(equalTo(second.hashCode())));
    }

    @Test
    public void hashcodeMultipleTimes() {
        assertThat(first.hashCode(), is(equalTo(first.hashCode())));
    }

    @Test
    public void hashcodeDiffersOnDifferentObjects() {
        assertThat(first.hashCode(), is(not(equalTo(other.hashCode()))));
    }
}
