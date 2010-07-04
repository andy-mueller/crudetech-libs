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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public abstract class OrderMatcher<T> extends TypeSafeMatcher<T> {
    private final T rhs;

    private OrderMatcher(T rhs) {
        this.rhs = rhs;
    }
    @Override
    public boolean matchesSafely(T lhs) {
        if (lhs == null || rhs == null) {
            throw new IllegalArgumentException();
        }
        return compare(lhs, rhs);
    }
    protected abstract boolean compare(final T lhs, final T rhs);
    public void describeTo(Description description) {
        description.appendValue(rhs);
    }

    public static <T extends Comparable<T>>  Matcher<T> lessThan(final T rhs) {
        return new OrderMatcher<T>(rhs) {
            @Override
            protected boolean compare(T lhs, T rhs) {
                return lhs.compareTo(rhs) < 0;
            }
        };
    }

    public static <T extends Comparable<T>>  Matcher<T> lessOrEqualThan(final T rhs) {
        return new OrderMatcher<T>(rhs) {
            @Override
            protected boolean compare(T lhs, T rhs) {
                return lhs.compareTo(rhs) <= 0;
            }
        };
    }

    public static <T extends Comparable<T>>  Matcher<T> greaterThan(final T rhs) {
        return new OrderMatcher<T>(rhs) {
            @Override
            protected boolean compare(T lhs, T rhs) {
                return lhs.compareTo(rhs) > 0;
            }
        };
    }

    public static <T extends Comparable<T>>  Matcher<T> greaterOrEqualThan(final T rhs) {
        return new OrderMatcher<T>(rhs) {
            @Override
            protected boolean compare(T lhs, T rhs) {
                return lhs.compareTo(rhs) >= 0;
            }
        };
    }
}
