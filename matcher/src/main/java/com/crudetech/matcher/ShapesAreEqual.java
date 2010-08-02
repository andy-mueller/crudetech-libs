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

import java.awt.*;
import java.awt.geom.PathIterator;

import static com.crudetech.matcher.FloatingPointMatcher.equ;

public class ShapesAreEqual {
    public static Matcher<Shape> equalTo(final Shape rhs){
        return equalTo(rhs, 1e-6);
    }
    public static Matcher<Shape> equalTo(final Shape rhs, final double tol){
        return new TypeSafeMatcher<Shape>(){
            @Override
            public boolean matchesSafely(Shape lhs) {
                PathIterator ilhs = lhs.getPathIterator(null);
                PathIterator irhs = rhs.getPathIterator(null);

                boolean bl, br;
                while(!(bl = ilhs.isDone()) & !(br = irhs.isDone())){
                    float[] ptLhs = new float[6];
                    float[] ptRhs = new float[6];

                    if(ilhs.currentSegment(ptLhs) != irhs.currentSegment(ptRhs))
                        return false;
                    for(int i = 0; i < 6; ++i)
                        if(!equ(ptLhs[i], ptRhs[i], tol))
                            return false;

                    ilhs.next();
                    irhs.next();
                }
                return bl == br;
            }

            public void describeTo(Description desc) {
                desc.appendValue(rhs);
            }
        };
    }
}
