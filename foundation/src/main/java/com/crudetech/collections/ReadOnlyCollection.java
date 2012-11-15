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

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * A readonly collection wrapper. It basically does the same
 * as the unmodifiable wrapper returned by the {@link Collections} class, however,
 * by using this class you can explicitly state by its type the immutability.
 * * <p>
 * If you have google collections or google guava
 * available, you should consider using ImmutableList instead!
 * @param <T>
 */
public class ReadOnlyCollection<T> extends AbstractCollection<T>{
    private final Collection<T> inner;

    public ReadOnlyCollection(Collection<T> inner) {
        this.inner = inner;
    }
    public static <T> ReadOnlyCollection<T> from(Collection<T> from){
        return new ReadOnlyCollection<T>(from);
    }

    @Override
    public Iterator<T> iterator() {
        return inner.iterator();
    }

    @Override
    public int size() {
        return inner.size();
    }
}
