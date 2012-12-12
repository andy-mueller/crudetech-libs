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

/**
 * A very simple bean that takes a collection of {@link com.crudetech.event.EventListener}and
 * adds them to the passed in{@link com.crudetech.event.Event}. It is intended to be used
 * in conjunction with a DI framework like spring to wire up events in the configuration.
 * <p>
 * Please refer to the {@link EventSupport} documentation for details on
 * how to use the type safe event mechanism.
 * @param <TEventObject>
 */
public class EventHookingBean<TEventObject extends EventObject<?>> {
    private final Event<? extends TEventObject> event;
    private final Iterable<EventListener<? super  TEventObject>> eventListeners;

    public EventHookingBean(Event<? extends TEventObject> event, Iterable<EventListener<? super  TEventObject>> eventListeners) {
        this.event = event;
        this.eventListeners = eventListeners;
        for (EventListener<? super TEventObject> listener : eventListeners) {
            event.addListener(listener);
        }
    }

    public void destroy() {
        for (EventListener<? super TEventObject> listener : eventListeners) {
            event.removeListener(listener);
        }
    }
}
