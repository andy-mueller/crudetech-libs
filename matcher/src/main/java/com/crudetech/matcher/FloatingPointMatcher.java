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

/**
 * Creates matcher implementations that compare floating point values
 * using a specified tolerance value.
 *
 * @author Andy
 */
public final class FloatingPointMatcher {

    private FloatingPointMatcher() {
    }

    /**
     * Creates matcher implementations that compare floating point values
     * using a specified tolerance value.
     *
     * @param rhs
     * @param tol The tolerance to be under which value two values are
     *            considered equal.
     * @return
     */
    public static Matcher<Double> equalTo(final Double rhs, final double tol) {
        return new TypeSafeMatcher<Double>() {

            @Override
            public boolean matchesSafely(Double lhs) {
                return equ(lhs.doubleValue(), rhs.doubleValue(), tol);
            }

            public void describeTo(Description desc) {
                desc.appendValue(rhs);
            }
        };
    }

    /**
     * Creates matcher implementations that compare floating point values
     * using a specified tolerance value. The tolerance is 1e-6.
     *
     * @param rhs
     * @return
     */
    public static Matcher<Double> equalTo(final Double rhs) {
        return equalTo(rhs, 1e-6);
    }

    /**
     * Creates matcher implementations that compare floating point values
     * using a specified tolerance value.
     *
     * @param rhs
     * @param tol The tolerance to be under which value two values are
     *            considered equal.
     * @return
     */
    public static Matcher<Float> equalTo(final Float rhs, final double tol) {
        return new TypeSafeMatcher<Float>() {

            @Override
            public boolean matchesSafely(Float lhs) {
                return equ(lhs.doubleValue(), rhs.doubleValue(), tol);
            }

            public void describeTo(Description desc) {
                desc.appendValue(rhs);
            }
        };
    }

    /**
     * Creates matcher implementations that compare floating point values
     * using a specified tolerance value. The tolerance is 1e-6.
     *
     * @param rhs
     * @return
     */
    public static Matcher<Float> equalTo(final Float rhs) {
        return equalTo(rhs, 1e-6);
    }

    public static double withTol(final double tol) {
        return tol;
    }

    public static float withTol(final float tol) {
        return tol;
    }

    static boolean equ(final double lhs, final double rhs, final double tol) {
        return Math.abs(lhs - rhs) <= tol;
    }
}
