package com.immomo.matrix.util;

import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * @author mixueqiang
 * @since 2012-10-25
 * 
 */
public class URLUtils {

    public static int getIntParameter(URL url, String parameterName, int defaultValue) {
        String strValue = getParameter(url, parameterName, null);
        if (strValue != null) {
            return Integer.parseInt(strValue);
        }

        return defaultValue;
    }

    public static long getLongParameter(URL url, String parameterName, long defaultValue) {
        String strValue = getParameter(url, parameterName, null);
        if (strValue != null) {
            return Long.parseLong(strValue);
        }

        return defaultValue;
    }

    public static String getParameter(URL url, String parameterName, String defaultValue) {
        String result = StringUtils.substringBetween(url.getQuery(), parameterName + "=", "&");
        if (result != null) {
            return result;
        }

        result = StringUtils.substringAfter(url.getQuery(), parameterName + "=");
        if (result != null) {
            return result;
        }

        return defaultValue;
    }

}
