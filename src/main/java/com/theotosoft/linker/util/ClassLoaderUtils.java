package com.theotosoft.linker.util;

import java.io.InputStream;

/**
 * @author mixueqiang
 * @since 2012-10-23
 * 
 */
public class ClassLoaderUtils {

    public static InputStream getContextResource(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

}
