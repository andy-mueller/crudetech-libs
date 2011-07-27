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

import java.util.HashMap;
import java.util.Map;

/**
 * When a class that has an {@link Event} needs to be decorated this typically
 * applies to the events of this class, too.
 */
public abstract class EventDecorator<TEventSource, TEventObject extends EventObject<TEventSource>> implements Event<TEventObject> {
    private final EventSupport<TEventObject> event = new EventSupport<>();
    private final TEventSource decorated;
    private final Event<TEventObject> decoratedEvent;


    public EventDecorator(TEventSource decorated, Event<TEventObject> decoratedEvent) {
        this.decorated = decorated;
        this.decoratedEvent = decoratedEvent;
    }

    @Override
    public void addListener(EventListener<TEventObject> e) {
        EventListener<TEventObject> adapter = createAdapter();
        event.addListener(e);
        handlers.put(e, adapter);
        decoratedEvent.addListener(adapter);
    }

    private final Map<EventListener<TEventObject>, EventListener<TEventObject>> handlers = new HashMap<>();

    private EventListener<TEventObject> createAdapter() {
        return new EventListener<TEventObject>() {
            @Override
            public void onEvent(TEventObject e) {
                TEventObject cloned = createDecoratedEventObject(e, decorated);
                event.fireEvent(cloned);
            }
        };
    }

    protected abstract TEventObject createDecoratedEventObject(TEventObject e, TEventSource src);

    @Override
    public void removeListener(EventListener<TEventObject> e) {
        EventListener<TEventObject> adapter = handlers.get(e);
        event.removeListener(e);
        decoratedEvent.removeListener(adapter);
        handlers.remove(e);
    }
}
