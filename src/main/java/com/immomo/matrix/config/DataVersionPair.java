package com.immomo.matrix.config;

import org.apache.commons.codec.digest.DigestUtils;

import com.immomo.matrix.util.Pair;

/**
 * @author mixueqiang
 * @since Oct 19, 2012
 * 
 */
public final class DataVersionPair extends Pair<String, String> {

    public DataVersionPair(String data) {
        super(data, DigestUtils.md5Hex(data));
    }

    public String getData() {
        return left;
    }

    public String getVersion() {
        return right;
    }

}
