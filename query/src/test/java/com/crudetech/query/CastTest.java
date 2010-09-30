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
package com.crudetech.query;

import org.junit.Test;

import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static com.crudetech.query.Query.from;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class CastTest {
    @Test
    public void cast() {
        Iterable<Integer> expected = asList(0, 2, 7, 5);

        Iterable<Number> actual = from(expected).cast(Number.class);

        assertThat(actual, is(equalTo((Iterable) expected)));
    }
    @Test
    public void castFails() {
        Iterable<Integer> expected = asList(0, 2, 7, 5);

        Iterable<Long> actual = from(expected).cast(Long.class);

//        long first = actual.iterator().next();
//        for(long l : actual){}
        assertThat(actual, is(equalTo((Iterable) expected)));
    }
}
