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
package com.crudetech.lang;


import org.junit.Test;

import java.sql.SQLException;

import static com.crudetech.lang.SneakyThrow.sneakyThrow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SneakyThrowTest {
    @Test
    public void sneakyThrowsTunnelsCheckedExceptions(){
        try{
            throwCheckedException();
        }catch(Exception ex){
            assertThat(ex, is(not(instanceOf(RuntimeException.class))));
        }
    }

    private void throwCheckedException() {
        throw sneakyThrow(new SQLException());
    }
}
