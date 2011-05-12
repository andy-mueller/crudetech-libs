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

import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.EqualityComparer;

import java.util.*;

/**
 * A {@link java.util.Map} adapter implementation that uses a passed in {@link com.crudetech.lang.EqualityComparer}
 * instead of the build in {@link Object#hashCode()} and {@link Object#equals(Object)} methods. It does so by
 * internally wrapping the key in a lightweight {@link com.crudetech.collections.EqualityComparable} object.
 * <p/>
 * It is up to the user of the class to provide the concrete Map implementation to be used.
 * <pre>{@code
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
 * }</pre>
 * <p/>
 * For standard map implementations, static convenient factory methods are provided.
 *
 * @param <TKey>   The type of keys maintained by this map
 * @param <TValue> The type of mapped values
 */
public class EqualityComparableMap<TKey, TValue> implements Map<TKey, TValue> {
    private final Map<EqualityComparable<TKey>, TValue> wrapped;
    private final EqualityComparer<TKey> equComp;

    public EqualityComparableMap(EqualityComparer<TKey> equComp, Map<EqualityComparable<TKey>, TValue> wrapped) {
        this.equComp = equComp;
        this.wrapped = wrapped;
    }

    public static <K, V> Map<K, V> newHashMap(EqualityComparer<K> equComp) {
        return new EqualityComparableMap<K, V>(equComp, new HashMap<EqualityComparable<K>, V>());
    }

    public static <K, V> Map<K, V> newLinkedHashMap(EqualityComparer<K> equComp, final UnaryFunction<Entry<K, V>, Boolean> removeOldestEntry) {
        return new EqualityComparableMap<K, V>(equComp, new LinkedHashMap<EqualityComparable<K>, V>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<EqualityComparable<K>, V> eldest) {
                return removeOldestEntry.execute(new AbstractMap.SimpleEntry<K, V>(eldest.getKey().getWrapped(), eldest.getValue()));
            }
        }
        );
    }

    public static <K, V> Map<K, V> newLinkedHashMap(EqualityComparer<K> equComp) {
        return new EqualityComparableMap<K, V>(equComp, new LinkedHashMap<EqualityComparable<K>, V>());
    }

    public static <K, V> Map<K, V> newTreeMap(EqualityComparer<K> equComp) {
        return new EqualityComparableMap<K, V>(equComp, new TreeMap<EqualityComparable<K>, V>());
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
        return wrapped.containsKey(wrapKey(keyCast(o)));
    }

    @Override
    public boolean containsValue(Object o) {
        return wrapped.containsValue(o);
    }


    @Override
    public TValue get(Object o) {
        EqualityComparable<TKey> key = wrapKey(keyCast(o));
        return wrapped.get(key);
    }

    private TKey keyCast(Object o) {
        @SuppressWarnings("unchecked") TKey tmp = (TKey) o;
        return tmp;
    }

    private EqualityComparable<TKey> wrapKey(TKey key) {
        return new EqualityComparable<TKey>(equComp, key);
    }

    @Override
    public TValue put(TKey k, TValue v) {
        return wrapped.put(wrapKey(k), v);
    }

    @Override
    public TValue remove(Object o) {
        return wrapped.remove(wrapKey(keyCast(o)));
    }

    @Override
    public void putAll(Map<? extends TKey, ? extends TValue> map) {
        for (Entry<? extends TKey, ? extends TValue> e : map.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        wrapped.clear();
    }

    @Override
    public Set<TKey> keySet() {
        UnaryFunction<EqualityComparable<TKey>, TKey> unwrapKey = new UnaryFunction<EqualityComparable<TKey>, TKey>() {
            @Override
            public TKey execute(EqualityComparable<TKey> entry) {
                return entry.getWrapped();
            }
        };
        UnaryFunction<TKey, EqualityComparable<TKey>> wrapKey = new UnaryFunction<TKey, EqualityComparable<TKey>>() {
            @Override
            public EqualityComparable<TKey> execute(TKey key) {
                return wrapKey(key);
            }
        };
        return new SetView<TKey, EqualityComparable<TKey>>(wrapped.keySet(), unwrapKey, wrapKey);
    }

    @Override
    public Collection<TValue> values() {
        return wrapped.values();
    }

    @Override
    public Set<Entry<TKey, TValue>> entrySet() {

        UnaryFunction<Entry<EqualityComparable<TKey>, TValue>, Entry<TKey, TValue>> wrapKey = new UnaryFunction<Entry<EqualityComparable<TKey>, TValue>, Entry<TKey, TValue>>() {
            @Override
            public Entry<TKey, TValue> execute(Entry<EqualityComparable<TKey>, TValue> entry) {
                return new AbstractMap.SimpleEntry<TKey, TValue>(entry.getKey().getWrapped(), entry.getValue());
            }
        };

        UnaryFunction<Entry<TKey, TValue>, Entry<EqualityComparable<TKey>, TValue>> unwrapKey = new UnaryFunction<Entry<TKey, TValue>, Entry<EqualityComparable<TKey>, TValue>>() {
            @Override
            public Entry<EqualityComparable<TKey>, TValue> execute(Entry<TKey, TValue> entry) {
                return new AbstractMap.SimpleEntry<EqualityComparable<TKey>, TValue>(wrapKey(entry.getKey()), entry.getValue());
            }
        };

        return new SetView<Entry<TKey, TValue>, Entry<EqualityComparable<TKey>, TValue>>(wrapped.entrySet(), wrapKey, unwrapKey);
    }

    @Override
    public String toString() {
        return "EqualityComparableMap{" +
                "wrapped=" + wrapped +
                ", equComp=" + equComp +
                '}';
    }
}
