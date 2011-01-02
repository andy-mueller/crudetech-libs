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
 * A strong typed version of the {@link java.util.EventListener} interface. It features a generic
 * finished method.
 * Please refer to the {@link EventSupport} documentation for details on
 * how to use the type safe event mechanism.
 */
public interface EventListener<TEventObject extends EventObject<?>> extends java.util.EventListener {
    void onEvent(final TEventObject e);
}
