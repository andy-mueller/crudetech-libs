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

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class ListView<TView, TModel> extends CollectionView<TView, TModel> implements List<TView> {
    public ListView(List<TModel> tModels, UnaryFunction<? super TModel, TView> modelToView, UnaryFunction<? super TView, TModel> viewToModel) {
        super(tModels, modelToView, viewToModel);
    }
    public ListView(List<? extends TModel> tModels, UnaryFunction<? super TModel, TView> modelToView) {
        super(tModels, modelToView);
    }

    @Override
    protected List<TModel> getModelCollection() {
        return (List<TModel>) super.getModelCollection();
    }

    @Override
    public boolean addAll(int index, Collection<? extends TView> c) {
        Collection<TModel> source = new CollectionView<>(c, getViewToModel());
        return getModelCollection().addAll(index, source);
    }

    @Override
    public TView get(int index) {
        return getModelToView().execute(getModelCollection().get(index));
    }

    @Override
    public TView set(int index, TView element) {
        return getModelToView().execute(
                getModelCollection().set(index, getViewToModel().execute(element))
        );
    }

    @Override
    public void add(int index, TView element) {
        getModelCollection().add(index, getViewToModel().execute(element));
    }

    @Override
    public TView remove(int index) {
        return getModelToView().execute(
                getModelCollection().remove(index)
        );
    }

    @Override
    public int indexOf(Object o) {
        return getModelCollection().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOf(o);
    }

    @Override
    public ListIterator<TView> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<TView> listIterator(final int index) {
        return new ListIterator<TView>() {
            private final ListIterator<TModel> inner = getModelCollection().listIterator(index);

            @Override
            public boolean hasNext() {
                return inner.hasNext();
            }

            @Override
            public TView next() {
                return getModelToView().execute(inner.next());
            }

            @Override
            public boolean hasPrevious() {
                return inner.hasPrevious();
            }

            @Override
            public TView previous() {
                return getModelToView().execute(inner.previous());
            }

            @Override
            public int nextIndex() {
                return inner.nextIndex();
            }

            @Override
            public int previousIndex() {
                return inner.previousIndex();
            }

            @Override
            public void remove() {
                inner.remove();
            }

            @Override
            public void set(TView tView) {
                inner.set(getViewToModel().execute(tView));
            }

            @Override
            public void add(TView tView) {
                inner.add(getViewToModel().execute(tView));
            }
        };
    }

    @Override
    public List<TView> subList(int fromIndex, int toIndex) {
        List<TModel> subList = getModelCollection().subList(fromIndex, toIndex);
        return new ListView<>(subList, getModelToView(), getViewToModel());
    }
}
