package com.immomo.matrix.config.client;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import com.immomo.matrix.config.Constants;
import com.immomo.matrix.config.DataVersionPair;
import com.immomo.matrix.config.IConfigClient;
import com.immomo.matrix.config.IConfigListener;
import com.immomo.matrix.config.util.JedisPoolConfigUtil;
import com.immomo.matrix.util.NamedThreadFactory;

/**
 * 初始化过程：
 * <ol>
 * <li>从Thread context classloader中加载configcenter.properties进行初始化，成功则完成
 * <li>从{@link ConfigClient}
 * 类的classloader中加载configcenter-default.properties进行初始化。
 * </ol>
 * 
 * @author mixueqiang
 * @since Oct 18, 2012
 * 
 */
public class ConfigClient implements IConfigClient {
    private static final Log LOG = LogFactory.getLog(ConfigClient.class);

    private static JedisPool jedisPool;

    public static String getDataVersionKey(final String key) {
        return key + Constants.KEY_VERSION;
    }

    private ScheduledExecutorService subscriberExecutor = null;
    private ConcurrentMap<String, DataVersionPair> dataCache = new ConcurrentHashMap<String, DataVersionPair>();

    public ConfigClient() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("configcenter.properties");

            if (inputStream != null) {
                properties.load(inputStream);
                LOG.info("ConfigClient property file [configcenter.properties].");
                return;
            } else {
                inputStream = ConfigClient.class.getClassLoader()
                        .getResourceAsStream("configcenter-default.properties");
                properties.load(inputStream);
                LOG.info("ConfigClient property file [configcenter-default.properties].");
            }

            String host = properties.getProperty("host");
            int port = Integer.valueOf(properties.getProperty("port"));

            jedisPool = new JedisPool(JedisPoolConfigUtil.CONFIG_MEDIUM, host, port);
            LOG.info("ConfigClient initialized OK!");

        } catch (Exception e) {
            LOG.error("ConfigClient initialized error!", e);
        }
    }

    public ConfigClient(String host, int port) {
        jedisPool = new JedisPool(JedisPoolConfigUtil.CONFIG_MEDIUM, host, port);
        LOG.info("ConfigClient initialized OK!");
    }

    @Override
    public void publish(String dataId, String data) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            Transaction tx = jedis.multi();
            tx.set(dataId, data);
            tx.set(getDataVersionKey(dataId), DigestUtils.md5Hex(data));
            tx.exec();

        } catch (Exception e) {
            LOG.error("ConfigClient publish error for: " + dataId, e);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
    }

    @Override
    public String subscribe(String dataId) {
        return subscribe(dataId, false);
    }

    @Override
    public void subscribe(final String dataId, final IConfigListener... configListeners) {
        subscribe(dataId, 10, 15, configListeners);
    }

    @Override
    public void subscribe(final String dataId, long initialDelay, long period, final IConfigListener... configListeners) {
        if (subscriberExecutor == null) {
            subscriberExecutor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory(ConfigClient.class
                    .getSimpleName() + dataId));
        }

        subscriberExecutor.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                final String data = subscribe(dataId, true);
                if (data == null) { // 配置中心无数据，或数据从上次订阅后未被修改过。
                    return;
                }

                for (final IConfigListener configListener : configListeners) {
                    Executor executor = configListener.getExecutor();
                    if (executor != null) {
                        executor.execute(new Runnable() {

                            @Override
                            public void run() {
                                configListener.dataReceived(data);
                            }
                        });
                    } else {
                        configListener.dataReceived(data);
                    }
                }
            }
        }, initialDelay, period, TimeUnit.SECONDS);
    }

    /**
     * 注意：关闭线程池，清除缓存，但未释放Redis连接池。
     */
    public synchronized void stop() {
        LOG.info("ConfigClient shutting down.");

        if (subscriberExecutor != null) {
            subscriberExecutor.shutdownNow();
            subscriberExecutor = null;
        }

        dataCache.clear();
        LOG.info("ConfigClient shut down OK.");
    }

    /**
     * 订阅数据。
     * <p>
     * 如果onlyAcceptChange设置为true，则只有数据修改过，才会接收到数据；否则订阅结果为null。
     * 
     * @param dataId
     *            数据的key
     * @param onlyAcceptChange
     *            是否只接收修改过的数据
     * @return 数据
     */
    private String subscribe(String dataId, boolean onlyAcceptChange) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            DataVersionPair pair = dataCache.get(dataId);

            if (pair != null) { // 本地有缓存数据
                String dataVersion = jedis.get(getDataVersionKey(dataId));
                if (StringUtils.equals(dataVersion, pair.getVersion())) { // 未被修改
                    if (onlyAcceptChange) { // 只接收修改数据
                        return null;
                    } else {
                        return pair.getData();
                    }
                }
            }

            // 本地无缓存，或数据已被修改
            String data = jedis.get(dataId);
            dataCache.put(dataId, new DataVersionPair(data));
            return data;

        } catch (Exception e) {
            LOG.error("ConfigClient subscribe error for: " + dataId, e);
            return null;
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
    }

}
