package com.crudetech.junit.hierarchy;

import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.List;

public class Hierarchical extends Suite {

    public Hierarchical(Class<?> klass) throws InitializationError {
        super(klass, computeTestAndSubTests(klass));
    }

    private static List<Runner> computeTestAndSubTests(Class<?> klass) throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        for (Class<?> innerClass : getAllInnerClasses(klass)) {
            runners.add(new BlockJUnit4ClassRunner(innerClass));
        }
        return runners;
    }

    private static Class<?>[] getAllInnerClasses(Class<?> klass) {
        return klass.getClasses();
    }
}
