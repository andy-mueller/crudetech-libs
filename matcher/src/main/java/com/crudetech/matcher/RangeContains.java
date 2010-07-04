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

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

/**
 * Provides {@link org.hamcrest.Matcher} implementations concerning the containment of items in a range.
 * <p>
 * They might look similar to the according hamcrest matchers, however they are not meant to
 * compare two equal long ranges. Instead they check if a set of items is containes without any order
 * in a range.
 */
public class RangeContains {
    private RangeContains() {
    }

    /**
     * Provides a matcher that checks if all of the items are contained in a range in any order.
     * @param items
     * @param <T>
     * @return
     */
    public static <T> Matcher<Iterable> contains(final T... items){
        Collection<Matcher<? super T>> matchers = new ArrayList<Matcher<? super T>>(items.length);
        for(T item : items){
            matchers.add(equalTo(item));
        }
        return contains(matchers);
    }

    /**
     * Provides a matcher that checks if all of the matchers match on eny item in a range in any order.
     * @param matchers
     * @param <T>
     * @return
     */
    public static <T> Matcher<Iterable> contains(final Matcher<? super T>... matchers){
        return contains(asList(matchers));
    }
    
    public static <T> Matcher<Iterable> contains(final Iterable<Matcher<? super T>> matchers) {
        return new TypeSafeMatcher<Iterable>() {
            @Override
            public boolean matchesSafely(Iterable iterable) {
                Matchers:
                for (Matcher<? super T> matcher : matchers) {
                    for (Object o : iterable) {
                        if(matcher.matches(o)){
                            continue Matchers;
                        }
                    }
                    return false;
                }
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("The item(s) was(were) not in the range!");
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static Matcher<Iterable> contains(final Object item) {
        return contains(new Matcher[]{ equalTo(item) } );
    }

    public static Matcher<Iterable> doesNotContain(final Object item) {
        return not(contains(item));
    }
}
