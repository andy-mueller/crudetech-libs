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

import static com.crudetech.matcher.RangeIsEmpty.isEmpty;
import static com.crudetech.matcher.RangeIsEmpty.isNotEmpty;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;

public class RangeIsEmptyFixture {
    @Test
    public void isEmptyIsTrueOnEmptyRange(){
        Iterable<Integer> range = emptyList();

        assertThat(range, isEmpty());
    }
    @Test
    public void isNotEmptyTest(){
        Iterable<Integer> range = asList(1, 2);

        assertThat(range, isNotEmpty());
    }
}
