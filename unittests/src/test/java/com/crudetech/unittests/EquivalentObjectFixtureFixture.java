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
package com.crudetech.unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Collection;
import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class EquivalentObjectFixtureFixture extends EquivalentObjectFixture<Integer> {
    public EquivalentObjectFixtureFixture(Integer first, Integer second, Integer third, Integer other) {
        super(first, second, third, other);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        final Integer first = new Integer(42);
        final Integer second = new Integer(42);
        final Integer third = new Integer(42);

        return asList(new Object[][]{
                { first, second, third, 84 },
                { first, second, third, 56 },
        });
    }
}