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
package com.crudetech.lang;

/**
 *
 */
public class NotImplementedException extends UnsupportedOperationException{
    public NotImplementedException() {
    }

    public NotImplementedException(String s) {
        super(s);
    }

    public NotImplementedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotImplementedException(Throwable throwable) {
        super(throwable);
    }
}
