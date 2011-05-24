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
package com.crudetech.junit.feature;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.util.List;

class FeatureRunner extends BlockJUnit4ClassRunner {
    private final Object factory;

    FeatureRunner(Class<?> klass, Object factory) throws InitializationError {
        super(klass);

        if(factory == null){
            throw new InitializationError(new IllegalArgumentException("Factory instance must not be null!!"));
        }

        this.factory = factory;
    }

    /**
     * Returns a new fixture for running a test. Default implementation executes
     * the test class's no-argument constructor (validation should have ensured
     * one exists).
     */
    protected Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance(factory);
    }

    /**
     * Adds to {@code errors} if the test class has more than one constructor,
     * or if the constructor takes parameters. Override if a subclass requires
     * different validation rules.
     */
    protected void validateConstructor(List<Throwable> errors) {
        validateOnlyOneConstructor(errors);
        validateFactoryArgConstructor(errors);
    }

    private void validateFactoryArgConstructor(List<Throwable> errors) {
        if (getTestClass().getJavaClass().getConstructors().length != 1) {
            String gripe = "Test class should have exactly one public constructor taking a factory of type " + factory;
            errors.add(new Exception(gripe));
        }
    }
}
