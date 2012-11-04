package com.immomo.matrix.config;

import redis.clients.jedis.Jedis;

/**
 * @author mixueqiang
 * @since Oct 19, 2012
 * 
 */
public abstract class ConfigClientTestSupport {
    protected static final String HOST = "127.0.0.1";
    protected static final int PORT = 6379;

    protected Jedis jedis;
    protected String testKey;
    protected String testData;

    public ConfigClientTestSupport() {
        jedis = new Jedis(HOST, PORT);

        testKey = getClass().getSimpleName();
        testData = getClass().getCanonicalName();
    }

}
