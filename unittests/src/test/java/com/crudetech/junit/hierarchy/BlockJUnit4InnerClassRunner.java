package com.crudetech.junit.hierarchy;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.reflect.Modifier;
import java.util.List;

public class BlockJUnit4InnerClassRunner extends BlockJUnit4ClassRunner {
    final BlockJUnit4InnerClassRunner parentRunner;

    private BlockJUnit4InnerClassRunner(Class<?> testClass, BlockJUnit4InnerClassRunner parentRunner) throws InitializationError {
        super(testClass);
        this.parentRunner = parentRunner;
    }

    public static BlockJUnit4InnerClassRunner createInnerClassRunner(Class<?> testClass, BlockJUnit4InnerClassRunner parentRunner) throws InitializationError {
        return isStatic(testClass)
                ? new BlockJUnit4StaticInnerClassRunner(testClass, parentRunner)
                : new BlockJUnit4NonStaticInnerClassRunner(testClass, parentRunner);
    }

    private static boolean isStatic(Class<?> clazz) {
        return Modifier.isStatic(clazz.getModifiers());
    }

    public Statement allTestMethodsBlock(RunNotifier notifier) {
        return super.childrenInvoker(notifier);
    }


    @Override
    protected void validateInstanceMethods(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(After.class, false, errors);
        validatePublicVoidNoArgMethods(Before.class, false, errors);
        validateTestMethods(errors);
    }

    private static class BlockJUnit4StaticInnerClassRunner extends BlockJUnit4InnerClassRunner {
        private BlockJUnit4StaticInnerClassRunner(Class<?> testClass, BlockJUnit4InnerClassRunner parentRunner) throws InitializationError {
            super(testClass, parentRunner);
        }
    }

    private static class BlockJUnit4NonStaticInnerClassRunner extends BlockJUnit4InnerClassRunner {
        private BlockJUnit4NonStaticInnerClassRunner(Class<?> testClass, BlockJUnit4InnerClassRunner parentRunner) throws InitializationError {
            super(testClass, parentRunner);
        }

        @Override
        protected void validateNoNonStaticInnerClass(List<Throwable> errors) {
        }

        protected void validateConstructor(List<Throwable> errors) {
            validateOnlyOneConstructor(errors);
            validateOneArgumentConstructor(errors);
        }

        private void validateOneArgumentConstructor(List<Throwable> errors) {
            if (getTestClass().isANonStaticInnerClass()
                    && (getTestClass().getOnlyConstructor().getParameterTypes().length != 1)) {
                String gripe = "Test class should have exactly one public inner class one -argument constructor";
                errors.add(new Exception(gripe));
            }
        }

        @Override
        protected Object createTest() throws Exception {
            Object[] arguments = new Object[]{parentRunner.createTest()};
            return getTestClass().getOnlyConstructor().newInstance(arguments);
        }
    }
}
