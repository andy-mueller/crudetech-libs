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

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Before;
import org.junit.Test;

import static com.crudetech.matcher.ThrowsException.doesThrow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ObjectModelTest {

    private ObjectModel<Integer,String> valueModel;
    private ObjectModel.Converter<Integer, String> int2string = new ObjectModel.Converter<Integer, String>() {
        @Override
        public String convertTo(Integer integer) {
            return integer.toString();
        }

        @Override
        public Integer convertFrom(String source) {
            return source != null ?  Integer.valueOf(source) : null;
        }
    };
    private IModel<Integer> innerModel;
    private ObjectModel<Integer,String> chainedModel;

    @Before
    public void before() throws Exception {
        valueModel = new ObjectModel<Integer,String>(int2string, 42);
        innerModel = Model.of(42);
        chainedModel = new ObjectModel<Integer,String>(int2string, innerModel);
    }

    @Test
    public void defaultCtorCreatesEmptyModel() {
        ObjectModel<Integer, String> model = new ObjectModel<Integer,String>(int2string);
        assertThat(model.getObject(), is(nullValue()));
    }
    @Test
    public void ctorWithNullValueStoresNull() {
        ObjectModel<Integer, String> model = new ObjectModel<Integer,String>(int2string, (Integer)null);
        assertThat(model.getObject(), is(nullValue()));
    }
    @Test
    public void ctorWithNullModelThrows() {
        Runnable ctorWithNullModel = new Runnable() {
            @Override
            public void run() {
                new ObjectModel<Integer,String>(int2string, (IModel<Integer>)null);
            }
        };

        assertThat(ctorWithNullModel, doesThrow(IllegalArgumentException.class));
    }

    @Test
    public void ctorWithValueCreatesModelWithValue() {
        assertThat(valueModel.getObject(), is("42"));
    }

    @Test
    public void ctorWithModelCreatesChainedModel() {
        assertThat(chainedModel.getChainedModel(), is(sameInstance(innerModel)));
    }
    @Test
    public void getChainedModelThrowsWhenThereIsNoInnerModel() {

        Runnable getChainedModel = new Runnable() {
            @Override
            public void run() {
                valueModel.getChainedModel();
            }
        };
        assertThat(getChainedModel, doesThrow(IllegalStateException.class));
    }

    @Test
    public void chainedModelReturnsValueOfInnerModel() {
        assertThat(chainedModel.getObject(), is("42"));
    }
    @Test
    public void getObjectThrowsWhenInnerModelThrows() {
        class BoomException extends RuntimeException{}
        IModel<Integer> inner = new Model<Integer>(42){
            @Override
            public Integer getObject() {
                throw new BoomException();
            }
        };
        final ObjectModel<Integer, String> model = new ObjectModel<Integer,String>(int2string, inner);

        Runnable getObject = new Runnable() {
            @Override
            public void run() {
                model.getObject();
            }
        };
        assertThat(getObject, doesThrow(BoomException.class));
    }

    @Test
    public void setValueSetsNewValue(){
        valueModel.setObject("84");

        assertThat(valueModel.getObject(), is("84"));
    }
    @Test
    public void setObjectsDelegatesToInnerModel(){
        chainedModel.setObject("84");

        assertThat(innerModel.getObject(), is(84));
    }

    @Test
    public void setChainedModelOverwritesPreviousValue(){
        valueModel.setObject("84");
        valueModel.setChainedModel(Model.of(42));

        assertThat(valueModel.getObject(), is("42"));
    }
    @Test
    public void setChainedModelStoresModel(){
        IModel<Integer> newModel = Model.of(55);
        valueModel.setChainedModel(newModel);

        assertThat(valueModel.getChainedModel(), is(sameInstance(newModel)));
    }

    @Test
    public void setObjectAllowsNull(){
        valueModel.setObject(null);

        assertThat(valueModel.getObject(), is(nullValue()));
    }
    @Test
    public void setObjectAllowsNullWithChained(){
        chainedModel.setObject(null);

        assertThat(chainedModel.getObject(), is(nullValue()));
    }

    @Test
    public void detachDelegatesToInnerModel(){
        Model<Integer> inner = spy(new Model<Integer>(42));
        ObjectModel<Integer, String> model = new ObjectModel<Integer,String>(int2string, inner);

        model.detach();

        verify(inner, times(1)).detach();
    }
    @Test
    public void detachDelegatesToValueIfItIsDetachable(){
        class DetachableValue implements IDetachable{
            private boolean detached = true;
            @Override public void detach() {
                detached = false;
            }
        }
        class DetachableValueConverter implements ObjectModel.Converter<DetachableValue, String>{
            @Override
            public String convertTo(DetachableValue detachableValue) {
                throw new UnsupportedOperationException("Implement me!");
            }

            @Override
            public DetachableValue convertFrom(String source) {
                throw new UnsupportedOperationException("Implement me!");
            }
        }
        DetachableValue value = new DetachableValue();
        ObjectModel<DetachableValue, String> model = new ObjectModel<DetachableValue, String>(new DetachableValueConverter(), value);


        model.detach();

        assertThat(value.detached, is(false));
    }
}
