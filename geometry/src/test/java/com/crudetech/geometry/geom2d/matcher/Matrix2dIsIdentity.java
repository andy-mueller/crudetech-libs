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
import com.crudetech.geometry.geom2d.Matrix2d;
import com.crudetech.lang.Compare;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import static com.crudetech.geometry.geom.Matrix.column;
import static com.crudetech.geometry.geom.Matrix.row;


public class Matrix2dIsIdentity extends TypeSafeMatcher<Matrix2d>{
    private final Tolerance tol;
    private Matrix2dIsIdentity(Tolerance tol) {
        this.tol = tol;
    }
    public static Matcher<Matrix2d> isIdentity(Tolerance tol){
        return new Matrix2dIsIdentity(tol);
    }
    @Override
    public boolean matchesSafely(Matrix2d lhs) {
        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                if(i == j){
                    if(!Compare.equals(lhs.get(row(i), column(j) ), 1.0, tol.getVectorTolerance())){
                        return false;
                    }
                }else{
                    if(!Compare.equals(lhs.get(row(i), column(j)), 0.0, tol.getVectorTolerance())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(Matrix2d.Identity);
    }
}
