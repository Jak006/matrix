package com.immomo.matrix.util;

import java.net.URI;

import org.apache.commons.lang.StringUtils;

/**
 * @author mixueqiang
 * @since 2012-10-25
 * 
 */
public class URIUtils {

    public static int getIntParameter(URI uri, String parameterName, int defaultValue) {
        String strValue = getParameter(uri, parameterName, null);
        if (strValue != null) {
            return Integer.parseInt(strValue);
        }

        return defaultValue;
    }

    public static long getLongParameter(URI uri, String parameterName, long defaultValue) {
        String strValue = getParameter(uri, parameterName, null);
        if (strValue != null) {
            return Long.parseLong(strValue);
        }

        return defaultValue;
    }

    public static String getParameter(URI uri, String parameterName, String defaultValue) {
        String result = StringUtils.substringBetween(uri.getQuery(), parameterName + "=", "&");
        if (result != null) {
            return result;
        }

        result = StringUtils.substringAfter(uri.getQuery(), parameterName + "=");
        if (result != null) {
            return result;
        }

        return defaultValue;
    }

}
