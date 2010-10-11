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
package com.crudetech.graphics2d.xwt;

import com.crudetech.collections.LightweightStack;
import com.crudetech.collections.ListStack;
import com.crudetech.event.Event;
import com.crudetech.event.EventObject;
import com.crudetech.event.EventSupport;
import com.crudetech.lang.ArgumentNullException;

import java.util.Iterator;


class NotifyingStackImp<T> implements NotifyingStack<T>, LightweightStack<T> {
    private final LightweightStack<T> theStack;
    private final EventSupport<EventObject<NotifyingStack<T>>> pushEvent = new EventSupport<EventObject<NotifyingStack<T>>>();
    private final EventSupport<EventObject<NotifyingStack<T>>> popEvent = new EventSupport<EventObject<NotifyingStack<T>>>();

    NotifyingStackImp(LightweightStack<T> theStack) {
        this.theStack = theStack;
    }

    NotifyingStackImp(Iterable<T> content) {
        this(new ListStack<T>(content));
    }

    @Override
    public T peek() {
        return theStack.peek();
    }

    @Override
    public void pop() {
        theStack.pop();
        popEvent.fireEvent(new EventObject<NotifyingStack<T>>(this));
    }

    @Override
    public void push(T item) {
        theStack.push(item);
        pushEvent.fireEvent(new EventObject<NotifyingStack<T>>(this));
    }

    @Override
    public boolean isEmpty() {
        return theStack.isEmpty();
    }

    @Override
    public Event<EventObject<NotifyingStack<T>>> getPushEvent() {
        return pushEvent;
    }

    @Override
    public Event<EventObject<NotifyingStack<T>>> getPopEvent() {
        return popEvent;
    }
    @Override
    public T[] toArray(Class<T> clazz) {
        return theStack.toArray(clazz);        
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final Iterator<T> it = theStack.iterator();
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public T next() {
                return it.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    void clearWithoutEvents() {
        while(!isEmpty()){
            theStack.pop();
        }
    }

    public void addWithoutEvents(T... items) {
        if(items == null) throw new ArgumentNullException("items");
        for(T item : items){
            theStack.push(item);
        }
    }
}
