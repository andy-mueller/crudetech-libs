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

import com.crudetech.event.Event;
import com.crudetech.event.EventObject;

interface NotifyingStack<T> {
    Event<EventObject<NotifyingStack<T>>> getPushEvent();

    Event<EventObject<NotifyingStack<T>>> getPopEvent();
}
