package com.crudetech.junit.hierarchy.stubs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tracker {
    private static List<String> methods = Collections.synchronizedList(new ArrayList<String>());

    public static String lastMethodRun() {
        return methods.get(methods.size() - 1);
    }

    public static void executed() {
        methods.add(executingMethod());
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
        methods.clear();
    }

    public static boolean methodWasRun(String method) {
        return methods.contains(method);
    }
}
