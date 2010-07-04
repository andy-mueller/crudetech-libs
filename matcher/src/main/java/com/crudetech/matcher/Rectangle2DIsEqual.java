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


import java.awt.geom.Rectangle2D;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.crudetech.matcher.FloatingPointMatcher.equ;

/**
 * Provides {@link Matcher} implementations to compare {@link java.awt.geom.Rectangle2D} implementations.
 */
public class Rectangle2DIsEqual {
    private Rectangle2DIsEqual(){}
    @Factory
    public static Matcher<Rectangle2D> equalTo(final Rectangle2D rhs, final double tol){
        return new TypeSafeMatcher<Rectangle2D>(){

            @Override
            public boolean matchesSafely(Rectangle2D lhs) {
                return equ(lhs.getX(), rhs.getX(), tol)
                    && equ(lhs.getY(), rhs.getY(), tol)
                    && equ(lhs.getWidth(), rhs.getWidth(), tol)
                    && equ(lhs.getHeight(), rhs.getHeight(), tol);
            }

            public void describeTo(Description desc) {
                desc.appendValue(rhs);
            }

        };
    }
    @Factory
    public static Matcher<Rectangle2D> equalTo(final Rectangle2D rhs){
        return equalTo(rhs, 1e-6);
    }
}
