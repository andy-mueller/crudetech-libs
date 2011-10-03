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
package com.crudetech.query;


import com.crudetech.functional.BinaryFunction;
import com.crudetech.lang.VerifyArgument;

import java.util.AbstractList;
import java.util.List;

class SelectWithIndexList<From, To> extends AbstractList<To>{
    private final List<? extends From> range;
    private final BinaryFunction<? super From, Integer, To> select;

    SelectWithIndexList(List<? extends From> range, BinaryFunction<? super From, Integer, To> select) {
        VerifyArgument.isNotNull("range", range);
        VerifyArgument.isNotNull("select", select);
        this.range = range;
        this.select = select;
    }

    @Override
    public To get(int index) {
        return select.execute(range.get(index), index);
    }

    @Override
    public int size() {
        return range.size();
    }
}
