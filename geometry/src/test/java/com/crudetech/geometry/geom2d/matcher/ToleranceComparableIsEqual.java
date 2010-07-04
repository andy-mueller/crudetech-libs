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
package com.crudetech.geometry.geom2d.matcher;

import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.geometry.geom.ToleranceComparable;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;


public class ToleranceComparableIsEqual<T extends ToleranceComparable<T>> extends TypeSafeMatcher<T> {
    private final T rhs;
    private final Tolerance tol;
    private ToleranceComparableIsEqual(T rhs, Tolerance tol) {
        this.rhs = rhs;
        this.tol = tol;
    }
    @Factory
    public static <U extends ToleranceComparable<U>> Matcher<U> equalTo(U rhs, Tolerance tol){
        return new ToleranceComparableIsEqual<U>(rhs, tol);
    }
    
    @Override
    public boolean matchesSafely(T lhs) {
        return rhs.equals(lhs, tol);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(rhs);
    }
}
