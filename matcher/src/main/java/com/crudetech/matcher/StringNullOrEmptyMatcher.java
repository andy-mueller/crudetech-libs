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

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.core.IsNot.not;


public class StringNullOrEmptyMatcher {
    public static Matcher<String> notNullOrEmpty() {
        return not(nullOrEmpty());
    }
    public static Matcher<String> nullOrEmpty() {
        return new BaseMatcher<String>() {
            @Override
            public boolean matches(Object o) {
                if(o == null){
                    return true;
                }
                return "".equals(o);
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("Null or Empty String");
            }
        };
    }
}
