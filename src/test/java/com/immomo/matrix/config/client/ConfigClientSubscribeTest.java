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
public class ConfigClientSubscribeTest extends ConfigClientTestSupport {

    private IConfigClient configClient = null;

    @Before
    public void setUp() {
        configClient = new ConfigClient();
        configClient.publish(testKey, testData);
    }

    @Test
    public void testSubscribe() {
        String data = configClient.subscribe(testKey);
        Assert.assertEquals(testData, data);

        data = configClient.subscribe(testKey);
        Assert.assertEquals(testData, data);
    }

    @Test
    public void testSubscribeAfterChange() {
        String newData = String.valueOf(System.currentTimeMillis());
        jedis.set(testKey, newData);

        String data = configClient.subscribe(testKey);
        Assert.assertNotSame(testData, data);
        Assert.assertEquals(newData, data);
    }

    @After
    public void testDown() {
        jedis.del(testKey);
        jedis.del(ConfigClient.getDataVersionKey(testKey));
    }

}
