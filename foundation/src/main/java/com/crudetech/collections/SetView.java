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
package com.crudetech.collections;

import com.crudetech.functional.UnaryFunction;

import java.util.Set;

public class SetView<TView, TModel> extends CollectionView<TView, TModel> implements Set<TView> {
    public SetView(Set<TModel> modelSet, UnaryFunction<? super TModel, TView> modelToView, UnaryFunction<? super TView, TModel> viewToModel) {
        super(modelSet, modelToView, viewToModel);
    }
    public SetView(Set<? extends TModel> modelSet, UnaryFunction<? super TModel, TView> modelToView) {
        super(modelSet, modelToView);
    }

    @Override
    protected Set<TModel> getModelCollection() {
        return (Set<TModel>) super.getModelCollection();
    }
}
