package com.immomo.matrix.datastore;

import java.util.HashMap;
import java.util.Map;

/**
 * 非线程安全的数据存储组件。
 * <p>
 * 适用于在启动期初始化服务相关的数据，用于在服务的生命周期中使用。注意线程安全问题。
 * 
 * @author mixueqiang
 * @since Nov 28, 2012
 * 
 */
public class ThreadNotSafeDataStoreComponent implements DataStoreService {

    private Map<String, Map<String, Object>> datas = new HashMap<String, Map<String, Object>>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String componentName, String key) {
        if (!datas.containsKey(componentName)) {
            return null;
        }

        return (T) datas.get(componentName).get(key);
    }

    @Override
    public void put(String componentName, String key, Object value) {
        if (!datas.containsKey(componentName)) {
            Map<String, Object> componentDatas = new HashMap<String, Object>();
            datas.put(componentName, componentDatas);
        }

        datas.get(componentName).put(key, value);
    }

    @Override
    public void remove(String componentName, String key) {
        if (!datas.containsKey(componentName)) {
            return;
        }

        datas.get(componentName).remove(key);
    }

}
