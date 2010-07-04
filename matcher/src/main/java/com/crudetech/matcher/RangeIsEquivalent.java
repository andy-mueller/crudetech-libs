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
package com.crudetech.matcher;

import com.crudetech.collections.Iterables;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

/**
 * This class provides several {@link org.hamcrest.Matcher} implementations
 * to compare ranges for equivalence, i.e. they have the same content
 * but not necessarily in the same order.
 */
public final class RangeIsEquivalent {

    private RangeIsEquivalent() {
    }

    public static <T> Matcher<Iterable<T>> equivalentTo(final Iterable<T> rhs) {
        return new TypeSafeMatcher<Iterable<T>>() {

            @Override
            public boolean matchesSafely(Iterable<T> lhs) {

                for (T l : lhs) {
                    if (!Iterables.contains(rhs, l)) {
                        return false;
                    }
                }

                return Iterables.size(lhs) == Iterables.size(rhs);
            }

            public void describeTo(Description description) {
                description.appendValue(rhs);
            }
        };
    }

    public static <T> Matcher<Iterable<T>> equivalentTo(final T... rhs) {
        return equivalentTo(Arrays.asList(rhs));
    }
}