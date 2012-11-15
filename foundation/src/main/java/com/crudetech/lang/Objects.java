package com.crudetech.lang;

import java.util.Arrays;

/**
 * This class consists of {@code static} utility methods for operating
 * on objects.  These utilities include {@code null}-safe or {@code
 * null}-tolerant methods for computing the hash code of an object,
 * returning a string for an object, and comparing two objects.
 * <p/>
 * Check out the {@link Compare} class for more extensive comparison methods
 * <p/>
 * In Java 7 this class will be obsolete, as a similar class is provided with the java libraries
 */
public final class Objects {
    private Objects() {
    }

    /**
     * Returns {@code true} if the arguments are equal to each other
     * and {@code false} otherwise.
     * <p/>
     * More specific equal comparisons are provided by the {@link Compare} class.
     *
     * @param lhs
     * @param rhs
     * @return
     */
    public static boolean equals(Object lhs, Object rhs) {
        return Compare.equals(lhs, rhs);
    }

    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    public static String toString(Object o) {
        return String.valueOf(o);
    }

    public static <T> T requireNonNull(T value) {
        if(value == null){
            throw new IllegalArgumentException();
        }
        return value;
    }
}
