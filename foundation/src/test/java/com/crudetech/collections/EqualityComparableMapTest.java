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

import com.crudetech.lang.EqualityComparer;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;


public class EqualityComparableMapTest {
    private static EqualityComparer<String> LowerCaseStringComparer = new EqualityComparer<String>() {
        @Override
        public boolean equals(String lhs, String rhs) {
            return lhs.toLowerCase().equals(rhs.toLowerCase());
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
    public void newMapIsEmpty() {
         Map<String, String> m = EqualityComparableMap.newHashMap(LowerCaseStringComparer);
        assertThat(m.isEmpty(), is(true));
    }
    @Test
    public void addingItemsEnlargesMap(){
        Map<String, String> m = EqualityComparableMap.newHashMap(LowerCaseStringComparer);

        assertThat(m.size(), is(0));
        m.put("1", "one");
        assertThat(m.size(), is(1));
    }
    @Test
    public void addingItemsPutsItemIntoTheMap(){
        Map<String, String> m = EqualityComparableMap.newHashMap(LowerCaseStringComparer);

        assertThat(m.containsKey("1"), is(false));
        m.put("1", "one");
        assertThat(m.containsKey("1"), is(true));
    }
    @Test
    public void removingItemsDeletesItemFromTheMap(){
        Map<String, String> m = EqualityComparableMap.newHashMap(LowerCaseStringComparer);

        m.put("1", "one");
        assertThat(m.containsKey("1"), is(true));
        m.remove("1");
        assertThat(m.containsKey("1"), is(false));
    }

    @Test
    public void mapUsesEqualityComparer(){
        Map<String, String> m = EqualityComparableMap.newHashMap(LowerCaseStringComparer);

        assertThat(m.put("a", "one"), is(nullValue()));
        assertThat(m.put("A", "two"), is("one"));
        assertThat(m.containsKey("a"), is(true));
        assertThat(m.containsKey("A"), is(true));
        assertThat(m.get("A"), is("two"));
        assertThat(m.get("a"), is("two"));

        assertThat(m.size(), is(1));
    }
}
