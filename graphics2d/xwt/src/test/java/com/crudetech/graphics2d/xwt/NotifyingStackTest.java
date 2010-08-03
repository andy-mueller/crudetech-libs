package com.crudetech.graphics2d.xwt;

import com.crudetech.event.EventListener;
import com.crudetech.event.EventObject;
import org.junit.Test;

import java.util.List;

import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class NotifyingStackTest {
    @Test
    public void copyCtorDoesNotFireNotification() throws CloneNotSupportedException {
        EventListener<EventObject<NotifyingStack<Integer>>> listener = mock(EventListener.class);

        NotifyingStack<Integer> stack = new NotifyingStackImp(asList(1, 2, 3));
        stack.getPushEvent().addListener(listener);
        stack.getPopEvent().addListener(listener);

        verify(listener, never()).onEvent((EventObject<NotifyingStack<Integer>>) any());
    }

    @Test
    public void popEventIsRaised() {
        EventListener<EventObject<NotifyingStack<Integer>>> popListener = mock(EventListener.class);
        NotifyingStackImp<Integer> stack = new NotifyingStackImp<Integer>(asList(41, 42));

        stack.getPopEvent().addListener(popListener);

        stack.pop();
        stack.pop();

        verify(popListener, times(2)).onEvent(new EventObject<NotifyingStack<Integer>>(stack));
    }

    @Test
    public void pushEventIsRaised() {
        EventListener<EventObject<NotifyingStack<Integer>>> pushListener = mock(EventListener.class);
        NotifyingStackImp<Integer> stack = new NotifyingStackImp<Integer>(asList(41, 42));

        stack.getPushEvent().addListener(pushListener);

        stack.push(353);
        stack.pop();
        stack.push(3525);

        verify(pushListener, times(2)).onEvent(new EventObject<NotifyingStack<Integer>>(stack));
    }

    @Test
    public void toArray() {
        NotifyingStackImp<Integer> stack = new NotifyingStackImp<Integer>(asList(0, 1, 2, 3));

        List<Integer> copy = asList(stack.toArray(Integer.class));
        assertThat(copy, is(equalTo(stack)));
    }

    @Test
    public void clearMakesStackEmptyAndDoesNotRaiseEvents() {
        EventListener<EventObject<NotifyingStack<Integer>>> listener = mock(EventListener.class);
        NotifyingStackImp<Integer> stack = new NotifyingStackImp<Integer>(asList(41, 42));
        stack.getPopEvent().addListener(listener);
        stack.getPushEvent().addListener(listener);

        stack.clear();

        assertThat(stack.isEmpty(), is(true));
        verify(listener, never()).onEvent((EventObject<NotifyingStack<Integer>>) any());
    }
}
