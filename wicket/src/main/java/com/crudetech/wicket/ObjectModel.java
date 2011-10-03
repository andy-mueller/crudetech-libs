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
package com.crudetech.wicket;

import com.crudetech.lang.VerifyArgument;
import org.apache.wicket.model.IChainingModel;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class ObjectModel<TObject, TDisplay> implements IChainingModel<TDisplay> {

    public static interface Converter<TSource, TTarget> {
        TTarget convertTo(TSource source);

        TSource convertFrom(TTarget source);
    }

    private Object value;
    private Converter<TObject, TDisplay> converter;

    public ObjectModel(Converter<TObject, TDisplay> converter) {
        this.converter = converter;
    }

    public ObjectModel(Converter<TObject, TDisplay> converter, TObject value) {
        this.converter = converter;
        this.value = value;
    }

    public ObjectModel(Converter<TObject, TDisplay> converter, IModel<? extends TObject> model) {
        VerifyArgument.isNotNull("model", model);
        this.converter = converter;
        this.value = model;
    }


    @Override
    public void setChainedModel(IModel<?> model) {
        this.value = model;
    }

    @Override
    public IModel<TObject> getChainedModel() {
        if (!(value instanceof IModel<?>)) {
            throw new IllegalStateException("There is no chained model");
        }

        return valueAsModel();
    }

    @Override
    public TDisplay getObject() {
        TObject rv = value == null ? null : valueIsModel() ? valueAsModel().getObject() : valueAsObject();
        return rv != null ? converter.convertTo(rv) : null;
    }

    private IModel<TObject> valueAsModel() {
        @SuppressWarnings("unchecked")
        IModel<TObject> model = ((IModel<TObject>) value);
        return model;
    }
    private TObject valueAsObject() {
        @SuppressWarnings("unchecked")
        TObject tmp = (TObject) value;
        return tmp;
    }

    private boolean valueIsModel() {
        return value instanceof Model<?>;
    }

    @Override
    public void setObject(TDisplay value) {
        TObject newValue = converter.convertFrom(value);
        if(valueIsModel()){
            valueAsModel().setObject(newValue);
        }      else{
            this.value = newValue;
        }
    }

    @Override
    public void detach() {
        if(value instanceof IDetachable){
            ((IDetachable)value).detach();
        }
    }
}
