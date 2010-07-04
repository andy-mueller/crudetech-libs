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

import org.junit.Test;

import java.io.IOException;


public class AbstractRunnableFixture {
    @Test(expected = IOException.class)
    public void doesThrowCheckedException(){
        Runnable r = new AbstractRunnable() {
            @Override
            protected void doRun() throws Exception {
                throw new IOException("BOOM!");
            }
        };
        r.run();
    }
}