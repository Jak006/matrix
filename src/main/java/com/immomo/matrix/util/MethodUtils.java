package com.immomo.matrix.util;

/**
 * @author mixueqiang
 * @since 2012-10-23
 * 
 */
public class MethodUtils {

    public static String[] getMethodArgSigs(Class<?>[] parameterTypes) {
        String[] methodArgSigs = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            methodArgSigs[i] = parameterTypes[i].getName();
        }

        return methodArgSigs;
    }

    public static String getMethodKey(String methodName, Class<?>[] parameterTypes) {
        String[] methodArgSigs = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            methodArgSigs[i] = parameterTypes[i].getName();
        }

        return getMethodKey(methodName, methodArgSigs);
    }

    public static String getMethodKey(String methodName, String... methodArgSigs) {
        StringBuffer sb = new StringBuffer(methodName);
        for (String methodArgSig : methodArgSigs) {
            sb.append(methodArgSig);
        }
        return sb.toString();
    }

}
