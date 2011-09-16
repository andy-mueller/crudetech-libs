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

import static com.crudetech.junit.AssertThrows.assertThrows;
import static java.lang.Integer.signum;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class Comparable<T extends java.lang.Comparable<T>> implements FeatureFixture {
    public interface Factory<T> {
        T createX();

        T createY();

        T createZ();
    }

    private final Factory<T> factory;

    public Comparable(Factory<T> factory) {
        this.factory = factory;
    }

    private T x;
    private T y;
    private T z;

    @Before
    public void before() {
        x = factory.createX();
        y = factory.createY();
        z = factory.createZ();
    }

    @Test
    public final void prerequisites() {
        assertThat(x.compareTo(y) < 0, is(true));
        assertThat(y.compareTo(z) < 0, is(true));
        assertThat(x.compareTo(z) < 0, is(true));
    }


    @Test
    public void reflexivity() {
        assertThat(x, is(equalTo(x)));
        assertThat(y, is(equalTo(y)));
        assertThat(z, is(equalTo(z)));
    }

    @Test
    public void isCommutative() {
        assertThat(signum(x.compareTo(y)), is(-signum(y.compareTo(x))));
        assertThat(signum(y.compareTo(z)), is(-signum(z.compareTo(y))));
        assertThat(signum(x.compareTo(z)), is(-signum(z.compareTo(x))));
    }

    @Test
    public void isTransitive() {
        assertThat(x.compareTo(y) < 0 && y.compareTo(z) < 0, is(x.compareTo(z) < 0));
        assertThat(z.compareTo(y) > 0 && y.compareTo(x) > 0, is(z.compareTo(y) > 0));
    }

    @Test
    public void sameAsEqual() {
        T x2 = factory.createX();
        assertThat(x2, is(not(sameInstance(x))));

        assertThat(x2, is(x));
        assertThat(x2.compareTo(x), is(0));
        assertThat(x.compareTo(x2), is(0));
    }

    @Test
    public void compareToThrowsWhenArgIsNull() {
        Runnable compareWithNull = new Runnable() {
            @Override
            public void run() {
                x.compareTo(null);
            }
        };

        // I hate to throw NullPointerException, but that what the spec says!
        assertThrows(compareWithNull, NullPointerException.class);
    }
}
