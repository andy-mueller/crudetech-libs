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

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Simple type safe helper class to implement events. The class is thread safe.
 * <p>
 * <pre>
 * class Window{
 *   static class ClosedEventObject extends EventObject&lt;Window&gt;{
 *     ClosedEventObject(Window source){ super(source); }
 *   }
 *   private final EventSupport&lt;ClosedEventObject&gt; closedEvent =
 *     new EventSupport&lt;ClosedEventObject&gt;();
 *
 *   Event&lt;ClosedEventObject&gt; closedEvent(){
 *     return closedEvent;
 *   }
 *   void close(){
 *     closedEvent.fireEvent(new ClosedEventObject(this) );
 *   }
 * }
 *
 * class App{
 *   public static void main(String[] args){
 *     Window w = new Window();
 *     w.closedEvent().addHandler(
 *       new EventListener&lt;Window.ClosedEventObject&gt;(){
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

    private final CopyOnWriteArrayList<EventListener<TEventObject>> listeners = new CopyOnWriteArrayList<EventListener<TEventObject>>();

    public void addListener(EventListener<TEventObject> listener) {
        listeners.add(listener);
    }
    public void fireEvent(TEventObject eventObject) {
        for (EventListener<TEventObject> listener : listeners) {
            listener.onEvent(eventObject);
        }
    }

    public Iterable<EventListener<TEventObject>> getListeners() {
        return new ArrayList<EventListener<TEventObject>>(listeners);
    }

    public void clearListeners() {
        listeners.clear();
    }

    public boolean contains(EventListener<TEventObject> listener) {
        return listeners.contains(listener);
    }

    public void removeListener(EventListener<TEventObject> listener) {
        listeners.remove(listener);
    }
}
