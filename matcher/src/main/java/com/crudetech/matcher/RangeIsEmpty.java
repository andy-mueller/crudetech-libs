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

import org.hamcrest.*;

import static org.hamcrest.core.IsNot.not;

public class RangeIsEmpty extends TypeSafeDiagnosingMatcher<Iterable<?>>{

    @Override
    protected boolean matchesSafely(Iterable<?> item, Description mismatchDescription) {
        return !item.iterator().hasNext();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("empty");
    }
    @Factory
    public static Matcher<Iterable<?>> isNotEmpty() {
        return not(isEmpty());
    }

    @Factory
    public static Matcher<Iterable<?>> isEmpty() {
        return new RangeIsEmpty();
    }
}
