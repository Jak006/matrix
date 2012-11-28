package com.immomo.matrix.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 服务元数据：服务配置文件将最终被转换为服务元数据实例，这里维护了：
 * <ul>
 * <li>服务的名称、分组，服务属性
 * <li>对服务提供端：服务被对外发布的协议，及相关属性
 * <li>对服务消费端：服务调用协议，及相关属性
 * </ul>
 * 
 * @author mixueqiang
 * @since Nov 28, 2012
 * 
 */
public class ServiceMetadata implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_GROUP = "MATRIX";

    private String name;
    private String serviceName;
    private String group;
    private Properties properties = new Properties();

    private Map<String, Properties> exporters = new HashMap<String, Properties>();
    private Map<String, Properties> importers = new HashMap<String, Properties>();

    public ServiceMetadata() {
        this.group = DEFAULT_GROUP;
    }

    public void addExporter(String exportType, Properties properties) {
        this.exporters.put(exportType.toUpperCase(), properties);
    }

    public void addImporter(String importType, Properties properties) {
        this.importers.put(importType.toUpperCase(), properties);
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "Metadata [" + group + "#" + serviceName + "]";
    }

}
