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

import org.junit.Test;

import java.util.Collection;

public class Unmodifiable<T> {
    private final Factory<T> factory;

    public Unmodifiable(Factory<T> factory) {
        this.factory = factory;
    }

    public interface Factory<T> {
        Collection<T> createCollection();

        T createUniqueItem(int id);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void addIsUnsupported() {
        Collection<T> unmodifyable = factory.createCollection();
        unmodifyable.add(factory.createUniqueItem(42));
    }
    @Test(expected = UnsupportedOperationException.class)
    public void clearThrowsUnsupportedOperationException(){
        Collection<T> unmodifyable = factory.createCollection();
        unmodifyable.clear();
    }
    @Test(expected = UnsupportedOperationException.class)
    public void removeThrowsUnsupportedOperationException(){
        Collection<T> unmodifyable = factory.createCollection();
        unmodifyable.remove(factory.createUniqueItem(42));
    }
}
