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

import com.crudetech.junit.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;

@Category(ParameterizedCategory.class)
@RunWith(Parameterized.class)
public class ParameterizedStub extends CategoryTestStub{
    private final int val;

    public ParameterizedStub(int val) {
        this.val = val;
    }
    @Parameterized.Parameters
    public static Collection<Integer[]> createData(){
        return asList(new Integer[][]{
                {0},{1}
        });
    }

}
