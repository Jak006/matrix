package com.immomo.matrix.datastore;

/**
 * 数据存储服务。
 * <p>
 * 用于存储全局数据，在多个组件间共享信息。
 * 
 * @author mixueqiang
 * @since Nov 28, 2012
 * 
 */
public interface DataStoreService {

    /**
     * 读取数据。
     */
    <T> T get(String componentName, String key);

    /**
     * 存储数据。
     */
    void put(String componentName, String key, Object value);

    /**
     * 删除数据。
     */
    void remove(String componentName, String key);

}
