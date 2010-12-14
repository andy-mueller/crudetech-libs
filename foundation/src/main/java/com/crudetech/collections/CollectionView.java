////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
// Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.collections;

import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.Compare;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;


public class CollectionView<TView, TModel> extends AbstractCollection<TView> {
    private final UnaryFunction<TModel, TView> modelToView;
    private final UnaryFunction<TView, TModel> viewToModel;
    private final Collection<TModel> modelCollection;

    public CollectionView(Collection<TModel> modelCollection, UnaryFunction<TModel, TView> modelToView, UnaryFunction<TView, TModel> back) {
        this.modelCollection = modelCollection;
        this.modelToView = modelToView;
        this.viewToModel = back;
    }

    protected Collection<TModel> getModelCollection() {
        return modelCollection;
    }

    protected UnaryFunction<TView, TModel> getBackTransform() {
        return viewToModel;
    }

    protected UnaryFunction<TModel, TView> getToTransform() {
        return modelToView;
    }

    @Override
    public int size() {
        return getModelCollection().size();
    }

    @Override
    public boolean isEmpty() {
        return getModelCollection().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked") TView view =  (TView)o;//class cast exception is what collection spec tells us!!
        return getModelCollection().contains(getBackTransform().execute(view));
    }

    @Override
    public Iterator<TView> iterator() {
        return new Iterator<TView>() {
            private final Iterator<TModel> inner = getModelCollection().iterator();
            @Override
            public boolean hasNext() {
                return inner.hasNext();
            }

            @Override
            public TView next() {
                return getToTransform().execute(inner.next());
            }

            @Override
            public void remove() {
                inner.remove();
            }
        };
    }

    @Override
    public boolean add(TView item) {
        return getModelCollection().add(getBackTransform().execute(item));
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked") TView view =  (TView)o;//class cast exception is what collection spec tells us!!
                return getModelCollection().remove(getBackTransform().execute(view));
    }

    @Override
    public void clear() {
        getModelCollection().clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        @SuppressWarnings("unchecked")
        CollectionView<TView, TModel> that = (CollectionView<TView, TModel>) o;

        return Compare.equals(this, that);
    }

    @Override
    public int hashCode() {
       return Iterables.hashCode(this);
    }
}
