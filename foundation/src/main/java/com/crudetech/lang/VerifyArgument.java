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

import java.text.MessageFormat;

/**
 * This class offers basic argument checking functionality.
 * <p/>
 * However, if your project dependency allow for including hamcrest
 * and crudetech matchers, it is recommended to use Verify.verifyThat(..)
 * instead, as it yield a more expressive syntax and allows for combination
 * of different matches.
 */
public class VerifyArgument {
    private VerifyArgument() {
    }

    public static void isNotNull(String argName, Object arg) {
        if (arg == null) {
            throw new ArgumentNullException(argName != null ? argName : "<unspecified>");
        }
    }

    public static void isNotNull(Object arg) {
        isNotNull(null, arg);
    }

    public static <T> void isLess(String argName, T arg, Comparable<? super T> compareTo) {
        if (compareTo.compareTo(arg) <= 0) {
            throw new ArgumentOutOfBoundsException(argName);
        }
    }

    public static <T> void isEqual(String argName, T arg, Comparable<? super T> compareTo) {
        if (compareTo.compareTo(arg) != 0) {
            throw new IllegalArgumentException(MessageFormat.format("The argument {0} was not equal to {1}.", argName, compareTo));
        }
    }

    public static <T> void isNotEqual(String argName, T arg, Comparable<? super T> compareTo) {
        if (compareTo.compareTo(arg) == 0) {
            throw new IllegalArgumentException(MessageFormat.format("The argument {0} was equal to {1}.", argName, compareTo));
        }
    }

    public static <T> void isLessEqual(String argName, T arg, Comparable<? super T> compareTo) {
        if (compareTo.compareTo(arg) < 0) {
            throw new ArgumentOutOfBoundsException(argName);
        }
    }

    public static <T> void isGreaterEqual(String argName, T arg, Comparable<? super T> compareTo) {
        if (compareTo.compareTo(arg) > 0) {
            throw new ArgumentOutOfBoundsException(argName);
        }
    }

    public static <T> void isGreater(String argName, T arg, Comparable<? super T> compareTo) {
        if (compareTo.compareTo(arg) >= 0) {
            throw new ArgumentOutOfBoundsException(argName);
        }
    }
}
