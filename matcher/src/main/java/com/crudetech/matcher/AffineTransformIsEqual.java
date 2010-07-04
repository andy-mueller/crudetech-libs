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

import java.awt.geom.AffineTransform;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * This class provides a {@link org.hamcrest.Matcher } implementations for {@link java.awt.geom.AffineTransform}
 * matrices.
 */
public final class AffineTransformIsEqual {
    private AffineTransformIsEqual(){}

    /**
     * Provides a {@link org.hamcrest.Matcher} that checks if a matrix is the identity matrix.
     * @param tol The tolerance under which two floating point values are considered equal.
     * @return
     */
    public static  Matcher<AffineTransform> identity( final double tol){
        return equalTo(new AffineTransform(), tol);
    }

    /**
     * Provides a {@link org.hamcrest.Matcher} that checks if a matrix is the identity matrix.
     * @return
     */
    public static  Matcher<AffineTransform> identity( ){
        return equalTo(new AffineTransform());
    }

    /**
     * Provides a {@link org.hamcrest.Matcher} that checks if a matrix are equivalent.
     * @param rhs The matrix to compare to.
     * @param tol The tolerance under which two floating point values are considered equal.
     * @return
     */
    public static  Matcher<AffineTransform> equalTo(final AffineTransform rhs, final double tol){
        return new TypeSafeMatcher<AffineTransform>(){

            @Override
            public boolean matchesSafely(AffineTransform lhs) {
                double[] r = new double[6];
                double[] l = new double[6];
                lhs.getMatrix(l);
                rhs.getMatrix(r);

                for(int i = 0; i < r.length; ++i)
                    if(Math.abs(r[i] - l[i]) > tol)
                        return false;
                return true;
            }

            public void describeTo(Description desc) {

                desc.appendValue(rhs);
            }

        };
    }

    /**
     * Provides a {@link org.hamcrest.Matcher} that checks if a matrix are equivalent. The tolerance under
     * wich two floating point values are considered equal is 1e-6.
     * @param rhs
     * @return
     */
    public static  Matcher<AffineTransform> equalTo(final AffineTransform rhs){
        return equalTo(rhs, 1e-6);
    }
}
