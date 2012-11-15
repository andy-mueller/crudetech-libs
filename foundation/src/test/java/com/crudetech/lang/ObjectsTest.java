package com.crudetech.lang;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;

public class ObjectsTest {
    @Test
    public void equalsCallsEqualsOnFirst() throws Exception {
        class Noob {
            @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
            @Override
            public boolean equals(Object unused) {
                return true;
            }
        }
        Object lhs = new Noob();
        Object rhs = 9999;

        assertThat(Objects.equals(lhs, rhs), is(true));
        assertThat(Objects.equals(rhs, lhs), is(false));
    }

    @Test
    public void equalsHandlesNullOnFirstArgument() throws Exception {
        Object lhs = null;
        Object rhs = 9999;

        assertThat(Objects.equals(lhs, rhs), is(false));
    }

    @Test
    public void equalsHandlesNullOnSecondArgument() throws Exception {
        Object lhs = 9999;
        Object rhs = null;

        assertThat(Objects.equals(lhs, rhs), is(false));
    }

    @Test
    public void equalsHandlesNullOnBothArguments() throws Exception {
        Object lhs = null;
        Object rhs = null;

        assertThat(Objects.equals(lhs, rhs), is(true));
    }

    @Test
    public void givenObjectsItsHashCodeIsUsed() throws Exception {
        int hc = Objects.hashCode(42);

        assertThat(hc, is(Integer.valueOf(42).hashCode()));
    }

    @Test
    public void givenNullHashCodeZero() throws Exception {
        int hc = Objects.hashCode(null);

        assertThat(hc, is(0));
    }

    @Test
    public void givenValuesHashIsConcated() throws Exception {
        int hash = Objects.hash(4, 6);

        assertThat(hash, is(Arrays.hashCode(new Object[]{4, 6})));
    }

    @SuppressWarnings("NullArgumentToVariableArgMethod")
    @Test
    public void givenValuesHashCanHandleNull() throws Exception {
        int hash = Objects.hash((Object[]) null);

        assertThat(hash, is(Arrays.hashCode((Object[]) null)));
    }

    @Test
    public void givenValuesHashCanHandleEmptyArray() throws Exception {
        int hash = Objects.hash();

        assertThat(hash, is(1));
    }


    @Test
    public void toStringUsesObjectImplementation() throws Exception {
        Object o = new Object() {
            @Override
            public String toString() {
                return "This is fun!";
            }
        };

        assertThat(Objects.toString(o), is("This is fun!"));
    }

    @Test
    public void toStringHandlesNull() throws Exception {
        assertThat(Objects.toString(null), is(String.valueOf((Object) null)));
    }

    @Test
    public void givenNonNullReference_requireNonNullReturnsReference() throws Exception {
        Object value = 42;
        assertThat(Objects.requireNonNull(value), is(sameInstance(value)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenNullReference_requireNonNullThrows() throws Exception {
        Objects.requireNonNull(null);
    }
}
