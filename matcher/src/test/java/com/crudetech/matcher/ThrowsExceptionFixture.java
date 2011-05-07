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
package com.crudetech.matcher;

import org.junit.Test;

import javax.xml.bind.DataBindingException;
import java.sql.SQLException;

import static com.crudetech.matcher.ThrowsException.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class ThrowsExceptionFixture {
    @Test
    public void doesNotThrowDifferentExceptionType() {
        assertThat(new Runnable() {
            public void run() {
                throw new IndexOutOfBoundsException("OOO");
            }
        }, doesNotThrow(IllegalArgumentException.class));
    }

    @Test
    public void doesNotThrowAtAll() {
        assertThat(new Runnable() {
            public void run() {
                System.out.println();
            }
        }, doesNotThrow());
    }

    @Test
    public void doesThrowTest() {
        assertThat(new Runnable() {
            public void run() {
                throw new IndexOutOfBoundsException("OOO");
            }
        }, doesThrow(IndexOutOfBoundsException.class));
    }

    @Test
    public void doesThrowDifferentExceptionType() {
        assertThat(new Runnable() {
            public void run() {
                throw new IndexOutOfBoundsException("OOO");
            }
        }, doesNotThrow(IllegalAccessException.class));
    }

    @Test
    public void doesThrowWithCause() {
        Runnable doIt = new Runnable() {
            public void run() {
                throw new DataBindingException("OOO", new SQLException());
            }
        };
        assertThat(doIt, doesThrow(DataBindingException.class, withCause(SQLException.class)));
    }
    @Test
    public void doesThrowWithMsg() {
        Runnable doIt = new Runnable() {
            public void run() {
                throw new DataBindingException("OOO", new SQLException());
            }
        };
        assertThat(doIt, doesThrow(DataBindingException.class, withMessage("OOO")));
    }
    @Test
    public void doesThrowWithMsgAndCause() {
        Runnable doIt = new Runnable() {
            public void run() {
                throw new DataBindingException("OOO", new SQLException());
            }
        };
        assertThat(doIt, doesThrow(DataBindingException.class, withMessage("OOO"), withCause(SQLException.class)));
    }
    @Test
    public void doesThrowWithMsgAndCauseFails() {
        Runnable doIt = new Runnable() {
            public void run() {
                throw new DataBindingException("OOO", new SQLException());
            }
        };
        assertThat(doIt, not(doesThrow(DataBindingException.class, withMessage("XXX"), withCause(SQLException.class))));
    }
    @Test
    public void doesThrowWithCauseFails() {
        Runnable doIt = new Runnable() {
            public void run() {
                throw new DataBindingException("OOO", new SQLException());
            }
        };
        assertThat(doIt, not(doesThrow(DataBindingException.class, withCause(RuntimeException.class))));
    }
}
