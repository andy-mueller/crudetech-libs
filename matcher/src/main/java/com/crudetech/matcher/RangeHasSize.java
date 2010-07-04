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

import org.hamcrest.Matcher;
import org.hamcrest.Description;
import static org.hamcrest.core.IsNot.not;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import java.util.Iterator;


public final class RangeHasSize {
    private RangeHasSize() {
    }

    @Factory
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Matcher<Iterable> hasSizeOf(final int expectedSize) {
        return new TypeSafeMatcher<Iterable>() {

            public boolean matchesSafely(Iterable range) {
                int size = 0;
                Iterator i = range.iterator();
                //noinspection WhileLoopReplaceableByForEach
                while (i.hasNext()) {
                    i.next();
                    ++size;
                }
                return size == expectedSize;
            }

            public void describeTo(Description description) {
                description.appendText("The range has not the correct size!");
            }
        };
    }

    @Factory
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Matcher<Iterable> isNotEmpty() {
        return not(isEmpty());
    }

    @Factory
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Matcher<Iterable> isEmpty() {
        return new TypeSafeMatcher<Iterable>() {

            public boolean matchesSafely(Iterable range) {
                return !range.iterator().hasNext();
            }

            public void describeTo(Description description) {
                description.appendText("The range is not empty!");
            }
        };
    }
}
