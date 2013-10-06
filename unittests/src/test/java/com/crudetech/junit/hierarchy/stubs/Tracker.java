package com.crudetech.junit.hierarchy.stubs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

public class Tracker {
    private static List<String> collectedMethods = Collections.synchronizedList(new ArrayList<String>());

    public static void executed() {
        collectedMethods.add(executingMethod());
    }

    private static String executingMethod() {
        StackTraceElement callingMethod = Thread.currentThread().getStackTrace()[3];
        String method = callingMethod.getMethodName();
        String className = getClassName(callingMethod);
        return MessageFormat.format("{0}#{1}", className, method);
    }

    private static String getClassName(StackTraceElement element) {
        String className = element.getClassName();
        int start = className.lastIndexOf('.') + 1;
        return className.substring(start);
    }

    public static void reset() {
        collectedMethods.clear();
    }

    public static void assertMethodWasRun(String method) {
        assertThat(collectedMethods.contains(method), is(true));
    }

    public static void assertExecutionOrder(String... methods) {
        assertThat(Collections.indexOfSubList(collectedMethods, asList(methods)), is(greaterThanOrEqualTo(0)));
    }
}
