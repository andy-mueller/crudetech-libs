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
package com.crudetech.collections;

import com.crudetech.lang.EqualityComparer;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class EqualityComparableTest {
    @Test
    public void equalsForwardsToComparer(){
        EqualityComparer<Integer> equComp = mock(EqualityComparer.class);
        EqualityComparable<Integer> c = new EqualityComparable<Integer>(equComp, new Integer(42));

        c.equals(new EqualityComparable<Integer>(equComp,new Integer(2)));

        verify(equComp).equals(new Integer(42), new Integer(2));
    }
    @Test
    public void hashCodeForwardsToComparer(){
        EqualityComparer<Integer> equComp = mock(EqualityComparer.class);
        EqualityComparable<Integer> c = new EqualityComparable<Integer>(equComp, new Integer(42));

        c.hashCode();

        verify(equComp).hashCode(new Integer(42));
    }
}
