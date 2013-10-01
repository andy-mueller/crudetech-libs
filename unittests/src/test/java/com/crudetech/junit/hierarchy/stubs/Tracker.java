package com.crudetech.junit.hierarchy.stubs;

import java.text.MessageFormat;

public class Tracker {
    static String last;

    public static String lastMethodRun() {
        return last;
    }

    public static void executed() {
        last = executingMethod();
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
        last = null;
    }
}
