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
package com.crudetech.junit.categories;

public class If {
    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isEmpty(Object[] o) {
        return o.length == 0;
    }

    public static boolean isEmpty(String s) {
        return "".equals(s);
    }

    public static boolean isNullOrEmpty(Object[] o) {
        return isNull(o) || isEmpty(o);
    }

    public static boolean isNullOrEmpty(String s) {
        return isNull(s) || isEmpty(s);
    }

    public static boolean isEmpty(Iterable<?> i) {
        return !isNotEmpty(i);
    }

    public static boolean isNotEmpty(Iterable<?> i) {
        return i.iterator().hasNext();
    }

    public static boolean is(boolean b) {
        return b;
    }
}
