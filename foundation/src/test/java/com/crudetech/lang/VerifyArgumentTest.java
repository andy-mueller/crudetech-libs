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

import static com.crudetech.junit.AssertThrows.assertNoThrow;
import static com.crudetech.junit.AssertThrows.assertThrows;


public class VerifyArgumentTest {
    @Test
    public void isNotNullThrowsWhenArgIsNull() {
        Runnable isNotNullWithNull = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isNotNull("anArg", null);
            }
        };
        assertThrows(isNotNullWithNull, ArgumentNullException.class);
    }

    @Test
    public void testIsNotNull() throws Exception {
        Runnable isNotNullWithValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isNotNull("anArg", new Object());
            }
        };
        assertNoThrow(isNotNullWithValue);
    }

    @Test
    public void isLessWithLessDoesNotThrow() {
        Runnable isLessWithCorrectValues = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isLess("anArg", 1, 2);
            }
        };
        assertNoThrow(isLessWithCorrectValues);
    }

    @Test
    public void isLessWithNonLessDoesThrow() {
        Runnable isLessWithEqualValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isLess("anArg", 2, 2);
            }
        };
        assertThrows(isLessWithEqualValue, ArgumentOutOfBoundsException.class);
    }

    @Test
    public void isLessEqualWithLessDoesNotThrow() {
        Runnable isLessWithEqualValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isLessEqual("anArg", 1, 2);
            }
        };
        assertNoThrow(isLessWithEqualValue);
    }

    @Test
    public void isLessEqualWithEqualDoesNotThrow() {
        Runnable isLessWithEqualValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isLessEqual("anArg", 2, 2);
            }
        };
        assertNoThrow(isLessWithEqualValue);
    }

    @Test
    public void isLessEqualWithGreaterDoesThrow() {
        Runnable isLessEqualWithGreaterWithEqualValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isLessEqual("anArg", 3, 2);
            }
        };
        assertThrows(isLessEqualWithGreaterWithEqualValue, ArgumentOutOfBoundsException.class);
    }


    @Test
    public void isGreaterEqualWithGreaterDoesNotThrow() {
        Runnable isGreaterEqualWithGreater = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isGreaterEqual("anArg", 3, 2);
            }
        };
        assertNoThrow(isGreaterEqualWithGreater);
    }

    @Test
    public void isGreaterEqualWithEqualDoesNotThrow() {
        Runnable isGreaterEqualWithEqual = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isGreaterEqual("anArg", 2, 2);
            }
        };
        assertNoThrow(isGreaterEqualWithEqual);
    }

    @Test
    public void isGreaterEqualWithNonGreaterDoesThrow() {
        Runnable isGreaterEqualWithSmaller = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isGreaterEqual("anArg", 2, 3);
            }
        };
        assertThrows(isGreaterEqualWithSmaller, ArgumentOutOfBoundsException.class);
    }

    @Test
    public void isGreaterWithNonGreaterDoesThrow() {
        Runnable isGreaterWithNonGreaterValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isGreater("anArg", 2, 3);
            }
        };
        assertThrows(isGreaterWithNonGreaterValue, ArgumentOutOfBoundsException.class);
    }

    @Test
    public void isGreaterWithEqualDoesThrow() {
        Runnable isGreaterWithEqualValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isGreater("anArg", 3, 3);
            }
        };
        assertThrows(isGreaterWithEqualValue, ArgumentOutOfBoundsException.class);
    }

    @Test
    public void isGreaterWithSmallerDoesNotThrow() {
        Runnable isGreaterWithSmallerValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isGreater("anArg", 4, 3);
            }
        };
        assertNoThrow(isGreaterWithSmallerValue);
    }

    @Test
    public void isEqualWithEqualValueDoesNotThrow() {
        Runnable isEqualWithNonEqualValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isEqual("anArg", 3, 3);
            }
        };
        assertNoThrow(isEqualWithNonEqualValue);
    }

    @Test
    public void isEqualWithNonEqualValueDoesThrow() {
        Runnable isEqualWithNonEqualValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isEqual("anArg", -3, 3);
            }
        };
        assertThrows(isEqualWithNonEqualValue, IllegalArgumentException.class);
    }

    @Test
    public void isNotEqualWithNonEqualValueDoesNotThrow() {
        Runnable isNotEqualWithNonEqualValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isNotEqual("anArg", -3, 3);
            }
        };
        assertNoThrow(isNotEqualWithNonEqualValue);
    }

    @Test
    public void isNotEqualWithEqualValueDoesThrow() {
        Runnable isNotEqualWithEqualValue = new Runnable() {
            @Override
            public void run() {
                VerifyArgument.isNotEqual("anArg", 3, 3);
            }
        };
        assertThrows(isNotEqualWithEqualValue, IllegalArgumentException.class);
    }
}
