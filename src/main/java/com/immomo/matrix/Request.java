package com.immomo.matrix;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String KEY_VERSION = "_version";
    public static final String KEY_GROUP = "_group";

    private String serviceName;
    private String methodName;
    private String[] methodArgSigs;
    private Object[] methodArgs;
    private Map<String, Object> properties;

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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String toString() {
        return "Request [serviceName=" + serviceName + ", methodName=" + methodName + ", methodArgs="
                + Arrays.toString(methodArgs) + "]";
    }

}
