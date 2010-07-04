////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//     Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.unittests;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ResizeableListFixtureFixture extends ResizeableListFixture<Integer> {
    @Override
    protected List<Integer> createList() {
        return new ArrayList<Integer>(asList(0,1,2,3,4));
    }

    @Override
    protected List<Integer> createEmptyList() {
        return asList();
    }
    private static int item = 4242;
    @Override
    protected Integer createNewUniqueItem() {
        return new Integer(item++);
    }

}
