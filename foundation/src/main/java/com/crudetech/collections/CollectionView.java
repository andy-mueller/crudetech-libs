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
import com.crudetech.lang.Compare;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * A collection implementation that offers a projective view on another collection.
 * All transformations are done on the fly, so no value is stored two times!
 * <p>
 * If you just need a forward transformation, i.e you just need to read from the
 * original model collection, use the overloaded
 * {@link com.crudetech.collections.CollectionView#CollectionView(java.util.Collection, com.crudetech.functional.UnaryFunction, com.crudetech.functional.UnaryFunction)}
 * constructor. The collection will throw a {@link UnsupportedOperationException}
 * from all modifying methods as specified in the collections framework api.
 *
 * @param <TView>
 * @param <TModel>
 */
public class CollectionView<TView, TModel> extends AbstractCollection<TView> {
    private final UnaryFunction<? super TModel, TView> modelToView;
    private final UnaryFunction<? super TView, TModel> viewToModel;
    private final Collection<TModel> modelCollection;

    private static <V, M> UnaryFunction<V, M> readOnlyViewToModel(){
        return new UnaryFunction<V, M>() {
            @Override
            public M execute(V tView) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public CollectionView(Collection<TModel> modelCollection, UnaryFunction<? super TModel, TView> modelToView, UnaryFunction<? super TView, TModel> viewToModel) {
        this.modelCollection = modelCollection;
        this.modelToView = modelToView;
        this.viewToModel = viewToModel;
    }
    public CollectionView(Collection<? extends TModel> modelCollection, UnaryFunction<? super TModel, TView> modelToView) {
        this(cast(modelCollection), modelToView, CollectionView.<TView, TModel>readOnlyViewToModel());
    }

    private static<T> Collection<T> cast(Collection<? extends T> modelCollection) {
        @SuppressWarnings("unchecked")
        Collection<T> tmp = (Collection<T>) modelCollection;
        return tmp;
    }

    protected Collection<TModel> getModelCollection() {
        return modelCollection;
    }

    protected UnaryFunction<? super TView, TModel> getViewToModel() {
        return viewToModel;
    }

    protected UnaryFunction<? super TModel, TView> getModelToView() {
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
        @SuppressWarnings("unchecked") TView view = (TView) o;//class cast exception is what collection spec tells us!!
        return getModelCollection().contains(getViewToModel().execute(view));
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
                return getModelToView().execute(inner.next());
            }

            @Override
            public void remove() {
                inner.remove();
            }
        };
    }

    @Override
    public boolean add(TView item) {
        TModel converted = getViewToModel().execute(item);
        return getModelCollection().add(converted);
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked") TView view = (TView) o;//class cast exception is what collection spec tells us!!
        return getModelCollection().remove(getViewToModel().execute(view));
    }

    @Override
    public void clear() {
        getModelCollection().clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Collection)) return false;

        @SuppressWarnings("unchecked")
        Iterable<?> that = (Iterable<?>) o;

        return Compare.equals(this, that);
    }

    @Override
    public int hashCode() {
        return Iterables.hashCode(this);
    }
}
