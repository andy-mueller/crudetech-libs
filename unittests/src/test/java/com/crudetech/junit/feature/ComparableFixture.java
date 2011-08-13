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

@RunWith(Features.class)
public class ComparableFixture {
    @Feature(Comparable.class)
    public static Comparable.Factory<Integer> comparable(){
        return new Comparable.Factory<Integer>() {
            @Override
            public Integer createX() {
                return 256;
            }

            @Override
            public Integer createY() {
                return 257;
            }

            @Override
            public Integer createZ() {
                return 258;
            }
        };
    }
}
