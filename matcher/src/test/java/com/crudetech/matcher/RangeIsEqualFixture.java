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

import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class RangeIsEqualFixture {
    @Test
    public void isEqual(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,3,4);
        Iterable<Integer> i2 = Arrays.asList(0,1,2,3,4);

        assertThat(i1, is(equalTo(i2)));
    }
    @Test
    public void isEqualOnArray(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,3,4);

        assertThat(i1, is(equalTo(0,1,2,3,4)));
    }

    @Test
    public void isEqualContainingNull(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,null,4);
        Iterable<Integer> i2 = Arrays.asList(0,1,2,null,4);

        assertThat(i1, is(equalTo(i2)));
    }

    @Test
    public void onEmptyRanges(){
        Iterable<Integer> i1 = Arrays.asList();
        Iterable<Integer> i2 = Arrays.asList();

        assertThat(i1, is(equalTo(i2)));
    }
    @Test
    public void isNotEqual(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,3,42);
        Iterable<Integer> i2 = Arrays.asList(0,1,2,3,4 );

        assertThat(i1, is(not(equalTo(i2))));
    }
    @Test
    public void isNotEqualFirstLonger(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,3,4,5);
        Iterable<Integer> i2 = Arrays.asList(0,1,2,3,4  );

        assertThat(i1, is(not(equalTo(i2))));
    }
    @Test
    public void isNotEqualSecondLonger(){
        Iterable<Integer> i1 = Arrays.asList(0,1,2,3,4  );
        Iterable<Integer> i2 = Arrays.asList(0,1,2,3,4,5);

        assertThat(i1, is(not(equalTo(i2))));
    }

}
