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
package com.crudetech.unittests;

import java.util.Collection;
import java.util.List;

/**
 * A base fixture for objects that implement the {@List} interface.
 * @param <T>
 */
public abstract class ResizeableListFixture<T> extends ResizeableCollectionFixture<T> {
    protected abstract List<T> createList();
    protected abstract List<T> createEmptyList();
    @Override
    protected Collection<T> createCollection() {
        return createList();
    }

    @Override
    protected Collection<T> createEmptyCollection() {
        return createEmptyList();
    }
}
