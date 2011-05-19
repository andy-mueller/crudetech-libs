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

import com.crudetech.junit.feature.Equivalent;
import com.crudetech.junit.feature.Feature;
import com.crudetech.junit.feature.FeaturesSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(FeaturesSuite.class)
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
        EventHookingBean<EventObject<Object>> eventHook = new EventHookingBean<EventObject<Object>>(event, asList(l1, l2));
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
        EventHookingBean<EventObject<Object>> eventHook = new EventHookingBean<EventObject<Object>>(event, asList(l1, l2));
        eventHook.destroy();
        assertThat(event.getListeners().iterator().hasNext(), is(false));
    }
    @Feature(Equivalent.class)
    public static Equivalent.Factory<EventObject<Integer>> eventObjectFactory = new Equivalent.Factory<EventObject<Integer>>() {
        @Override
        public EventObject<Integer> createItem() {
            return new EventObject<Integer>(new Integer(2));
        }

        @Override
        public List<EventObject<Integer>> createOtherItems() {
            return asList(
                    new EventObject<Integer>(new Integer(4))
            );
        }
    };
}
