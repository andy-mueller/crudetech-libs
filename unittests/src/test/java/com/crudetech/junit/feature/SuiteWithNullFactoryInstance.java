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

@RunWith(FeaturesSuite.class)
public class SuiteWithNullFactoryInstance {
    public static class Prop1 extends TestTracker {
        public Prop1(Object o){}

    }
    public static class Prop2 extends TestTracker {
        public Prop2(Object o){}
    }

    @Feature(SuiteWithNullFactoryInstance.Prop1.class)
    public static Object factory1 = null;
    @Feature(SuiteWithNullFactoryInstance.Prop2.class)
    public static Object factory2 = new Object();
}
