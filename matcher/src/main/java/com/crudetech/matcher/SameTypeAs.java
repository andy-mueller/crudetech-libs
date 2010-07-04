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

/**
 * Creates a {@link org.hamcrest.Matcher} that can be used to check if two given objects are
 * from the exact same type.
 */
public class SameTypeAs {
    private SameTypeAs(){}
    @Factory
    public static Matcher<Object> sameTypeAs(final Object rhs) {
        return new TypeSafeMatcher<Object>(){

            @Override
            public boolean matchesSafely(Object lhs) {
                if(lhs != null && rhs != null)
                    return lhs.getClass().equals(rhs.getClass());
                return false;
            }

            public void describeTo(Description desc) {
                desc.appendText("The objects don't have the same type:");
                desc.appendValue(rhs);
            }

        };
    }
    @Factory
    public static Matcher<Object> isKindOf(final Object rhs) {
        return new TypeSafeMatcher<Object>(){

            @Override
            public boolean matchesSafely(Object lhs) {
                if(lhs != null && rhs != null)
                    return rhs.getClass().isAssignableFrom(lhs.getClass());
                return false;
            }

            public void describeTo(Description desc) {
                desc.appendValue(rhs);
            }

        };
    }

}
