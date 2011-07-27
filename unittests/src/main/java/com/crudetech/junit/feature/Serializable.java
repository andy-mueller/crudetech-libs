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
package com.crudetech.junit.feature;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A feature fixture for objects that are serializable.
 *
 * @param <T>
 */
public class Serializable<T extends java.io.Serializable> implements FeatureFixture {

    public static interface Factory<T> {
        T createObject();
    }

    public Serializable(Factory<T> factory) {
        this.factory = factory;
    }

    private final Factory<T> factory;

    @Test
    public void serialize() throws Exception {
        T o = factory.createObject();

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(byteStream)) {
            out.writeObject(o);
        }

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteStream.toByteArray()));
        @SuppressWarnings("unchecked")
        T o2 = (T) in.readObject();

        assertThat(o, is(equalTo(o2)));
        assertThat(o, is(not(sameInstance(o2))));
    }
}
