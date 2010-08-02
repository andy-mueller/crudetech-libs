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
package com.crudetech.event;

import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class EventSupportFixture {

    @Test
    public void ctor() {
        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        assertThat(sup, is(notNullValue()));
    }

    @Test
    public void addListener() {
        EventListener<EventObject<Integer>> listener = createMock(EventListener.class);
        listener.onEvent(new EventObject<Integer>(42));
        replay(listener);

        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        sup.addListener(listener);

        sup.fireEvent(new EventObject<Integer>(42));

        verify(listener);
    }

    @Test
    public void removeListener() {
        EventListener<EventObject<Integer>> listener = createMock(EventListener.class);
        replay(listener);

        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        sup.addListener(listener);
        sup.removeListener(listener);
        sup.fireEvent(new EventObject<Integer>(42));

        verify(listener);
    }

    @Test
    public void containsListener() {
        EventListener<EventObject<Integer>> listener = createMock(EventListener.class);
        replay(listener);

        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        assertThat(sup.contains(listener), is(false));
        sup.addListener(listener);

        assertThat(sup.contains(listener), is(true));


        verify(listener);
    }

    @Test
    public void fireEventOnMultipleListeners() {
        EventListener<EventObject<Integer>> listener = createMock(EventListener.class);
        listener.onEvent(new EventObject<Integer>(42));
        replay(listener);

        EventListener<EventObject<Integer>> listener2 = createMock(EventListener.class);
        listener2.onEvent(new EventObject<Integer>(42));
        replay(listener2);


        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        sup.addListener(listener);
        sup.addListener(listener2);

        sup.fireEvent(new EventObject<Integer>(42));

        verify(listener);
        verify(listener2);
    }

    @Test
    public void clearListeners() {
        EventListener<EventObject<Integer>> listener = createMock(EventListener.class);
        replay(listener);

        EventListener<EventObject<Integer>> listener2 = createMock(EventListener.class);
        replay(listener2);


        EventSupport<EventObject<Integer>> sup = new EventSupport<EventObject<Integer>>();
        sup.addListener(listener);
        sup.addListener(listener2);


        sup.clearListeners();
        assertThat(sup.getListeners().iterator().hasNext(), is(false));
        verify(listener);
        verify(listener2);
    }
}
