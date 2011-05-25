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
package com.crudetech.junit.collections;


import java.util.Iterator;

class Utils {
    static int sizeOf(java.lang.Iterable<?> iterable) {
        int count = 0;
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            it.next();
            count++;
        }
        return count;
    }

    static <T> Iterator<T> end(java.lang.Iterable<T> it) {
        Iterator<T> i = it.iterator();
        while (i.hasNext()) {
            i.next();
        }
        return i;
    }

    static <T> T first(java.lang.Iterable<T> it) {
        Iterator<T> i = it.iterator();
        return i.next();
    }
    static boolean equals(Object lhs, Object rhs){
        return lhs == null ? rhs == null : lhs.equals(rhs);
    }
    static <T> boolean equals(final java.lang.Iterable<? extends T> lhs, final java.lang.Iterable<? extends T> rhs) {
        if (lhs == null || rhs == null) {
            return lhs == rhs;
        }

        Iterator<? extends T> i1 = lhs.iterator();
        Iterator<? extends T> i2 = rhs.iterator();

        boolean b1, b2;
        while ((b1 = i1.hasNext()) & (b2 = i2.hasNext())) {
            T t1 = i1.next();
            T t2 = i2.next();
            if (!equals(t1, t2)) {
                break;
            }
        }
        return !b1 && !b2;
    }
}
