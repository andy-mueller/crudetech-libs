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
package com.crudetech.collections;


/**
 * A lightweight stack interface to hide away the infamous {@link java.util.Stack}
 * which itself derives from {@link java.util.Vector}.
 *
 * @param <T>
 */
public interface LightweightStack<T> extends Iterable<T>{
    /**
     * @return The topmost entry on this stack.
     */
    T peek();

    /**
     * Removes the top entry from this stack.
     */
    void pop();

    /**
     * Pushes an element on the top of this stack.
     * @param item The element to be pushed on the stack.
     */
    void push(T item);

    boolean isEmpty();

    T[] toArray(Class<T> clazz);
}
