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
package com.crudetech.collections;

import com.crudetech.junit.feature.Equivalent;
import com.crudetech.junit.feature.Feature;
import com.crudetech.junit.feature.Features;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Features.class)
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

    @Feature(Equivalent.class)
    public static Equivalent.Factory<Pair<Integer, String>> equivalentFactory() {
        return new Equivalent.Factory<Pair<Integer, String>>() {
            @Override
            public Pair<Integer, String> createItem() {
                return new Pair<Integer, String>(2, "default");
            }

            @Override
            public List<Pair<Integer, String>> createOtherItems() {
                return asList(
                        new Pair<Integer, String>(2, "other"),
                        new Pair<Integer, String>(5, "default"),
                        new Pair<Integer, String>(5, "other")
                );
            }
        };
    }
}
