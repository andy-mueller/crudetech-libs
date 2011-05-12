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
package com.crudetech.collections;

import java.util.AbstractList;
import java.util.List;

/**
 * A readonly list wrapper. It basically does the same
  * as the unmodifiable wrapper returned by the {@link java.util.Collections} class, however,
  * by using this class you can explicitly state by its type the immutability.
  * * <p>
  * If you have google collections or google guava
  * available, you should consider using ImmutableList instead!
  * @param <T>
 */
public class ReadOnlyList<T> extends AbstractList<T>{
    private final List<T> inner;

    public ReadOnlyList(List<T> inner) {
        this.inner = inner;
    }
    @Override
    public T get(int index) {
        return inner.get(index);
    }

    @Override
    public int size() {
        return inner.size();
    }
}
