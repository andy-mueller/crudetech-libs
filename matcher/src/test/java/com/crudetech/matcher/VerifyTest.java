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
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import static com.crudetech.matcher.ThrowsException.doesNotThrow;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static com.crudetech.matcher.Verify.verifyThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VerifyTest {
    @Test
    public void verifyThatThrowsIllegalArgumentExceptionWhenMatcherIsFalse() {
        final Matcher<Object> falseMatcher = new TypeSafeDiagnosingMatcher<Object>() {
            @Override
            protected boolean matchesSafely(Object o, Description description) {
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("false matcher");
            }
        };

        Runnable verifyThatThrowsArgumentNullExceptionWhenCheckedForNull = new Runnable() {
            @Override
            public void run() {
                verifyThat(new Object(), is(falseMatcher));
            }
        };
        assertThat(verifyThatThrowsArgumentNullExceptionWhenCheckedForNull, doesThrow(IllegalArgumentException.class));
    }
    @Test
    public void verifyThatDoesThrowWhenMatcherIsTrue() {
        final Matcher<Object> trueMatcher = new TypeSafeDiagnosingMatcher<Object>() {
            @Override
            protected boolean matchesSafely(Object o, Description description) {
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("true matcher");
            }
        };

        Runnable verifyThatDoesThrowWhenMatcherIsTrue = new Runnable() {
            @Override
            public void run() {
                verifyThat(new Object(), is(trueMatcher));
            }
        };

        assertThat(verifyThatDoesThrowWhenMatcherIsTrue, doesNotThrow(IllegalArgumentException.class));
    }
}
