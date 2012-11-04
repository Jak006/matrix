package com.immomo.matrix.config.util;

import redis.clients.jedis.JedisPoolConfig;

/**
 * @author mixueqiang
 * @since Oct 18, 2012
 * 
 */
public final class JedisPoolConfigUtil {
    public static final JedisPoolConfig CONFIG_LARGE = new JedisPoolConfig();
    public static final JedisPoolConfig CONFIG_MEDIUM = new JedisPoolConfig();
    public static final JedisPoolConfig CONFIG_SMALL = new JedisPoolConfig();

    static {
        CONFIG_LARGE.setMaxActive(2000);
        CONFIG_LARGE.setMaxIdle(200);
        CONFIG_LARGE.setMaxWait(2000);
        CONFIG_LARGE.setTestOnBorrow(true);

        CONFIG_MEDIUM.setMaxActive(200);
        CONFIG_MEDIUM.setMaxIdle(50);
        CONFIG_MEDIUM.setMaxWait(2000);
        CONFIG_MEDIUM.setTestOnBorrow(true);

        CONFIG_SMALL.setMaxActive(5);
        CONFIG_SMALL.setMaxIdle(5);
        CONFIG_SMALL.setMaxWait(2000);
        CONFIG_SMALL.setTestOnBorrow(true);
    }

    private JedisPoolConfigUtil() {
    }

}
