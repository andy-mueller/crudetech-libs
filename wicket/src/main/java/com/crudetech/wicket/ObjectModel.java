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

/**
 * Adaptor model that simplifies and enables the use of domain objects in
 * wicket models.
 * <p/>
 * A  lot of predefined wicket components use primitive types and toString to display them.
 * This often leads to the effect that the programming style relies more on these primitives and
 * strings as on domain objects. This model helps you to convert any complex object into another
 * representation. When you have more complicated domain objects, displaying
 * them can be achieved using {@link org.apache.wicket.model.PropertyModel}s or
 * their type safe brothers.
 *
 * @param <TObject>  The domain object type.
 * @param <TDisplay> The target type and the type of this model
 */
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

    @SuppressWarnings("unchecked")
    private IModel<TObject> valueAsModel() {
        return ((IModel<TObject>) value);
    }

    @SuppressWarnings("unchecked")
    private TObject valueAsObject() {
        return (TObject) value;
    }

    private boolean valueIsModel() {
        return value instanceof Model<?>;
    }

    @Override
    public void setObject(TDisplay value) {
        TObject newValue = converter.convertFrom(value);
        if (valueIsModel()) {
            valueAsModel().setObject(newValue);
        } else {
            this.value = newValue;
        }
    }

    @Override
    public void detach() {
        if (value instanceof IDetachable) {
            ((IDetachable) value).detach();
        }
    }
}
