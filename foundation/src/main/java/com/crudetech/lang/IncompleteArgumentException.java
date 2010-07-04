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


public class IncompleteArgumentException extends IllegalArgumentException{
    public IncompleteArgumentException() {
    }

    public IncompleteArgumentException(String s) {
        super(s);
    }

    public IncompleteArgumentException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public IncompleteArgumentException(Throwable throwable) {
        super(throwable);
    }
}
