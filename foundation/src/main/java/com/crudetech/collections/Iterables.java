////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//     Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.collections;

import com.crudetech.functional.BinaryFunction;
import com.crudetech.functional.UnaryFunction;
import com.crudetech.lang.ArgumentNullException;
import com.crudetech.lang.Compare;
import com.crudetech.lang.EqualityComparer;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;

/**
 * This class is very similar to {@link java.util.Arrays} except that it
 * works for {@link Iterable}s. In addition it also provides some very simple algorithms.
 * If you wish to do more complex operations consider the
 * com.crudetech.query, google collections or the apache commons packages. This
 * class is intended to serve as a very basic utility set.
 *
 * @author andreas.b.mueller@freenet.de
 */
public final class Iterables {
    private Iterables() {
    }

    public static <T> String toString(final Iterable<T> iterable) {
        if (iterable == null)
            return "null";

        StringBuilder b = new StringBuilder();
        b.append("[");

        for (T t : iterable) {
            b.append(t);
            b.append(", ");
        }

        b.delete(b.length() - 2, b.length());
        b.append("]");
        return b.toString();
    }

    public static <T> int hashCode(final Iterable<T> iterable) {
        return hashCode(iterable, (EqualityComparer<T>) EqualityComparer.Standard);
    }

    public static <T> int hashCode(final Iterable<T> iterable, EqualityComparer<T> comp) {
        if (iterable == null)
            return 0;

        int result = 1;

        for (T element : iterable)
            result = 31 * result + (element == null ? 0 : comp.hashCode(element));

        return result;
    }

    public static <T> boolean contains(Iterable<T> range, T value) {
        if (range == null) throw new ArgumentNullException("range");
        for (T v : range) {
            if (Compare.equals(v, value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Iterable<Double> range, double value) {
        if (range == null) throw new ArgumentNullException("range");
        for (Double v : range) {
            if (Compare.equals(v.doubleValue(), value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Iterable<Float> range, float value) {
        if (range == null) throw new ArgumentNullException("range");
        for (Float v : range) {
            if (Compare.equals(v.doubleValue(), value)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isEmpty(final Iterable<T> range) {
        if (range == null) throw new ArgumentNullException("range");
        return !range.iterator().hasNext();
    }

    public static <T> T lastOf(final Iterable<T> range) {
        if (range == null) throw new ArgumentNullException("range");
        T last = null;
        Iterator<T> i = range.iterator();

        if (!i.hasNext()) throw new NoSuchElementException();
        //noinspection WhileLoopReplaceableByForEach
        while (i.hasNext()) {
            last = i.next();
        }
        return last;
    }

    public static <T> int size(Iterable<T> range) {
        if (range == null) throw new ArgumentNullException("range");
        int size = 0;
        Iterator<T> i = range.iterator();
        //noinspection WhileLoopReplaceableByForEach
        while (i.hasNext()) {
            i.next();
            ++size;
        }
        return size;
    }

    public static <T> T firstOf(Iterable<T> range) {
        if (range == null) throw new ArgumentNullException("range");
        return range.iterator().next();
    }

    public static <From, To> Iterable<To> transform(final Iterable<From> from, final UnaryFunction<From, To> xform) {
        if (from == null) throw new ArgumentNullException("from");
        if (xform == null) throw new ArgumentNullException("xform");

        return new AbstractIterable<To>() {
            @Override
            public Iterator<To> iterator() {
                return transform(from.iterator(), xform);
            }
        };
    }

    public static <From, To> Iterator<To> transform(final Iterator<From> from, final UnaryFunction<From, To> xform) {
        if (from == null) throw new ArgumentNullException("from");
        if (xform == null) throw new ArgumentNullException("xform");

        return new Iterator<To>() {
            @Override
            public boolean hasNext() {
                return from.hasNext();
            }

            @Override
            public To next() {
                return xform.execute(from.next());
            }

            @Override
            public void remove() {
                from.remove();
            }
        };
    }

    public static <T> T accumulate(Iterable<T> range, BinaryFunction<T, T, T> binaryFunction) {
        Iterator<T> it = range.iterator();
        if (!it.hasNext()) throw new IllegalArgumentException();
        T init = it.next();

        return accumulate(init, it, binaryFunction);
    }

    public static <T> T accumulate(T init, Iterable<T> range, BinaryFunction<T, T, T> binaryFunction) {
        return accumulate(init, range.iterator(), binaryFunction);
    }

    private static <T> T accumulate(T init, Iterator<T> it, BinaryFunction<T, T, T> binaryFunction) {
        while (it.hasNext()) {
            init = binaryFunction.execute(init, it.next());
        }
        return init;
    }

    public static <T> Iterable<T> concat(Iterable<T>... iterables) {
        if (iterables == null) throw new ArgumentNullException("iterables");
        return new ConcatIterable<T>(asList(iterables));
    }

    public static <T> Iterable<T> concat(Iterable<Iterable<T>> iterables) {
        if (iterables == null) throw new ArgumentNullException("iterables");
        return new ConcatIterable<T>(iterables);
    }

    public static <From, To> Iterable<To> cast(Iterable<From> from) {
        return transform(from, new UnaryFunction<From, To>() {
            @Override
            public To execute(From from) {
                return (To) from;
            }
        });
    }

    public static <T, Col extends Collection<T>> Col copy(Iterable<T> src, Col target) {
        for (T item : src) {
            target.add(item);
        }
        return target;
    }
}
