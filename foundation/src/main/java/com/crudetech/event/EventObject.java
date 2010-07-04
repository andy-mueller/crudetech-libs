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
 * A type safe extension to {@link java.util.EventObject}.
 * Please refer to the {@link com.crudetech.event.EventSupport} documentation for details on
 * how to use the type safe event mechanism.
 */
public class EventObject<TEventSource> extends java.util.EventObject {

    public EventObject(final TEventSource source) {
        super(source);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventObject<TEventSource> other = (EventObject<TEventSource>) obj;
        return !(this.source != other.source && (this.source == null || !this.source.equals(other.source)));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash ^ (source != null ? source.hashCode() : 0);
    }

    @Override
    public TEventSource getSource() {
        return (TEventSource) super.getSource();
    }
}