package com.immomo.matrix.service;

import java.util.HashMap;
import java.util.Map;

import com.immomo.matrix.Constants;
import com.immomo.matrix.datastore.DataStoreService;
import com.immomo.matrix.datastore.ThreadNotSafeDataStoreComponent;

/**
 * 管理所有的服务元数据、服务实例。
 * 
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public abstract class ServiceProviderFactory {
    public static final String COMPONENT_NAME = "SERVICE_PROVIDER_FACTORY";

    /**
     * <ul>
     * <li>&lt;MATRIX_SERVER, METADATAS&lt;ServiceUniqueName, Metadata&gt;&gt;
     * <li>&lt;MATRIX_SERVER, WORKERS&lt;ServiceUniqueName, Worker&gt;&gt;
     * </ul>
     */
    public static DataStoreService dataStoreService = new ThreadNotSafeDataStoreComponent();

    /**
     * Get service instance through service name.
     */
    public ServiceInstance getInstance(String serviceName) {
        Map<String, ServiceInstance> serviceInstances = dataStoreService.get(COMPONENT_NAME,
                Constants.KEY_STORE_SERVICES);
        if (serviceInstances == null) {
            return null;
        }

        return serviceInstances.get(serviceName);
    }

    /**
     * Register service instance.
     */
    protected void registerServiceInstance(String serviceName, ServiceInstance serviceInstance) {
        if (serviceInstance == null) {
            throw new IllegalArgumentException("Service [" + serviceName + "] instance is null!");
        }

        Map<String, ServiceInstance> serviceInstances = dataStoreService.get(COMPONENT_NAME,
                Constants.KEY_STORE_SERVICES);
        if (serviceInstances == null) {
            serviceInstances = new HashMap<String, ServiceInstance>();
        }
        serviceInstances.put(serviceName, serviceInstance);
        dataStoreService.put(COMPONENT_NAME, Constants.KEY_STORE_SERVICES, serviceInstances);
    }

    /**
     * Register service metadata.
     */
    protected void registerServiceMetadata(String serviceName, ServiceMetadata metadata) {
        if (metadata == null) {
            throw new IllegalArgumentException("Service [" + serviceName + "] metadata is null!");
        }

        Map<String, ServiceMetadata> metadatas = dataStoreService.get(COMPONENT_NAME, Constants.KEY_STORE_METADATAS);
        if (metadatas == null) {
            metadatas = new HashMap<String, ServiceMetadata>();
        }

        metadatas.put(serviceName, metadata);
        dataStoreService.put(COMPONENT_NAME, Constants.KEY_STORE_METADATAS, metadatas);
    }

}
