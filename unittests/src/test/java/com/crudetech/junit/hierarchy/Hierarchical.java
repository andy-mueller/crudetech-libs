package com.crudetech.junit.hierarchy;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Hierarchical extends ParentRunner<Runner> {
    private final List<Runner> childRunners;

    public Hierarchical(Class<?> testClass) throws InitializationError {
        super(testClass);
        childRunners = buildChildRunners();
    }

    private List<Runner> buildChildRunners() throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        HierarchicalBlockJUnit4ClassRunner outerRunner = new HierarchicalBlockJUnit4RootClassRunner(getTestJavaClass());
        runners.add(outerRunner);
        for (Class<?> innerClass : getAllInnerClasses(getTestJavaClass())) {
            BlockJUnit4ClassRunner innerRunner = isStatic(innerClass)
                    ? new BlockJUnit4ClassRunner(innerClass)
                    : new BlockJUnit4NonStaticClassRunner(innerClass, outerRunner);
            runners.add(innerRunner);
        }
        return runners;
    }

    @Override
    protected Statement classBlock(RunNotifier notifier) {
        return super.classBlock(notifier);
        // build bafore/afterClass
        // all: setup/test/teardown of this class
        //recurse down
    }

    private Class<?> getTestJavaClass() {
        return getTestClass().getJavaClass();
    }

    @Override
    protected Description describeChild(Runner child) {
        return child.getDescription();
    }


    @Override
    protected void runChild(Runner runner, final RunNotifier notifier) {
        runner.run(notifier);
    }


    @Override
    protected List<Runner> getChildren() {
        return childRunners;
    }

    private static boolean isStatic(Class<?> innerClass) {
        return Modifier.isStatic(innerClass.getModifiers());
    }

    private static Class<?>[] getAllInnerClasses(Class<?> klass) {
        return klass.getClasses();
    }

}
