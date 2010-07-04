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
package com.crudetech.geometry.geom;


public class FloatCompare {
    private FloatCompare(){}
    public static boolean equals(final double lhs, final double rhs, final double tol) {
        return Math.abs(lhs - rhs) <= tol;
    }
    public static boolean equals(double[] lhs, double[] rhs, double tol) {
        if(lhs.length != rhs.length) return false;

        for(int idx = 0; idx < lhs.length; ++idx){
            if(!equals(lhs[idx], rhs[idx], tol)){
                return false;
            }
        }

        return true;
    }
    public static int hashCode(double d, double tol){

        double tolFactor = 1 / tol;
        return !equals(d, 0.0, tol) ? double2int(d * tolFactor) : 0;
    }
    public static int hashCode(double[] d, double tol){
        if (d == null)
             return 0;

         int result = 1;

         for (double element : d)
             result = 31 * result + hashCode(element, tol);

         return result;
    }
    private static int long2int(long l) {
        return (int) (l ^ (l >>> 32));
    }

    private static int double2int(double d) {
        return long2int((long) d);
    }
}
