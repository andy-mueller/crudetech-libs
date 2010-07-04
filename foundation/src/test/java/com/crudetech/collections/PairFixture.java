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
package com.crudetech.collections;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PairFixture {
    @Test
    public void getFirst() throws Exception {
        Pair<Integer, String> p = new Pair<Integer, String>(2, "sdf");
        assertThat(p.getFirst(), is(2));
    }

    @Test
    public void getSecond() throws Exception {
        Pair<Integer, String> p = new Pair<Integer, String>(2, "sdf");
        assertThat(p.getSecond(), is("sdf"));
    }
}
