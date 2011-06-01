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
package com.crudetech.event;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class EventSupportFixture {

    @Test
    public void ctor() {
        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        assertThat(sup, is(notNullValue()));
    }

    @Test
    public void addListener() {
        EventListener<EventObject<Integer>> listener = mock(EventListener.class);

        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        sup.addListener(listener);

        sup.fireEvent(new EventObject<Integer>(42));

        verify(listener).onEvent(new EventObject<Integer>(42));
    }

    @Test
    public void removeListener() {
        EventListener<EventObject<Integer>> listener = mock(EventListener.class);

        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        sup.addListener(listener);
        sup.removeListener(listener);
        sup.fireEvent(new EventObject<Integer>(42));

        verify(listener, never()).onEvent(any(EventObject.class));
    }

    @Test
    public void containsListener() {
        EventListener<EventObject<Integer>> listener = mock(EventListener.class);

        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        assertThat(sup.contains(listener), is(false));
        sup.addListener(listener);

        assertThat(sup.contains(listener), is(true));
    }

    @Test
    public void fireEventOnMultipleListeners() {
        EventListener<EventObject<Integer>> listener = mock(EventListener.class);

        EventListener<EventObject<Integer>> listener2 = mock(EventListener.class);


        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        sup.addListener(listener);
        sup.addListener(listener2);

        sup.fireEvent(new EventObject<Integer>(42));

        verify(listener).onEvent(new EventObject<Integer>(42));
        verify(listener2).onEvent(new EventObject<Integer>(42));
    }

    @Test
    public void clearListeners() {
        EventListener<EventObject<Integer>> listener = mock(EventListener.class);

        EventListener<EventObject<Integer>> listener2 = mock(EventListener.class);


        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        sup.addListener(listener);
        sup.addListener(listener2);


        sup.clearListeners();
        assertThat(sup.getListeners().iterator().hasNext(), is(false));
        verify(listener, never()).onEvent(any(EventObject.class));
        verify(listener2, never()).onEvent(any(EventObject.class));
    }
}
