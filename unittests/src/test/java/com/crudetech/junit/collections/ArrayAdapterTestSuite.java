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

import org.junit.runner.RunWith;

import java.util.Collection;

import static java.util.Arrays.asList;

@RunWith(CollectionsSuite.class)
public class ArrayAdapterTestSuite {
    @CollectionProperty(Unmodifiable.class)
    public static Unmodifiable.Factory<Integer> unmodifiableFactory = new Unmodifiable.Factory<Integer>() {
        @Override
        public Collection<Integer> createCollection() {
            return asList(0,1,2);
        }

        @Override
        public Integer createUniqueItem(int id) {
            return id;
        }
    };
}
