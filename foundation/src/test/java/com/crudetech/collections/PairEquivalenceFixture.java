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

import com.crudetech.unittests.EquivalentObjectFixture;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Collection;
import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class PairEquivalenceFixture extends EquivalentObjectFixture<Pair<Integer, String>> {
    public PairEquivalenceFixture(Pair<Integer, String> first, Pair<Integer, String> second, Pair<Integer, String> third, Pair<Integer, String> other) {
        super(first, second, third, other);
    }

    @Parameterized.Parameters
    public static Collection<Pair[]> parameters(){
        final Pair<Integer, String> first = new Pair<Integer, String>(2, "default");
        final Pair<Integer, String> second = new Pair<Integer, String>(2, "default");
        final Pair<Integer, String> third = new Pair<Integer, String>(2, "default");
        return asList(new Pair[][]{
                {first, second, third, new Pair<Integer, String>(2, "other") },
                {first, second, third, new Pair<Integer, String>(5, "default") },
                {first, second, third, new Pair<Integer, String>(5, "other") },
        });
    }
}
