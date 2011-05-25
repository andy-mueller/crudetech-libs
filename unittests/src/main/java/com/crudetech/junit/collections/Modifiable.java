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

import com.crudetech.junit.feature.FeatureFixture;

import java.util.Collection;

public class Modifiable<T> implements FeatureFixture {
    private final Factory<T> factory;

    public Modifiable(Factory<T> factory) {
        this.factory = factory;
    }

    public interface Factory<T> {
        Collection<T> createCollection();

        T createUniqueItem(int id);
    }

}
