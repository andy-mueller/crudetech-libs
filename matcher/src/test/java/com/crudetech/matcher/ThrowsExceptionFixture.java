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

import org.hamcrest.Matcher;
import org.junit.Test;

import javax.xml.bind.DataBindingException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.crudetech.matcher.ThrowsException.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;


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

    @Test
    public void runnableIsOnlyExecutedOnceOnFailure() {
        final AtomicInteger counter = new AtomicInteger(0);
        Runnable doIt = new Runnable() {
            public void run() {
                counter.incrementAndGet();
                throw new IllegalStateException();
            }
        };

        try {
            assertThat(doIt, doesThrow(IllegalArgumentException.class));
            fail("Don't want to be here!!");
        } catch (AssertionError ae) {
        }
        assertThat(counter.get(), is(1));
    }

    @Test
    public void matcherCanOnlyBeExecutedOneTime() {
        Matcher<Runnable> doesThrow = doesThrow(IllegalArgumentException.class);
        Runnable doThrow = new Runnable() {
            @Override
            public void run() {
                throw new IllegalArgumentException();
            }
        };

        doesThrow.matches(doThrow);

        try {
            doesThrow.matches(doThrow);
            fail("Expected exception was not thrown!");
        } catch (IllegalStateException e) {
        }
    }
}
