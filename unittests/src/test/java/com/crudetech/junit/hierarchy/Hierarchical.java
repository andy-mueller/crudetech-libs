package com.crudetech.junit.hierarchy;

import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Hierarchical extends Suite {

    public Hierarchical(Class<?> klass) throws InitializationError {
        super(klass, computeTestAndSubTests(klass));
    }

    private static List<Runner> computeTestAndSubTests(Class<?> klass) throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        HierarchicalBlockJUnit4ClassRunner outerRunner = new HierarchicalBlockJUnit4RootClassRunner(klass);
        runners.add(outerRunner);
        for (Class<?> innerClass : getAllInnerClasses(klass)) {
            BlockJUnit4ClassRunner innerRunner = isStatic(innerClass)
                    ? new BlockJUnit4ClassRunner(innerClass)
                    : new BlockJUnit4NonStaticClassRunner(innerClass, outerRunner);
            runners.add(innerRunner);
        }
        return runners;
    }

    private static boolean isStatic(Class<?> innerClass) {
        return Modifier.isStatic(innerClass.getModifiers());
    }

    private static Class<?>[] getAllInnerClasses(Class<?> klass) {
        return klass.getClasses();
    }
}
