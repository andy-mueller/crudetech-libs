package com.crudetech.junit.hierarchy;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Hierarchical extends ParentRunner<Runner> {
    private final List<Runner> childRunners;
    private final BlockJUnit4InnerClassRunner thisNodeRunner;
    private final Hierarchical parent;

    @SuppressWarnings("unused")
    public Hierarchical(Class<?> testClass) throws InitializationError {
        this(testClass, null);
    }

    public Hierarchical(Class<?> testClass, Hierarchical parent) throws InitializationError {
        super(testClass);
        this.parent = parent;
        this.childRunners = buildChildRunners();
        this.thisNodeRunner = BlockJUnit4InnerClassRunner.createInnerClassRunner(testClass, getParentsNodeRunner());
    }

    private BlockJUnit4InnerClassRunner getParentsNodeRunner() {
        return return parent != null ? parent.thisNodeRunner : null;
    }

    private List<Runner> buildChildRunners() throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        runners.addAll(createInnerClassRunners());
        return runners;
    }

    private List<Runner> createInnerClassRunners() throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        for (Class<?> innerClass : getAllInnerClasses(getTestJavaClass())) {
            Runner innerClassContextRunner = new Hierarchical(innerClass, this);
            runners.add(innerClassContextRunner);
        }
        return runners;
    }

    @Override
    protected Statement childrenInvoker(RunNotifier notifier) {
        final Statement allTestMethods = thisNodeRunner.allTestMethodsBlock(notifier);
        final Statement allChildContexts = super.childrenInvoker(notifier);

        return new MultipleStatements(allTestMethods, allChildContexts);
    }

    @Override
    protected void runChild(Runner runner, final RunNotifier notifier) {
        runner.run(notifier);
    }

    private Class<?> getTestJavaClass() {
        return getTestClass().getJavaClass();
    }


    @Override
    protected Description describeChild(Runner child) {
        return child.getDescription();
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
