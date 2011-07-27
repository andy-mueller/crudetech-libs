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

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class EventDecoratorTest {

    private DecoratedHolder decorated;
    private Holder decorator;
    private EventListener<HolderEventObject> listener;

    @Before
    public void before() {
        decorated = new DecoratedHolder();

        decorator = new HolderDecorator(decorated);

        @SuppressWarnings("unchecked")
        EventListener<HolderEventObject> tmp = mock(EventListener.class);
        listener = tmp;
        decorator.getEvent().addListener(listener);

    }

    @Test
    public void eventIsDispatchedWithDecoratorAsEventSource() {

        HolderEventObject original = new HolderEventObject(decorated, 42);
        decorated.raiseEvent(original);

        HolderEventObject expected = new HolderEventObject(decorator, 42);
        verify(listener).onEvent(expected);
    }

    @Test
    public void eventIsNotDispatchedWithNoHandler() {
        decorator.getEvent().removeListener(listener);


        HolderEventObject e = new HolderEventObject(decorated, 42);
        decorated.raiseEvent(e);

        verify(listener, never()).onEvent(any(HolderEventObject.class));
    }

    private static interface Holder {
        Event<HolderEventObject> getEvent();
    }

    private static class DecoratedHolder implements Holder {
        private final EventSupport<HolderEventObject> event = new EventSupport<>();

        void raiseEvent(HolderEventObject e) {
            getEvent().fireEvent(e);
        }

        @Override
        public EventSupport<HolderEventObject> getEvent() {
            return event;
        }
    }

    private static class HolderDecorator implements Holder {
        private final EventDecorator<Holder, HolderEventObject> decoratedEvent;

        public HolderDecorator(Holder decorated) {
            decoratedEvent = new EventDecorator<Holder, HolderEventObject>(this, decorated.getEvent()) {
                @Override
                public HolderEventObject createDecoratedEventObject(HolderEventObject e, Holder src) {
                    return new HolderEventObject(src, e.data);
                }
            };
        }

        @Override
        public Event<HolderEventObject> getEvent() {
            return decoratedEvent;
        }
    }

    private static class HolderEventObject extends EventObject<Holder> {
        private final int data;

        HolderEventObject(final Holder h, int data) {
            super(h);
            this.data = data;
        }

        int getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            HolderEventObject that = (HolderEventObject) o;

            if (data != that.data) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + data;
            return result;
        }
    }
}
