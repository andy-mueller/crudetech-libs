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

import com.crudetech.lang.Compare;
import functional.UnaryFunction;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;


public class XFormCollection<To, From> extends AbstractCollection<To> {
    private final UnaryFunction<To, From> xform;
    private final UnaryFunction<From, To> back;
    private final Collection<From> fromCollection;

    public XFormCollection(Collection<From> fromCollection, UnaryFunction<To, From> xform, UnaryFunction<From, To> back) {
        this.fromCollection = fromCollection;
        this.xform = xform;
        this.back = back;
    }

    protected Collection<From> getFromCollection() {
        return fromCollection;
    }

    protected UnaryFunction<From, To> getBackTransform() {
        return back;
    }

    protected UnaryFunction<To, From> getToTransform() {
        return xform;
    }

    @Override
    public int size() {
        return getFromCollection().size();
    }

    @Override
    public boolean isEmpty() {
        return getFromCollection().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getFromCollection().contains(getBackTransform().execute((To)o));
    }

    @Override
    public Iterator<To> iterator() {
        return new Iterator<To>() {
            private final Iterator<From> inner = getFromCollection().iterator();
            @Override
            public boolean hasNext() {
                return inner.hasNext();
            }

            @Override
            public To next() {
                return getToTransform().execute(inner.next());
            }

            @Override
            public void remove() {
                inner.remove();
            }
        };
    }

    @Override
    public boolean add(To to) {
        return getFromCollection().add(getBackTransform().execute(to));
    }

    @Override
    public boolean remove(Object o) {
        return getFromCollection().remove(getBackTransform().execute((To) o));
    }

    @Override
    public void clear() {
        getFromCollection().clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XFormCollection<To, From> that = (XFormCollection<To, From>) o;

        return Compare.equals(this, that);
    }

    @Override
    public int hashCode() {
       return Iterables.hashCode(this);
    }
}
