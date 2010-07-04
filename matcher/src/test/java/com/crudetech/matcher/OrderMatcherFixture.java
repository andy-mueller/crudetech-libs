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

import static com.crudetech.matcher.OrderMatcher.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class OrderMatcherFixture {
    @Test
    public void isLessThan(){
         assertThat(1, is(lessThan(2)));
    }
    @Test
    public void isNotLessThan(){
        assertThat(2, is(not(lessThan(1))));
    }
    @Test
    public void isLessEqualThan(){
         assertThat(1, is(lessOrEqualThan(2)));
         assertThat(2, is(lessOrEqualThan(2)));
    }
    @Test
    public void isNotLessEqualThan(){
        assertThat(2, is(not(lessOrEqualThan(1))));
    }

    @Test
    public void isGreaterThan(){
         assertThat(2, is(greaterThan(1)));
    }
    @Test
    public void isNotGreaterThan(){
        assertThat(1, is(not(greaterThan(2))));
    }
    @Test
    public void isGreaterOrEqualThan(){
         assertThat(2, is(greaterOrEqualThan(1)));
         assertThat(1, is(greaterOrEqualThan(1)));
    }
    @Test
    public void isNotGreaterOrEqualThan(){
        assertThat(1, is(not(greaterOrEqualThan(2))));
    }
}
