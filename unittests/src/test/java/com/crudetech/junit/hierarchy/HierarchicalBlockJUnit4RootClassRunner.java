package com.crudetech.junit.hierarchy;

import org.junit.After;
import org.junit.Before;
import org.junit.runners.model.InitializationError;

import java.util.List;

class HierarchicalBlockJUnit4RootClassRunner extends HierarchicalBlockJUnit4ClassRunner {

    public HierarchicalBlockJUnit4RootClassRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    // This is really a hack!! This method is the copy of the original super class
    //method, minus the commented out two lines at the end. These do prevent an
    // instantiation of this class, if there ar eno test methods and unfortunately
    // they were not extracted into an overrideable method.
    @Override
    protected void validateInstanceMethods(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(After.class, false, errors);
        validatePublicVoidNoArgMethods(Before.class, false, errors);
        validateTestMethods(errors);
//        if (computeTestMethods().size() == 0)
//            errors.add(new Exception("No runnable methods"));
    }
}
