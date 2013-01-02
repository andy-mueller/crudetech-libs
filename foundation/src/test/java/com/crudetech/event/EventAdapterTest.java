package com.crudetech.event;

import com.crudetech.functional.UnaryFunction;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

public class EventAdapterTest {

    private EventSupport<OneEventObject> oneEvent;
    private AnotherSender anotherSender;
    private Event<AnotherEventObject> adaptedEvent;
    private AnotherEventHandlerStub handler;

    @Before
    public void setUp() throws Exception {
        oneEvent = new EventSupport<OneEventObject>();
        anotherSender = new AnotherSender();
        UnaryFunction<OneEventObject, AnotherEventObject> eventObjectConverter = new UnaryFunction<OneEventObject, AnotherEventObject>() {
            @Override
            public AnotherEventObject execute(OneEventObject oneEventObjects) {
                return new AnotherEventObject(anotherSender);
            }
        };

        adaptedEvent = EventAdapter.adapt(oneEvent, eventObjectConverter);
        handler = new AnotherEventHandlerStub();
        adaptedEvent.addListener(handler);
    }

    @Test
    public void givenAdaptedEvent_AddingListenerIsAdaptedToOriginalEvent() throws Exception {
        oneEvent.fireEvent(new OneEventObject(new OneSender()));

        assertThat(handler.eventCount, is(1));
    }

    @Test
    public void givenAdaptedEvent_EventObjectIsAdapted() throws Exception {
        oneEvent.fireEvent(new OneEventObject(new OneSender()));

        assertThat(handler.eventObject.getSource(), is(sameInstance(anotherSender)));
    }

    @Test
    public void givenUnAdaptedEvent_AddingListenerIsAdaptedToOriginalEvent() throws Exception {
        adaptedEvent.removeListener(handler);

        oneEvent.fireEvent(new OneEventObject(new OneSender()));

        assertThat(handler.eventCount, is(0));
    }

    class OneSender {
    }

    class OneEventObject extends EventObject<OneSender> {
        public OneEventObject(OneSender oneSender) {
            super(oneSender);
        }
    }

    class AnotherSender {
    }

    class AnotherEventObject extends EventObject<AnotherSender> {
        public AnotherEventObject(AnotherSender anotherSender) {
            super(anotherSender);
        }
    }

    private class AnotherEventHandlerStub implements EventListener<AnotherEventObject> {
        int eventCount = 0;
        AnotherEventObject eventObject;

        @Override
        public void onEvent(AnotherEventObject e) {
            eventCount++;
            eventObject = e;
        }
    }
}
