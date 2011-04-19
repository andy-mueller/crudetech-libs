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
package com.crudetech.junit.categories.stubs;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public abstract class CategoryTestStub {
    private static Map<Class<? extends CategoryTestStub>, Integer> executedCounts =
            new HashMap<Class<? extends CategoryTestStub>, Integer>();

    public static int getExecutionCount(Class<? extends CategoryTestStub> stub) {
        Integer count = executedCounts.get(stub);
        return count != null ? count : 0;
    }

    @Test
    public void aTestToExecute() {
        Integer count = executedCounts.get(getClass());
        if (count == null) {
            count = 0;
        }
        count++;
        executedCounts.put(getClass(), count);
    }

    public static void reset() {
        executedCounts.clear();
    }
}
