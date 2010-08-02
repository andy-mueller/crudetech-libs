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

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;

public class EventObjectFixture  {
    @Test
    public void ctor() {
        EventObject<EventObjectFixture> event = new EventObject<EventObjectFixture>(this);

        assertThat(event.getSource(), is(sameInstance(this)));
    }
    @Test
    public void isDerivable(){
        EventObject<EventObjectFixture> derived = new EventObject<EventObjectFixture>(this){};
        assertThat(derived, is(instanceOf(EventObject.class)));
    }
    @Test
    public void equalsWithDerivedFails(){
        EventObject<EventObjectFixture> lhs = new EventObject<EventObjectFixture>(this);
        EventObject<EventObjectFixture> rhs = new EventObject<EventObjectFixture>(this){};

        assertThat(lhs, is(not(equalTo(rhs))));
    }

}