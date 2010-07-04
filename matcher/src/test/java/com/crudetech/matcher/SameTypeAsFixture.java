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

import static com.crudetech.matcher.SameTypeAs.isKindOf;
import static com.crudetech.matcher.SameTypeAs.sameTypeAs;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 *
 * @author Andy
 */
public class SameTypeAsFixture {
    @Test
    public void testSameTypeAs(){
        String s1 = "";
        String s2 = "safhaskj";

        assertThat(s1, is(sameTypeAs(s2)));
    }
    @Test
    public void notSameTypeAs(){
        Object o1 = "";
        Object o2 = new Exception();

        assertThat(o1, is(not(sameTypeAs(o2))));
    }
    @Test
    public void notSametypeAsWithInheritance(){
        Object o1 = new Exception();
        Object o2 = new Exception(){};

        assertThat(o1, is(not(sameTypeAs(o2))));
    }
    @Test
    public void testIsKindOf(){
        Object base    = new Exception();
        Object derived = new Exception(){};

        assertThat(derived, is(isKindOf(base)));
    }
    @Test
    public void sameTypeIsKindOf(){
        Object base    = new Exception();
        Object derived = new Exception();

        assertThat(derived, is(isKindOf(base)));
    }
    @Test
    public void testIsNotKindOf(){
        Object base    = "sdfljasf";
        Object derived = new Exception(){};

        assertThat(derived, is(not(isKindOf(base))));
    }
}
