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


import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.ArgumentNullException;

import java.util.AbstractList;
import java.util.List;

class SelectList<From, To> extends AbstractList<To>{
    private final List<? extends From> range;
    private final UnaryFunction<? super From, To> select;

    SelectList(List<? extends From> range, UnaryFunction<? super From, To> select) {
        if(range == null){
            throw new ArgumentNullException("range");
        }
        if(select == null){
            throw new ArgumentNullException("select");
        }
        this.range = range;
        this.select = select;
    }

    @Override
    public To get(int index) {
        return select.execute(range.get(index));
    }

    @Override
    public int size() {
        return range.size();
    }
}
