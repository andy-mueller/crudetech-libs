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
package com.crudetech.matcher;

import org.junit.Test;

import static com.crudetech.matcher.RangeHasSize.hasSizeOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;


public class RangeHasSizeFixture {
    @Test
    public void hasSizeOfTest(){
        Iterable<Integer> range = asList(0, 1, 2, 3);

        assertThat(range, hasSizeOf(4));
    }
    @Test
    public void hasSizeOfWitheEmptyRange(){
        Iterable<Integer> range = emptyList();

        assertThat(range, hasSizeOf(0));
    }

}