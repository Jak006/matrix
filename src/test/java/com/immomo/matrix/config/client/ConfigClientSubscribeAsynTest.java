package com.immomo.matrix.config.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.immomo.matrix.config.ConfigClientTestSupport;
import com.immomo.matrix.config.IConfigClient;
import com.immomo.matrix.config.client.ConfigClient;
import com.immomo.matrix.config.listener.ConfigListenerAdapter;

/**
 * @author mixueqiang
 * @since Oct 19, 2012
 * 
 */
public class ConfigClientSubscribeAsynTest extends ConfigClientTestSupport {

    private IConfigClient configClient = null;

    @Before
    public void setUp() {
        configClient = new ConfigClient();
        configClient.publish(testKey, testData);
    }

    @Test
    public void testSubscribe() throws InterruptedException {
        configClient.subscribe(testKey, 3, 5, new ConfigListenerAdapter() {

            @Override
            public void dataReceived(String data) {
                System.out.println(data);
            }
        });

        configClient.publish(testKey, "1");
        Thread.sleep(8 * 1000);

        configClient.publish(testKey, "2");
        Thread.sleep(6 * 1000);

        configClient.publish(testKey, "3");
        Thread.sleep(7 * 1000);
    }

    @After
    public void tearDown() {
        jedis.del(testKey);
        jedis.del(ConfigClient.getDataVersionKey(testKey));
    }

}
