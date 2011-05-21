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
package com.crudetech.junit.feature;

import org.junit.runner.RunWith;

import java.util.List;

import static java.util.Arrays.asList;

@RunWith(Features.class)
public class EquivalentFixture {

    @Feature(Equivalent.class)
    public static Equivalent.Factory<Integer> factory = new Equivalent.Factory<Integer>() {
        @Override
        public Integer createItem() {
            return new Integer(42);
        }

        @Override
        public List<Integer> createOtherItems() {
            return asList(84, 126, 168);
        }
    };
}
