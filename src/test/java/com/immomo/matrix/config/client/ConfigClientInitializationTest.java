package com.immomo.matrix.config.client;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.immomo.matrix.config.ConfigClientTestSupport;
import com.immomo.matrix.config.IConfigClient;
import com.immomo.matrix.config.client.ConfigClient;

/**
 * @author mixueqiang
 * @since Oct 19, 2012
 * 
 */
public class ConfigClientInitializationTest extends ConfigClientTestSupport {

    @Before
    public void setUp() {
        jedis.del(testKey);
    }

    @Test
    public void testInitialization0() {
        IConfigClient configClient = new ConfigClient();
        configClient.publish(testKey, testData);
        configClient.stop();

        Assert.assertEquals(testData, jedis.get(testKey));
    }

    @Test
    public void testInitialization1() {
        IConfigClient configClient = new ConfigClient(HOST, PORT);
        configClient.publish(testKey, testData);
        configClient.stop();

        Assert.assertEquals(testData, jedis.get(testKey));
    }

    @After
    public void tearDown() {
        jedis.del(testKey);
        jedis.del(ConfigClient.getDataVersionKey(testKey));
    }

}
