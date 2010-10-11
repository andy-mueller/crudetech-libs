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
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.equalTo;

public class AllAre {
    @Factory
    public static <T> Matcher<Iterable<T>> allAre(T item) {
        Matcher<T> e = (Matcher<T>) equalTo(item);
        return allAre(e);
    }
    @Factory
    public static <T> Matcher<Iterable<T>> allAre(final Matcher<T> matcher) {
        return new TypeSafeMatcher<Iterable<T>>() {

            @Override
            public boolean matchesSafely(Iterable<T> item) {
                for (T t : item) {
                    if (!matcher.matches(t)) {
                        return false;
                    }
                }
                return true;
            }

            public void describeTo(Description desc) {
                desc.appendText("At least one value does not match!");
                desc.appendDescriptionOf(matcher);
            }
        };
    }
}

