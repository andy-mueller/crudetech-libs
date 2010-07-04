package com.crudetech.unittests;

import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A simple base fixture for serialization support. It will serialize and
 * deserialize and object ang check for equality.
 */
public abstract class SerializableFixture<T extends Serializable> {
    @Test
    public void serialize() throws Exception{
        T o = createObject();

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteStream);
        try{
            out.writeObject(o);
        }finally{
            out.close();
        }

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteStream.toByteArray()));
        T o2 = (T) in.readObject();

        assertThat(o, is(equalTo(o2)));
        assertThat(o, is(not(sameInstance(o2))));
    }

    protected abstract T createObject();
}
