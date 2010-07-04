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

import static org.hamcrest.core.IsNot.not;

/**
 * A {@link org.hamcrest.Matcher} implementation that checks if exceptions were thrown. It
 * takes a {@link Runnable} implementation, executes it catches any thrown exception and
 * examines it.
 */
public class ThrowsException extends TypeSafeMatcher<Runnable> {
    private final  Class<? extends Exception> exceptionClazz;
    private final  Class<? extends Exception> causeClazz;
    private final  String message;

    private ThrowsException(Class<? extends Exception> exceptionClazz, Class<? extends Exception> causeClazz, String message) {
        this.exceptionClazz = exceptionClazz;
        this.causeClazz = causeClazz;
        this.message = message;
    }

    public static <T extends Exception> Matcher<Runnable> doesNotThrow(final Class<T> exceptionClazz) {
        return not(doesThrow(exceptionClazz));
    }

    public static Matcher<Runnable> doesNotThrow() {
        return not(doesThrow(Exception.class));
    }

    public static <T extends Exception> Matcher<Runnable> doesThrow(final Class<T> exceptionClazz) {
        return new ThrowsException(exceptionClazz, null, null);
    }

    public static Matcher<Runnable> doesThrow(final Class<? extends Exception> exceptionClass, Class<? extends Exception> causeClass) {
        return new ThrowsException(exceptionClass, causeClass, null);
    }
    public static Matcher<Runnable> doesThrow(final Class<? extends Exception> exceptionClass, String message) {
        return new ThrowsException(exceptionClass, null, message);
    }
     public static Matcher<Runnable> doesThrow(final Class<? extends Exception> exceptionClass, String message,Class<? extends Exception> causeClass) {
        return new ThrowsException(exceptionClass, causeClass, message);
    }

    public static Class<? extends Exception> withCause(Class<? extends Exception> causeClass) {
        return causeClass;
    }
    public static String withMessage(String message) {
        return message;
    }

    @Override
    public boolean matchesSafely(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception lastException) {
            boolean ex = exceptionClazz.isAssignableFrom(lastException.getClass());
            boolean cause = (causeClazz != null && lastException.getCause() != null) ? causeClazz.isAssignableFrom(lastException.getCause().getClass()) : true;
            boolean msg = message != null ? message.equals(lastException.getMessage()) : true;
            return ex && cause && msg;
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Does not throw an exception of type" + exceptionClazz);
    }
}
