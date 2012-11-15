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
package com.crudetech.lang;

import java.util.Iterator;

/**
 * Simple helper class that offers standard comparing functionality with additional
 * null checks. It offers a more extensive variety of comparison methods than
 * the jdk 7 Objects class.
 */
final public class Compare {

    private Compare() {
    }

    /**
     * Compares two objects using using {@link java.lang.Object#equals(Object)}
     * after checking for null references.
     *
     * @param lhs The first object to be compared.
     * @param rhs The second object to be compared.
     * @return true, if both objects are equal.
     */
    public static boolean equals(final Object lhs, final Object rhs) {
        if (lhs == null || rhs == null) {
            return lhs == rhs;
        }
        return lhs.equals(rhs);
    }

    /**
     * Helper to negate one of the comparison methods of this class
     * <pre>
     *     import static com.crudetech.lang.Compare.equals;
     *     import static com.crudetech.lang.Compare.not;
     *     Object lhs = ...;
     *     Object rhs = ...;
     *     if(not(equals(lhs, rhs))
     *         ...
     * </pre>
     *
     * @param b
     * @return The negation of the input parameter.
     */
    public static boolean not(boolean b) {
        return !b;
    }

    public static <T> boolean equals(final T lhs, final T rhs, EqualityComparer<T> comp) {
        if (comp == null) {
            throw new ArgumentNullException("comp");
        }
        if (lhs == null || rhs == null) {
            return lhs == rhs;
        }
        return comp.equals(lhs, rhs);
    }

    public static boolean equals(final CharSequence lhs, final CharSequence rhs) {
        if (lhs == null || rhs == null) {
            return lhs == rhs;
        }

        int l1 = lhs.length();
        int l2 = rhs.length();
        if (l1 != l2) {
            return false;
        }
        for (int idx = 0; idx < l1; ++idx) {
            if (lhs.charAt(idx) != rhs.charAt(idx)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares to double values for equality within a given
     * tolerance.
     *
     * @param lhs The first value to compare.
     * @param rhs The second value to compare.
     * @param tol The tolerance in between which two values are considered
     *            to be equal.
     * @return TRUE, if both values are equal within the given tolerance,
     *         FALSE otherwise.
     */
    public static boolean equals(final double lhs, final double rhs, final double tol) {
        return Math.abs(lhs - rhs) <= tol;
    }

    /**
     * Compares to double values for equality within a
     * tolerance of 1e-6.
     *
     * @param lhs The first value to compare.
     * @param rhs The second value to compare.
     * @return TRUE, if both values are equal within the given tolerance,
     *         FALSE otherwise.
     */
    public static boolean equals(final double lhs, final double rhs) {
        return equals(lhs, rhs, 1e-6);
    }

    /**
     * Compares two {@link Iterable}s by comparing each item.
     *
     * @param lhs The first range to be compared.
     * @param rhs The second range to be compared.
     * @return TRUE if each item in lhs  equals the
     *         corresponding item in rhs. FALSE otherwise.
     */
    public static boolean equals(final Iterable<?> lhs, final Iterable<?> rhs) {
        return equalsIt(lhs, rhs, EqualityComparer.Standard);
    }

    /**
     * Compares two {@link Iterable}s by comparing each item using
     * the provided {@link com.crudetech.lang.EqualityComparer} implementation.
     *
     * @param lhs  The first range to be compared.
     * @param rhs  The second range to be compared.
     * @param comp The comparer to be used for comparisons.
     * @param <T>
     * @return TRUE if each item in lhs  equals the
     *         corresponding item in rhs. FALSE otherwise.
     */
    public static <T> boolean equals(final Iterable<? extends T> lhs, final Iterable<? extends T> rhs, EqualityComparer<T> comp) {
        return equalsIt(lhs, rhs, comp);
    }

    private static <T> boolean equalsIt(final Iterable<? extends T> lhs, final Iterable<? extends T> rhs, EqualityComparer<T> comp) {
        if (lhs == null || rhs == null) {
            return lhs == rhs;
        }

        Iterator<? extends T> i1 = lhs.iterator();
        Iterator<? extends T> i2 = rhs.iterator();

        boolean b1, b2;
        while ((b1 = i1.hasNext()) & (b2 = i2.hasNext())) {
            T t1 = i1.next();
            T t2 = i2.next();
            if (!comp.equals(t1, t2)) {
                break;
            }
        }
        return !b1 && !b2;
    }

    /**
     * Returns the hash code of the given object using {@link Object#hashCode()}
     * or 0 if the passed in reference is null.
     *
     * @param o The object reference.
     * @return The hash cod eof the object or 0 if the reference is null.
     */
    @Deprecated
    public static int hashCode(Object o) {
        return Objects.hashCode(o);
    }

    /**
     * Returns the smaller of both passed in {@link Comparable} implementations
     * or the first if both are equal.
     *
     * @param lhs
     * @param rhs
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> T min(T lhs, T rhs) {
        VerifyArgument.isNotNull("lhs", lhs);
        VerifyArgument.isNotNull("rhs", rhs);
        if (lhs.compareTo(rhs) <= 0) {
            return lhs;
        }
        return rhs;
    }

    /**
     * Returns the greater one of both passed in {@link Comparable} implementations
     * or the first if both are equal.
     *
     * @param lhs
     * @param rhs
     * @param <T>
     * @return
     */
    public static <T extends Comparable<T>> T max(T lhs, T rhs) {
        VerifyArgument.isNotNull("lhs", lhs);
        VerifyArgument.isNotNull("rhs", rhs);
        if (lhs.compareTo(rhs) >= 0) {
            return lhs;
        }
        return rhs;
    }

    public static <T extends Comparable<T>> boolean isLess(T lhs, T rhs) {
        VerifyArgument.isNotNull("lhs", lhs);
        VerifyArgument.isNotNull("rhs", rhs);
        return lhs.compareTo(rhs) < 0;
    }

    public static <T extends Comparable<T>> boolean isLessEqual(T lhs, T rhs) {
        VerifyArgument.isNotNull("lhs", lhs);
        VerifyArgument.isNotNull("rhs", rhs);
        return lhs.compareTo(rhs) <= 0;
    }

    public static <T extends Comparable<T>> boolean isGreater(T lhs, T rhs) {
        VerifyArgument.isNotNull("lhs", lhs);
        VerifyArgument.isNotNull("rhs", rhs);
        return lhs.compareTo(rhs) > 0;
    }

    public static <T extends Comparable<T>> boolean isGreaterEqual(T lhs, T rhs) {
        VerifyArgument.isNotNull("lhs", lhs);
        VerifyArgument.isNotNull("rhs", rhs);
        return lhs.compareTo(rhs) >= 0;
    }

    public static boolean isLess(double lhs, double rhs, double tol) {
        return !equals(lhs, rhs, tol) && lhs < rhs;
    }

    public static boolean isLessEqual(double lhs, double rhs, double tol) {
        return !equals(lhs, rhs, tol) || lhs < rhs;
    }

    public static boolean isGreater(double lhs, double rhs, double tol) {
        return !equals(lhs, rhs, tol) && lhs > rhs;
    }

    public static boolean isGreaterEqual(double lhs, double rhs, double tol) {
        return !equals(lhs, rhs, tol) || lhs > rhs;
    }
}
