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

import static com.crudetech.matcher.RangeIsEquivalent.equivalentTo;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class RangeIsEquivalentFixture {

    @Test
    public void isEquivalentTo(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,3,4);
        Iterable<Integer> i2 = Arrays.asList(3,4,2,0,1);

        assertThat(i1, is(equivalentTo(i2)));
    }
    @Test
    public void isEquivalentToWithArray(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,3,4);

        assertThat(i1, is(equivalentTo(3,4,2,0,1)));
    }
    @Test
    public void isEquivalentToWithNull(){
        Iterable<Integer> i1 = asList(0,1,2,null,4);

        assertThat(i1, is(equivalentTo(null,4,2,0,1)));
    }

    @Test
    public void isNotEquivalentTo(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,3,4);
        Iterable<Integer> i2 = Arrays.asList(3,4,2,0,4);

        assertThat(i1, is(not(equivalentTo(i2))));
    }
    @Test
    public void onEmptyRanges(){
        Iterable<Integer> i1 = Arrays.asList();
        Iterable<Integer> i2 = Arrays.asList();

        assertThat(i1, is(equivalentTo(i2)));
    }
    @Test
    public void isNotEquivalentWhenFirstRangeIsLonger(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,3,4);
        Iterable<Integer> i2 = Arrays.asList(3,4,2);

        assertThat(i1, is(not(equivalentTo(i2))));
    }
    @Test
    public void isNotEquivalentWhenSecondRangeIsLonger(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2);
        Iterable<Integer> i2 = Arrays.asList(3,4,2,0,4);

        assertThat(i1, is(not(equivalentTo(i2))));
    }
}
