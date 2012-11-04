package com.immomo.matrix.config;

import java.util.concurrent.Executor;

/**
 * <pre>
 * User: -XMx, mixueqiang
 * Date: 12-9-25
 * Time: 下午5:53
 * E-mail:blackbeans.zc@gmail.com
 * </pre>
 */
public interface IConfigListener {

    /**
     * 获得消息处理的线程或线程池实例。可以返回null。
     * <p>
     * 当为null时，将使用主订阅线程进行处理。
     */
    Executor getExecutor();

    /**
     * 接收到配置信息后，进行处理。
     */
    void dataReceived(String data);

}