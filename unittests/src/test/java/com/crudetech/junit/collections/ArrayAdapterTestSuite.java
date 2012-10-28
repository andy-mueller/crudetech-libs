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

import com.crudetech.junit.feature.Feature;
import com.crudetech.junit.feature.Features;
import org.junit.runner.RunWith;

import java.util.Collection;

import static java.util.Arrays.asList;

@RunWith(Features.class)
public class ArrayAdapterTestSuite {
    @Feature(Unmodifiable.class)
    public static Unmodifiable.Factory<Integer> unmodifiableFeature() {
        return new Unmodifiable.Factory<Integer>() {
            @Override
            public Collection<Integer> createCollection() {
                return asList(0, 1, 2);
            }

            @Override
            public Integer createUniqueItem(int id) {
                return id;
            }
        };
    }
    @Feature(Iterable.class)
    public static Iterable.Factory<Integer> iterableFeature() {
        return new Iterable.Factory<Integer>() {
            @Override
            public java.lang.Iterable<Integer> createIterable() {
                return asList(0, 1, 2);
            }
        };
    }
}
