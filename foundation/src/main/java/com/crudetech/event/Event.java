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
 * A type safe interface that represents an event. You can add and remove
 * listeners an will be notified when the event occurs.
 * <p>
 * You will use this interface through the {@EventSupport} class.
 * Please refer to the {@link EventSupport} documentation for details on
 * how to use the type safe event mechanism.
 */
public interface Event<TEventObject extends EventObject<?>> {

    void addListener(EventListener<TEventObject> listener);

    void removeListener(EventListener<TEventObject> listener);
}