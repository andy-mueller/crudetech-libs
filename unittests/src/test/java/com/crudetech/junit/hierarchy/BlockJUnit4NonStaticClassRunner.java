package com.crudetech.junit.hierarchy;

import org.junit.runners.model.InitializationError;

import java.util.List;

public class BlockJUnit4NonStaticClassRunner extends HierarchicalBlockJUnit4ClassRunner {
    private final HierarchicalBlockJUnit4ClassRunner outerRunner;

    public BlockJUnit4NonStaticClassRunner(Class<?> klass, HierarchicalBlockJUnit4ClassRunner outerRunner) throws InitializationError {
        super(klass);
        this.outerRunner = outerRunner;
    }

    @Override
    protected void validateZeroArgConstructor(List<Throwable> errors) {
        if (hasOneConstructor()
                && hasNotOnlyOneOuterClassReferenceArgument()) {
            String gripe= "Test class should have exactly one public zero-argument constructor";
            errors.add(new Exception(gripe));
        }
    }
    private boolean hasOneConstructor() {
        return getTestClass().getJavaClass().getConstructors().length == 1;
    }
    private boolean hasNotOnlyOneOuterClassReferenceArgument() {
        return !(getTestClass().getOnlyConstructor().getParameterTypes().length == 1);
    }

    @Override
    protected Object createTest() throws Exception {
        Object outerTest = outerRunner.createTest();
        return getTestClass().getOnlyConstructor().newInstance(outerTest);
    }
}
