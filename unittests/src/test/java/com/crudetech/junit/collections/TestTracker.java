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

import java.util.Hashtable;

public class TestTracker {
    private static Hashtable<Class<? extends TestTracker>, Integer> executionCount =
            new Hashtable<Class<? extends TestTracker>, Integer>();

    @Test
    public void inheritedTest(){
        Integer count = executionCount.get(getClass());
        if(count == null){
            count = 0;
        }
        count++;
        executionCount.put(getClass(), count);
    }

    public static int getExecutionCount(Class<? extends TestTracker> clazz) {
        Integer count = executionCount.get(clazz);
        return count == null ? 0 : count;
    }

    public static void reset() {
        executionCount.clear();
    }
}
