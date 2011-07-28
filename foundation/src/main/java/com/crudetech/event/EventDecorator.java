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
 * When a class that has an {@link Event} needs to be decorated, this typically
 * applies to the events of this class, too. It turned out, that in java writing such
 * event decorating code is very verbose and non trivial, especially when you want
 * to ensure a proper removing of the listeners.
 * <p>
 * This class is thread safe.
 * <p/>
 * Here is a simple decorating example showing the use of this class:
 * <pre>
 *
 * interface Holder {
 *     Event&lt;HolderEventObject&gt; getEvent();
 * }
 *
 * class DecoratedHolder implements Holder {
 *     private final EventSupport&lt;HolderEventObject&gt; event = new EventSupport&lt;&gt;();
 *
 *     void raiseEvent(HolderEventObject e) {
 *         getEvent().fireEvent(e);
 *     }
 *
 *     &#064;Override
 *     public EventSupport&lt;HolderEventObject&gt; getEvent() {
 *         return event;
 *     }
 * }

 * class HolderDecorator implements Holder {
 *     private final EventDecorator&lt;Holder, HolderEventObject&gt; decoratedEvent;
 *
 *     public HolderDecorator(Holder decorated) {
 *         decoratedEvent = new EventDecorator&lt;Holder, HolderEventObject&gt;(this, decorated.getEvent()) {
 *             &#064;Override
 *             public HolderEventObject createDecoratedEventObject(HolderEventObject e, Holder src) {
 *                 return new HolderEventObject(src, e.getData());
 *             }
 *         };
 *     }
 *     &#064;Override
 *     public Event&lt;HolderEventObject&gt; getEvent() {
 *         return decoratedEvent;
 *     }
 * }
 *
 * class HolderEventObject extends EventObject&lt;Holder&gt; {
 *     private final int data;
 *     HolderEventObject(final Holder h, int data) {
 *         super(h);
 *         this.data = data;
 *     }
 *     int getData() {
 *         return data;
 *     }
 *     &#064;Override
 *     public boolean equals(Object o) {
 *         if (this == o) return true;
 *         if (o == null || getClass() != o.getClass()) return false;
 *         if (!super.equals(o)) return false;
 *
 *         HolderEventObject that = (HolderEventObject) o;
 *
 *         if (data != that.data) return false;
 *
 *         return true;
 *     }
 *
 *     &#064;Override
 *     public int hashCode() {
 *         int result = super.hashCode();
 *         result = 31 * result + data;
 *         return result;
 *     }
 * }
 *
 * </pre>
 */
public abstract class EventDecorator<TEventSource, TEventObject extends EventObject<TEventSource>> implements Event<TEventObject> {
    private final EventSupport<TEventObject> event = new EventSupport<>();
    private final TEventSource decorated;
    private final Event<TEventObject> decoratedEvent;
    private final Object SyncLock = new Object();


    public EventDecorator(TEventSource decorated, Event<TEventObject> decoratedEvent) {
        this.decorated = decorated;
        this.decoratedEvent = decoratedEvent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addListener(EventListener<TEventObject> e) {
        EventListener<TEventObject> adapter = createAdapter();
        synchronized (SyncLock) {
            event.addListener(e);
            handlers.put(e, adapter);
            decoratedEvent.addListener(adapter);
        }
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

    /**
     * When implemented, it returns a new {@link EventObject} implementation, that has
     * the data of the input event object, but refers to the passed in source object as
     * its {@link EventObject#source} property.
     * @param e The original event object where the data is copied from.
     * @param src The new event objects source property
     * @return A new event object.
     */
    protected abstract TEventObject createDecoratedEventObject(TEventObject e, TEventSource src);

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListener(EventListener<TEventObject> e) {
        synchronized (SyncLock) {
            EventListener<TEventObject> adapter = handlers.get(e);
            if (adapter == null) {
                throw new IllegalArgumentException("The provided listener was not registered with this object!");
            }
            event.removeListener(e);
            decoratedEvent.removeListener(adapter);
            handlers.remove(e);
        }
    }
}
