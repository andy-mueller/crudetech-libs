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
package com.crudetech.query;

import com.crudetech.lang.ArgumentNullException;

import java.util.Iterator;

import static java.util.Arrays.asList;

public class Query {
    private Query(){}
    public static <T> Queryable<T> from(final Iterable<T> range){
        if(range == null) throw new ArgumentNullException("range");
        return new AbstractQueryable<T>() {
            @Override
            public Iterator<T> iterator() {
                return range.iterator();                
            }
        };
    }

    public static <T> Queryable<T> from(T... range) {
        if(range == null) throw new ArgumentNullException("range");        
        return from(asList(range));
    }
}
