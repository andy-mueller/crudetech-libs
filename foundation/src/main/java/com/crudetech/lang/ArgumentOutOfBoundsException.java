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



public class ArgumentOutOfBoundsException extends IllegalArgumentException{
    public ArgumentOutOfBoundsException() {
    }

    public ArgumentOutOfBoundsException(String s) {
        super(s);
    }

    public ArgumentOutOfBoundsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ArgumentOutOfBoundsException(Throwable throwable) {
        super(throwable);
    }
}
