package com.immomo.matrix;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 0L;
    private static final AtomicLong REQUEST_ID = new AtomicLong(0);
    public static final String KEY_VERSION = "_version";
    public static final String KEY_GROUP = "_group";

    private long id;
    private String serviceName;
    private String methodName;
    private String[] methodArgSigs;
    private Object[] methodArgs;
    private Map<String, Object> properties;

    public Request() {
        this.id = REQUEST_ID.getAndIncrement();
    }

    public long getId() {
        return id;
    }

    public Object[] getMethodArgs() {
        return methodArgs;
    }

    public String[] getMethodArgSigs() {
        return methodArgSigs;
    }

    public String getMethodName() {
        return methodName;
    }

    public Map<String, Object> getProperties() {
        if (properties == null) {
            properties = new HashMap<String, Object>();
        }
        return properties;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMethodArgs(Object[] methodArgs) {
        this.methodArgs = methodArgs;
    }

    public void setMethodArgSigs(String[] methodArgSigs) {
        this.methodArgSigs = methodArgSigs;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void setProperty(String key, Object value) {
        getProperties().put(key, value);
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "Request [id=" + id + ", serviceName=" + serviceName + ", methodName=" + methodName + ", methodArgSigs="
                + Arrays.toString(methodArgSigs) + "]";
    }

}
