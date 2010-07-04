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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Simple type safe helper class to implement events.
 * <pre>
 * class Window{
 *   static class ClosedEventObject extends EventObject<Window>{
 *     ClosedEventObject(Window source){ super(source); }
 *   }
 *   private final EventSupport<ClosedEventObject> closedEvent =
 *     new EventSupport<ClosedEventObject>();
 *
 *   Event<ClosedEventObject> closedEvent(){
 *     return closedEvent;
 *   }
 *   void close(){
 *     closedEvent.fireEvent(e);
 *   }
 * }
 *
 * class App{
 *   public static void main(String[] args){
 *     Window w = new Window();
 *     w.closedEvent().addHandler(
 *       new EventListener<Window.ClosedEventObject>(){
 *         void onEvent(final Window.ClosedEventObject e){
 *           System.out.println("Closed event was raised!");
 *         }
 *       }
 *     );
 *
 *     w.close();
 *   }
 * }
 * </pre>
 */
public class EventSupport<TEventObject extends EventObject<?>> implements Event<TEventObject> {

    private final List<EventListener<TEventObject>> listeners = new ArrayList<EventListener<TEventObject>>();

    public void addListener(EventListener<TEventObject> listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void fireEvent(TEventObject eventObject) {
        for (EventListener<TEventObject> aList : getListeners()) {
            aList.onEvent(eventObject);
        }
    }

    public Iterable<EventListener<TEventObject>> getListeners() {
        synchronized (listeners) {
            final Collection<EventListener<TEventObject>> tmp = new ArrayList<EventListener<TEventObject>>(listeners.size());
            tmp.addAll(listeners);
            return tmp;
        }
    }

    public void clearListeners() {
        synchronized (listeners) {
            listeners.clear();
        }
    }

    public boolean contains(EventListener<TEventObject> listener) {
        synchronized (listeners) {
            return listeners.contains(listener);
        }
    }

    public void removeListener(EventListener<TEventObject> listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }
}
