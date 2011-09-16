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
import org.hamcrest.StringDescription;

/**
 * This class can be used for input validation using hamcrest matchers.
 *
 * @author Andy
 */
public class Verify {
    private Verify() {
    }

    /**
     * Verifies that <code>actual</code> satisfies the condition specified by
     * <code>matcher</code>. If not, an {@link java.lang.IllegalArgumentException}
     * is thrown with the reason and information about the matcher and failing
     * value. Example:
     * <p/>
     * <pre>
     *  void bar(int foo){
     *      verifyThat(foo, is(1));
     * }
     * </pre>
     *
     * @param <T>     The static type accepted by the matcher (this can flag obvious
     *                compile-time problems such as {@code verifyThat(1, is("a")}.
     * @param actual  The computed value being compared.
     * @param matcher An expression, built of {@link Matcher}s, specifying allowed
     *                values.
     */
    public static <T> void verifyThat(T actual, Matcher<? super T> matcher) {
        verifyThat("<unspecified>", actual, matcher);
    }

    /**
     * Verifies that <code>actual</code> satisfies the condition specified by
     * <code>matcher</code>. If not, an {@link java.lang.IllegalArgumentException}
     * is thrown with the reason and information about the matcher and failing
     * value. Example:
     * <p/>
     * <pre>
     *  void bar(int foo){
     *      verifyThat(&quot;The parameter foo is bogus!&quot;, foo, is(1));
     * }
     * </pre>
     *
     * @param reason       The argument name.
     * @param <T>          The static type accepted by the matcher (this can flag obvious
     *                     compile-time problems such as {@code verifyThat(1, is("a"))}.
     * @param actual       The computed value being compared.
     * @param matcher      An expression, built of {@link Matcher}s, specifying allowed
     *                     values.
     */
    public static <T> void verifyThat(String reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            Description description = new StringDescription();
            description.appendText(reason)
                       .appendText("\nExpected: ")
                       .appendDescriptionOf(matcher)
                       .appendText("\n     but: ");
            matcher.describeMismatch(actual, description);

            throw new IllegalArgumentException(description.toString());
        }
    }
}
