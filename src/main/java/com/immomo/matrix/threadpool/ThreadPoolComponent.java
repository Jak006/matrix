package com.immomo.matrix.threadpool;

import java.util.concurrent.Executor;

import com.immomo.matrix.Request;

/**
 * 线程池管理组件。
 * 
 * @author mixueqiang
 * @since 2012-11-30
 * 
 */
public class ThreadPoolComponent implements ThreadPoolService {

    @Override
    public void registerThreadPool(String serviceName, int corePoolSize, int maxPoolSize) {
        // TODO Auto-generated method stub

    }

    @Override
    public Executor getThreadExecutor(String serviceName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Executor getThreadExecutor(Request request) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void shutdown() {
        // TODO Auto-generated method stub

    }

}
