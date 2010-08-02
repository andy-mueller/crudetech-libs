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

import com.crudetech.lang.AbstractRunnable;
import org.junit.Test;

import static com.crudetech.matcher.StringNullOrEmptyMatcher.nullOrEmpty;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static com.crudetech.matcher.Verify.verifyThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class StringNullOrEmptyMatcherTest {
    @Test
    public void stringIsNullOrEmptyOnStringThrows(){
        Runnable doVerify = new AbstractRunnable() {
            @Override
            protected void doRun() throws Throwable {
                verifyThat("test", is(nullOrEmpty()));
            }
        };

        assertThat(doVerify, doesThrow(IllegalArgumentException.class));
    }
    @Test
    public void stringIsNullOrEmptyOnEmptyStringThrows(){
        verifyThat("", is(nullOrEmpty()));
    }
    @Test
    public void stringIsNullOrEmptyOnNullStringThrows(){
        verifyThat((String)null, is(nullOrEmpty()));
    }
}
