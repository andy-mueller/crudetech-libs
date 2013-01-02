package com.crudetech.event;

import com.crudetech.functional.UnaryFunction;

import java.util.HashMap;
import java.util.Map;

public class EventAdapter<TToEventSource, TToEventObject extends EventObject<TToEventSource>, TFromEventSource, TFromEventObject extends EventObject<TFromEventSource>> implements Event<TToEventObject> {
    private final Map<EventListener<? super TToEventObject>, EventListener<TFromEventObject>> handlers =
            new HashMap<EventListener<? super TToEventObject>, EventListener<TFromEventObject>>();
    private final UnaryFunction<? super TFromEventObject, TToEventObject> convert;
    private final Event<TFromEventObject> adaptedEvent;

    public EventAdapter(UnaryFunction<? super TFromEventObject, TToEventObject> convert, Event<TFromEventObject> adaptedEvent) {
        this.convert = convert;
        this.adaptedEvent = adaptedEvent;
    }

    public static  <TFromEventSource, TFromEventObject extends EventObject<TFromEventSource>, TToEventSource, TToEventObject extends EventObject<TToEventSource>>
    Event<TToEventObject> adapt(final Event<TFromEventObject> adaptedEvent, final UnaryFunction<? super TFromEventObject, TToEventObject> convert) {
        return new EventAdapter<TToEventSource, TToEventObject, TFromEventSource, TFromEventObject>(convert, adaptedEvent);
    }

    @Override
    public void addListener(final EventListener<? super TToEventObject> listener) {
        EventListener<TFromEventObject> adapter = new EventListener<TFromEventObject>() {
            @Override
            public void onEvent(TFromEventObject e) {
                listener.onEvent(convert.execute(e));
            }
        };
        handlers.put(listener, adapter);
        adaptedEvent.addListener(adapter);
    }

    @Override
    public void removeListener(EventListener<? super TToEventObject> listener) {
        EventListener<TFromEventObject> adapter = handlers.remove(listener);
        adaptedEvent.removeListener(adapter);
    }
}
