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

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class EventHookingBeanFixture {
    @Test
    public void listenersAreAddedToEvent(){
        EventSupport<EventObject<Object>> event = new EventSupport<EventObject<Object>>();
        EventListener<EventObject<Object>> l1 = new EventListener<EventObject<Object>>() {
            @Override
            public void onEvent(EventObject<Object> e) {
            }
        };
        EventListener<EventObject<Object>> l2 = new EventListener<EventObject<Object>>() {
            @Override
            public void onEvent(EventObject<Object> e) {
            }
        };
        EventHookingBean eventHook = new EventHookingBean(event, asList(l1, l2));
        assertThat((List<EventListener<EventObject<Object>>>)event.getListeners(), is(asList(l1, l2)));
    }
    @Test
    public void listenersAreRemovedWhenDestroyed(){
        EventSupport<EventObject<Object>> event = new EventSupport<EventObject<Object>>();
        EventListener<EventObject<Object>> l1 = new EventListener<EventObject<Object>>() {
            @Override
            public void onEvent(EventObject<Object> e) {
            }
        };
        EventListener<EventObject<Object>> l2 = new EventListener<EventObject<Object>>() {
            @Override
            public void onEvent(EventObject<Object> e) {
            }
        };
        EventHookingBean eventHook = new EventHookingBean(event, asList(l1, l2));
        eventHook.destroy();
        assertThat(event.getListeners().iterator().hasNext(), is(false));
    }
}
