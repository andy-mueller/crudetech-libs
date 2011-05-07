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
import org.hamcrest.TypeSafeMatcher;

import java.awt.geom.Point2D;

import static com.crudetech.matcher.FloatingPointMatcher.equ;

/**
 * Creates {@link org.hamcrest.Matcher} implementations that compares {@link java.awt.geom.Point2D}
 * using a tolerance: {@code assertThat(new Point2D.Double(2, 4), is(equalTo(new Point2D.Double(2, 4), withTol(1.e-2))); } 
 */
public final class Point2DMatcher {
    public static  Matcher<Point2D> equalTo(final Point2D rhs, final double tol){
        return new TypeSafeMatcher<Point2D>(){

            @Override
            public boolean matchesSafely(Point2D lhs) {
                return equ(lhs.getX(),rhs.getX(), tol)
                    && equ(lhs.getY(), rhs.getY(), tol);
            }

            public void describeTo(Description desc) {
                desc.appendValue(rhs);
            }

        };
    }
    public static  Matcher<Point2D> equalTo(final Point2D rhs){
        return equalTo(rhs, 1e-6);
    }
}
