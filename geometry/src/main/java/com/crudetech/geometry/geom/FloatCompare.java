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
package com.crudetech.geometry.geom;


/**
 * Helper class to interact with floating point types. THis class differs from
 * the JDK classes such as {@link java.util.Arrays} in that it respects a given
 * tolerance for all its calculations.
 */
public class FloatCompare {
    private FloatCompare() {
    }

    /**
     * Checks if two floating point types are equal by
     * comparing equivalence within the given tolerance.
     *
     * @param lhs The first argument for the comparison.
     * @param rhs The second argument for the comparison.
     * @param tol the tolerance in with two floating point types are considered to be equal
     * @return True, if both arguments are equal within the given tolerance. False
     *         if otherwise.
     */
    public static boolean equals(final double lhs, final double rhs, final double tol) {
        return Math.abs(lhs - rhs) <= tol;
    }

    /**
     * Checks if two arrays of floating point type are equal by
     * comparing them element wise for equivalence within
     * the given tolerance.
     *
     * @param lhs The first array for the comparison.
     * @param rhs The second array for the comparison.
     * @param tol the tolerance in with two floating point are considered to be equal
     * @return True, if all elements of both arguments are equal within the given tolerance. False
     *         if otherwise.
     */
    public static boolean equals(double[] lhs, double[] rhs, double tol) {
        if (lhs.length != rhs.length) return false;

        for (int idx = 0; idx < lhs.length; ++idx) {
            if (!equals(lhs[idx], rhs[idx], tol)) {
                return false;
            }
        }

        return true;
    }
    /**
     * Checks if two arrays of floating point type are equal by
     * comparing them element wise for equivalence within
     * the given tolerance.
     *
     * @param lhs The first array for the comparison.
     * @param rhs The second array for the comparison.
     * @param tol the tolerance in with two floating point are considered to be equal
     * @return True, if all elements of both arguments are equal within the given tolerance. False
     *         if otherwise.
     */
    public static boolean equals(float[] lhs, float[] rhs, float tol) {
        if (lhs.length != rhs.length) return false;

        for (int idx = 0; idx < lhs.length; ++idx) {
            if (!equals(lhs[idx], rhs[idx], tol)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculates the has hash code for a floating point type. In contrast to
     * {@link Double#hashCode()}, it reflects the given tolerance, so that
     * two floating point types that are equal by [@link #equals} return the same
     * hash code.                                     <
     *
     * @param d The input to compute te hash code for.
     * @param tol The tolerance in that two floats are considered equal.
     * @return A hash code fot the input.
     */
    public static int hashCode(double d, double tol) {
        double tolFactor = 1 / tol;
        return !equals(d, 0.0, tol) ? double2int(d * tolFactor) : 0;
    }

    /**
     * Calculates the has hash code for a floating point type array. In contrast to
     * {@link java.util.Arrays#hashCode(double[])}, it also reflects the given tolerance, so that
     * two floating point types that are equal by by [@link #equals} return the same
     * hash code.                                     <
     *
     * @param d The input to compute te hash code for.
     * @param tol The tolerance in that two floats are considered equal.
     * @return A hash code fot the input.
     */
    public static int hashCode(double[] d, double tol) {
        if (d == null)
            return 0;

        int result = 1;

        for (double element : d) {
            result = 31 * result + hashCode(element, tol);
        }
        return result;
    }
    /**
     * Calculates the has hash code for a floating point type array. In contrast to
     * {@link java.util.Arrays#hashCode(double[])}, it also reflects the given tolerance, so that
     * two floating point types that are equal by by [@link #equals} return the same
     * hash code.                                     <
     *
     * @param d The input to compute te hash code for.
     * @param tol The tolerance in that two floats are considered equal.
     * @return A hash code fot the input.
     */
    public static int hashCode(float[] d, float tol) {
        if (d == null)
            return 0;

        int result = 1;

        for (double element : d) {
            result = 31 * result + hashCode(element, tol);
        }
        return result;
    }

    private static int long2int(long l) {
        return (int) (l ^ (l >>> 32));
    }

    private static int double2int(double d) {
        return long2int((long) d);
    }
}
