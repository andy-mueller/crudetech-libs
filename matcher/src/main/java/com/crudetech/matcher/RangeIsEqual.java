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

import com.crudetech.lang.Compare;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

/**
 * This class provides several {@link org.hamcrest.Matcher} implementations
 * to compare ranges for equality. To ranges are considered equal, when each
 * item compares equal
 */
public final class RangeIsEqual {
    private RangeIsEqual(){}

    @Factory
    public static <T> Matcher<Iterable<T>> equalTo(final T... rhs) {
        return equalTo(Arrays.asList(rhs));
    }
    @Factory
    public static <T> Matcher<Iterable<T>> equalTo(final Iterable<T> rhs) {
        return new TypeSafeMatcher<Iterable<T>>(){
            @Override
            public boolean matchesSafely(Iterable<T> lhs) {
                return Compare.equals(lhs, rhs);
            }
            public void describeTo(Description description) {
                description.appendValue(rhs);
            }
        };
    }
}
