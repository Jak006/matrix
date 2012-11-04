package com.immomo.matrix.config;

/**
 * @author mixueqiang
 * @since Oct 19, 2012
 * 
 */
public final class Constants {

    // ConfigCenter KEY
    // ConfigCenter configuration
    public static final String KEY_WRITABLE = "_CC_CONFIGURATION";

    // data version suffux, eg: data_id_CC_VERSION
    public static final String KEY_VERSION = "_CC_VERSION";

    // ConfigCenter Data Status
    public static final String STATUS_OK = "_CC_200";
    public static final String STATUS_REDIRECT = "_CC_301";
    public static final String STATUS_NO_CHANGE = "_CC_304";
    public static final String STATUS_NOT_FOUND = "_CC_404";

    private Constants() {
    }

}
