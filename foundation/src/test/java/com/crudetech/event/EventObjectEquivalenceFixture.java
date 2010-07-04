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

import com.crudetech.collections.Pair;
import com.crudetech.unittests.EquivalentObjectFixture;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class EventObjectEquivalenceFixture extends EquivalentObjectFixture<EventObject<Integer>> {
    public EventObjectEquivalenceFixture(EventObject<Integer> first, EventObject<Integer> second, EventObject<Integer> third, EventObject<Integer> other) {
        super(first, second, third, other);
    }
    @Parameterized.Parameters
    public static Collection<Object[]> parameters(){
        final EventObject<Integer> first = new EventObject<Integer>(new Integer(2));
        final EventObject<Integer> second = new EventObject<Integer>(new Integer(2));
        final EventObject<Integer> third = new EventObject<Integer>(new Integer(2));

        return asList(new Object[][]{
                {first, second, third, new EventObject<Integer>(new Integer(4)) },
        });
    }
}
