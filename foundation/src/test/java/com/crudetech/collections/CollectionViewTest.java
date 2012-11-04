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
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CollectionViewTest {
    UnaryFunction<Long, Integer> modelToView = new UnaryFunction<Long, Integer>() {
        @Override
        public Integer execute(Long aLong) {
            return (int)aLong.longValue();
        }
    };
    UnaryFunction<Integer, Long> viewToModel= new UnaryFunction<Integer, Long>() {
        @Override
        public Long execute(Integer integer) {
            return (long)integer.intValue();
        }
    };

    @Test
    public void viewTransformsOnRead() {
        Collection<Long> src = asList(0L, 1L, 2L);

        CollectionView<Integer, Long> view = new CollectionView<Integer, Long>(src, modelToView, viewToModel);

        Collection<Integer> expected = asList(0, 1, 2);
        assertThat(view, is(expected));
    }

    private static <T> List<T> asList(T... items) {
        return new ArrayList<T>(Arrays.<T>asList(items));
    }

    @Test
    public void viewTransformsOnWrite() {
        Collection<Long> src = asList(0L, 1L, 2L);

        CollectionView<Integer, Long> view = new CollectionView<Integer, Long>(src, modelToView, viewToModel);
        view.add(42);

        Collection<Integer> expected = asList(0, 1, 2, 42);
        assertThat(view, is(expected));
        Collection<Long> expectedSrc = asList(0L, 1L, 2L, 42L);
        assertThat(src, is(expectedSrc));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void readonlyViewThrowsOnWrite() {
        Collection<Long> src = asList(0L, 1L, 2L);

        CollectionView<Integer, Long> view = new CollectionView<Integer, Long>(src, modelToView);
        view.add(42);
    }
}
