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

import static com.crudetech.matcher.Verify.verifyThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;

/**
 * A {@link org.hamcrest.Matcher} implementation that checks if exceptions were thrown. It
 * takes a {@link Runnable} implementation, executes it catches any thrown exception and
 * examines it.
 */
public class ThrowsException extends TypeSafeDiagnosingMatcher<Runnable> {
    private final Class<? extends Exception> exceptionClazz;
    private final Class<? extends Exception> causeClazz;
    private final String message;

    private static final String UnspecifiedCause = "<Unspecified>";
    private static final String UnspecifiedMessage = "<Unspecified>";
    private static class UnspecifiedCauseExceptionType extends Exception{}

    private ThrowsException(Class<? extends Exception> exceptionClazz, Class<? extends Exception> causeClazz, String message) {
        verifyThat("exceptionClazz", exceptionClazz, is(notNullValue()));
        verifyThat("causeClazz", causeClazz, is(notNullValue()));
        verifyThat("message", message, not(isEmptyOrNullString()));

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
        return new ThrowsException(exceptionClazz, UnspecifiedCauseExceptionType.class, UnspecifiedMessage);
    }

    public static Matcher<Runnable> doesThrow(final Class<? extends Exception> exceptionClass, Class<? extends Exception> causeClass) {
        return new ThrowsException(exceptionClass, causeClass, UnspecifiedMessage);
    }

    public static Matcher<Runnable> doesThrow(final Class<? extends Exception> exceptionClass, String message) {
        return new ThrowsException(exceptionClass, UnspecifiedCauseExceptionType.class, message);
    }

    public static Matcher<Runnable> doesThrow(final Class<? extends Exception> exceptionClass, String message, Class<? extends Exception> causeClass) {
        return new ThrowsException(exceptionClass, causeClass, message);
    }

    public static Class<? extends Exception> withCause(Class<? extends Exception> causeClass) {
        return causeClass;
    }

    public static String withMessage(String message) {
        return message;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(toString(exceptionClazz, message, causeClazz));
    }

    @Override
    protected boolean matchesSafely(Runnable runnable, Description mismatchDescription) {
        try {
            runnable.run();
        } catch (Exception lastException) {
            boolean ex = exceptionClazz.isAssignableFrom(lastException.getClass());
            boolean cause = causeMatches(lastException);
            boolean msg = message.equals(UnspecifiedMessage) || message.equals(lastException.getMessage());
            boolean didThrowCorrect = ex && cause && msg;
            if(!didThrowCorrect){
                mismatchDescription.appendText(toString(lastException));
            }
            return didThrowCorrect;
        }
        mismatchDescription.appendText("<Nothing>");
        return false;
    }

    private boolean causeMatches(Exception e) {
        return noCause(e) || causeTypeMatches(e);
    }

    private boolean noCause(Exception e) {
        return !(isNotUnspecifiedCause() && hasCause(e));
    }

    private boolean hasCause(Exception e) {
        return e.getCause() != null;
    }

    private boolean isNotUnspecifiedCause() {
        return !UnspecifiedCauseExceptionType.class.isAssignableFrom(causeClazz);
    }

    private boolean causeTypeMatches(Exception e) {
        return causeClazz.isAssignableFrom(e.getCause().getClass());
    }

    @Override
    public String toString() {
        return "ThrowsException{" +
                "exceptionClazz=" + exceptionClazz +
                ", causeClazz=" + causeClazz +
                ", message='" + message + '\'' +
                '}';
    }

    private static String toString(Throwable e){
        if(e == null){
            return UnspecifiedCause;
        }
        String msg = e.getMessage() != null ? e.getMessage() : UnspecifiedMessage;
        return String.format(ExceptionFormat, e.getClass().getName(), msg, toString(e.getCause()));
    }
    private static final String ExceptionFormat = "%s{message=\"%s\", cause=%s}";
    private static String toString(Class<? extends Throwable> clazz, String message, Class<? extends Throwable> cause){
        if(UnspecifiedCauseExceptionType.class.isAssignableFrom(clazz)){
            return UnspecifiedCause;
        }
        String causeString = toString(cause, UnspecifiedMessage, UnspecifiedCauseExceptionType.class);
        return String.format(ExceptionFormat, clazz.getName(), message, causeString);
    }
}
