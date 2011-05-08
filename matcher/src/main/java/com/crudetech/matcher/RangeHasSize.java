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
package com.crudetech.matcher;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Iterator;


public final class RangeHasSize extends TypeSafeDiagnosingMatcher<Iterable<?>> {
    private final int expectedSize;
    @Override
    protected boolean matchesSafely(Iterable<?> range, Description mismatchDescription) {
         int size = 0;
                Iterator i = range.iterator();
                //noinspection WhileLoopReplaceableByForEach
                while (i.hasNext()) {
                    i.next();
                    ++size;
                }
                return size == expectedSize;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expectedSize);
    }

    private RangeHasSize(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    @Factory
    public static Matcher<Iterable<?>> hasSizeOf(final int expectedSize) {
        return new RangeHasSize(expectedSize);
    }
}
