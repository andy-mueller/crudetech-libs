////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
// Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.collections;

import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.EqualityComparer;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A {@link java.util.Map} adapter implementation that uses a passed in {@link com.crudetech.lang.EqualityComparer}
 * instead of the keys build in {@link Object#hashCode()} and {@link Object#equals(Object)} methods. It does so by
 * internally wrapping the key in a lightweight {@link com.crudetech.collections.EqualityComparable} object.
 * <p>
 * It is up to the user of the class to provide the concrete Map implementation to be used.
 * <pre>
 * EqualityComparer<Integer> unsignedComparer = new EqualityComparer<Integer>() {
 *     @Override
 *     public boolean equals(Integer lhs, Integer rhs) {
 *         return Math.abs(lhs) == Math.abs(rhs);
 *     }
 *     @Override
 *     public int hashCode(Integer item) {
 *         return Math.abs(item);
 *     }
 * };
 * Map<Integer, String> equMap =
 *         new EqualityComparableMap<Integer, String>(unsignedComparer, new HashMap<EqualityComparable<Integer>, String>());
 * </pre>
 * @param <K> The type of keys maintained by this map
 * @param <V> The type of mapped values
 */
public class EqualityComparableMap<K, V> implements Map<K, V> {
    private final Map<EqualityComparable<K>, V> wrapped;
    private final EqualityComparer<K> equComp;

    public EqualityComparableMap(EqualityComparer<K> equComp, Map<EqualityComparable<K>, V> wrapped) {
        this.equComp = equComp;
        this.wrapped = wrapped;
    }

    @Override
    public int size() {
        return wrapped.size();
    }

    @Override
    public boolean isEmpty() {
        return wrapped.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return wrapped.containsKey(new EqualityComparable<K>(equComp, keyCast(o)));
    }

    @Override
    public boolean containsValue(Object o) {
        return wrapped.containsValue(o);
    }
    private K keyCast(Object o){
        @SuppressWarnings("unchecked") K tmp = (K)o;
        return tmp;
    }
    @Override
    public V get(Object o) {
        EqualityComparable<K> key = new EqualityComparable<K>(equComp, keyCast(o));
        return wrapped.get(key);
    }

    @Override
    public V put(K k, V v) {
        return wrapped.put(new EqualityComparable<K>(equComp, k), v);
    }

    @Override
    public V remove(Object o) {
        return wrapped.remove(new EqualityComparable<K>(equComp, keyCast(o)));
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> e : map.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        wrapped.clear();
    }

    @Override
    public Set<K> keySet() {
        UnaryFunction<EqualityComparable<K>, K> unwrapKey = new UnaryFunction<EqualityComparable<K>, K>() {
            @Override
            public K execute(EqualityComparable<K> entry) {
                return entry.getWrapped();
            }
        };
            UnaryFunction<K, EqualityComparable<K>> wrapKey = new UnaryFunction<K, EqualityComparable<K>>() {
            @Override
            public EqualityComparable<K> execute(K key) {
                return new EqualityComparable<K>(equComp, key);
            }
        };
        return new SetView<K, EqualityComparable<K>>(wrapped.keySet(), unwrapKey, wrapKey);
    }

    @Override
    public Collection<V> values() {
        return wrapped.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {

        UnaryFunction<Entry<EqualityComparable<K>, V>, Entry<K, V>> wrapKey = new UnaryFunction<Entry<EqualityComparable<K>, V>, Entry<K, V>>() {
            @Override
            public Entry<K, V> execute(Entry<EqualityComparable<K>, V> entry) {
                return new AbstractMap.SimpleEntry<K, V>(entry.getKey().getWrapped(), entry.getValue());
            }
        };

        UnaryFunction<Entry<K, V>, Entry<EqualityComparable<K>, V>> unwrapKey = new UnaryFunction<Entry<K, V>, Entry<EqualityComparable<K>, V>>() {
            @Override
            public Entry<EqualityComparable<K>, V> execute(Entry<K, V> entry) {
                return new AbstractMap.SimpleEntry<EqualityComparable<K>, V>(new EqualityComparable<K>(equComp, entry.getKey()), entry.getValue());
            }
        };

        return new SetView<Entry<K, V>, Entry<EqualityComparable<K>, V>>(wrapped.entrySet(), wrapKey, unwrapKey);
    }
}
