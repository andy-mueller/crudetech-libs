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
public class SuiteWithMethodAsFactory {
    public static class Prop1 extends TestTracker {
        interface Factory{}
        public Prop1(Factory o){}
    }

    @Feature(SuiteWithMethodAsFactory.Prop1.class)
    public static Prop1.Factory prop1Feature(){
        return new Prop1.Factory(){};
    }
}
