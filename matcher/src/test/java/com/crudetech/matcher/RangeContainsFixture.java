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

import static com.crudetech.matcher.RangeContains.contains;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;


public class RangeContainsFixture {
    @Test
    public void containsSucceedsWhenItemIsInRange(){
        Iterable<Integer> range = asList(0,1,2,3,4);

        assertThat(range, contains(2));
    }
    @Test
    public void containsFailsWhenItemIsNotInRange(){
        Iterable<Integer> range = asList(0,1,2,3,4);

        assertThat(range, not(contains(42)));
    }
    @Test
    public void containsSucceedsWhenItemRangeIsInRange(){
        Iterable<Integer> range = asList(0,1,2,3,4);

        assertThat(range, contains(4,2,1));
    }

}
