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
public class SerializableFixture {
    @Feature(Serializable.class)
    public static Serializable.Factory<Integer> seializableFeature(){
        return new Serializable.Factory<Integer>() {
            @Override
            public Integer createObject() {
                return 42;
            }
        };
    }
}
