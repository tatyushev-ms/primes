package com.tatyushevms.primes;

import java.io.InputStream;
import java.net.URL;

public final class TestUtils {
    
    private TestUtils() {
        super();
    }
    
    public static InputStream testClasspathResourceAsStream(final String path) {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return contextClassLoader.getResourceAsStream(path);
    }
    
    public static String testClasspathResourceFullName(final String path) {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        final URL resource = contextClassLoader.getResource(path);
        return resource == null ? null : resource.getPath();
    }
    
}
