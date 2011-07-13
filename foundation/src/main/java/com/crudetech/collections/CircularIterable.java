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

import com.crudetech.lang.ArgumentNullException;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Arrays.asList;

/**
 * A collection api compatible circular iterator that lets you
 * iterate endlessly over a given {@link java.util.List} implementation.
 * When it reaches the end of the collection it will wrap around and
 * continue at the beginning again.
 */
public class CircularIterable<T> implements Iterable<T>{
    private final List<? extends T> list;

    public CircularIterable(List<? extends T> list) {
        this.list = list;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
              private int pos = -1;

              @Override
              public boolean hasNext() {
                  return !list.isEmpty();
              }

              @Override
              public T next() {
                  if(list.isEmpty()){
                      throw new NoSuchElementException();
                  }
                  final int currentIndex = (++pos) % list.size();
                  return list.get(currentIndex);
              }
              @Override
              public void remove() {
                  throw new UnsupportedOperationException();
              }
          };
    }

    public static <T> CircularIterable<T> from(List<T> items) {
        if(items == null){
            throw new ArgumentNullException("items");
        }
        return new CircularIterable<T>(items);
    }

    public static <T> CircularIterable<T> from(T... items) {
        if(items == null){
            throw new ArgumentNullException("items");
        }
        return from(asList(items));
    }
}
