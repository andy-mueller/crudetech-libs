package com.crudetech.junit.hierarchy;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class Context extends BlockJUnit4ClassRunner {
     public Context(Class<?> klass) throws InitializationError {
        super(klass);
    }
}
