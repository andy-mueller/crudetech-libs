package com.crudetech.junit.hierarchy;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

class HierarchicalBlockJUnit4ClassRunner extends BlockJUnit4ClassRunner {

    public HierarchicalBlockJUnit4ClassRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Object createTest() throws Exception {
        return super.createTest();
    }
}
