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

import java.text.MessageFormat;


public class ArgumentOutOfBoundsException extends IllegalArgumentException {
    public ArgumentOutOfBoundsException(String argument) {
        super(MessageFormat.format("The argument {0} was out of bounds.", argument));
    }

    public ArgumentOutOfBoundsException(String argument, String msg) {
        super(MessageFormat.format("The argument {0} was out of bounds. \n{1}", argument, msg));
    }

    public ArgumentOutOfBoundsException(String argument, String msg, Throwable throwable) {
        super(MessageFormat.format("The argument {0} was out of bounds. \n{1}", argument, msg), throwable);
    }

    public ArgumentOutOfBoundsException(String argument, Throwable throwable) {
        super(MessageFormat.format("The argument {0} was out of bounds.", argument), throwable);
    }
}
