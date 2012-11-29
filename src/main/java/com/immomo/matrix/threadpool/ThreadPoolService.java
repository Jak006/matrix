package com.immomo.matrix.threadpool;

import java.util.concurrent.Executor;

import com.immomo.matrix.Request;

/**
 * @author mixueqiang
 * @since 2012-11-30
 * 
 */
public interface ThreadPoolService {

    /**
     * 为一个服务注册线程池。
     */
    void registerThreadPool(String serviceName, int corePoolSize, int maxPoolSize);

    /**
     * 获得线程池。
     */
    Executor getThreadExecutor(String serviceName);

    /**
     * 获得线程池。
     */
    Executor getThreadExecutor(Request request);

    /**
     * 关闭线程池服务：注意释放所有已分配线程池。
     */
    void shutdown();

}
