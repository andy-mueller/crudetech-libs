package com.crudetech.lang;

/**
 * A resource that must be closed when it is no longer needed.
 * <b>
 * This interface is placeholder to help to prepare for the JDK 7 AutoCloseable
 * implementation.
 */
public interface AutoCloseable /*extends java.lang.AutoCloseable*/{
    void close() throws Exception;
}
