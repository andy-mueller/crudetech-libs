package com.crudetech.junit.hierarchy.stubs;

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
        String className = callingMethod.getClassName();
        int start = className.lastIndexOf('.') + 1;
        className = className.substring(start);
        return className + "#" + method;
    }

    public static void reset() {
        last = null;
    }
}
