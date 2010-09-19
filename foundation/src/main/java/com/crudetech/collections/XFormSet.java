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

import com.crudetech.functional.UnaryFunction;

import java.util.Set;

public class XFormSet<To, From> extends XFormCollection<To, From> implements Set<To> {
    public XFormSet(Set<From> from, UnaryFunction<From, To> xform, UnaryFunction<To, From> back) {
        super(from, xform, back);
    }
    @Override
    protected Set<From> getFromCollection() {
        return (Set<From>) super.getFromCollection();
    }
}
