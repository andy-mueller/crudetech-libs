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
package com.crudetech.lang;


/**
 * When implemented it enables you to override an objects {@link Object#hashCode()}
 * and {@link Object#equals(Object)} methods.
 */
public interface EqualityComparer<T> {
    boolean equals(T lhs, T rhs);
    int hashCode(T item);

    public static final EqualityComparer<Object> Standard = new EqualityComparer<Object>() {
        @Override
        public boolean equals(Object lhs, Object rhs) {
            return lhs != null ? lhs.equals(rhs) : lhs == rhs;
        }

        @Override
        public int hashCode(Object item) {
            return item == null ? 0 : item.hashCode();
        }
    };
}
