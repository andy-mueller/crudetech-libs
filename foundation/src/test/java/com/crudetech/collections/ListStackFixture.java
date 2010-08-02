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

import java.util.EmptyStackException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class ListStackFixture {
    @Test
    public void peekMethodGivesTopElement() {
        LightweightStack<Integer> stack = new ListStack<Integer>();

        stack.push(42);

        assertThat(stack.peek(), is(42));
    }

    @Test
    public void isEmptyIsFalseAfterPush() {
        LightweightStack<Integer> stack = new ListStack<Integer>();

        stack.push(42);

        assertThat(stack.isEmpty(), is(false));
    }

    @Test
    public void ctorBuildEmptyStack() {
        LightweightStack<Integer> stack = new ListStack<Integer>();

        assertThat(stack.isEmpty(), is(true));
    }

    @Test
    public void isEmptyIsTrueeAfterPushAndPop() {
        LightweightStack<Integer> stack = new ListStack<Integer>();

        stack.push(42);
        stack.pop();
        assertThat(stack.isEmpty(), is(true));
    }

    @Test(expected = EmptyStackException.class)
    public void peekThrowsOnEmptyStack() {
        final LightweightStack<Integer> stack = new ListStack<Integer>();

        stack.peek();
    }

    @Test(expected = EmptyStackException.class)
    public void popThrowsOnEmptyStack() {
        LightweightStack<Integer> stack = new ListStack<Integer>();

        stack.pop();
    }

    @Test
    public void ctorTakesAllvalues() {
        LightweightStack<Integer> stack = new ListStack<Integer>(0,1,2);

        assertThat(stack.peek(), is(2));
        stack.pop();
        assertThat(stack.peek(), is(1));
        stack.pop();
        assertThat(stack.peek(), is(0));
        stack.pop();
        assertThat(stack.isEmpty(), is(true));
    }
}
