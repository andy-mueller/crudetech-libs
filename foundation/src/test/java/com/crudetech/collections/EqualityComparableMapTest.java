package com.crudetech.collections;

import com.crudetech.lang.EqualityComparer;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;



public class EqualityComparableMapTest {
    private static EqualityComparer<String> LowerCaseStringComparer = new EqualityComparer<String>() {
        @Override
        public boolean equals(String lhs, String rhs) {
            return lhs.toLowerCase().equals(rhs.toUpperCase());
        }

        @Override
        public int hashCode(String item) {
            return item.toLowerCase().hashCode();
        }
    };
    @Test
    public void sizeIs0OnEmptyMap(){
        Map<String, String> m = new EqualityComparableMap<String, String>(LowerCaseStringComparer, new HashMap<EqualityComparable<String>, String>());
        assertThat(m.size(), is(0));
    }

    @Test
    public void testIsEmpty() throws Exception {
         Map<String, String> m = new EqualityComparableMap<String, String>(LowerCaseStringComparer, new HashMap<EqualityComparable<String>, String>());
        assertThat(m.isEmpty(), is(true));
    }
}
