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
package com.crudetech.matcher;

import org.junit.Test;

import java.util.Arrays;

import static com.crudetech.matcher.RangeHasSize.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class RangeHasSizeFixture {
    @Test
    public void hasSizeOfTest(){
        Iterable<Integer> range = Arrays.asList(0,1,2,3);

        assertThat(range, hasSizeOf(4));
    }
    @Test
    public void hasSizeOfWitheEmptyRange(){
        Iterable<Integer> range = Arrays.asList();

        assertThat(range, hasSizeOf(0));
    }
    @Test
    public void isEmptyTest(){
        Iterable<Integer> range = Arrays.asList();

        assertThat(range, isEmpty());
    }
    @Test
    public void isNotEmptyTest(){
        Iterable<Integer> range = Arrays.asList(1,2);

        assertThat(range, isNotEmpty());
    }
}