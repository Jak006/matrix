package com.immomo.matrix.serializer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.immomo.matrix.Serializer;

/**
 * @author mixueqiang
 * @since Nov 28, 2012
 * 
 */
public class SerializerFactory {

    private static Map<String, Serializer> serializers = new HashMap<String, Serializer>();

    static {
        SerializerFactory.registerSerializer("HESSIAN", new HessianSerializer());
        SerializerFactory.registerSerializer("JAVA", new HessianSerializer());
    }

    /**
     * Get a serializer.
     */
    public static Serializer getSerializer(String type) {
        return SerializerFactory.serializers.get(type.toUpperCase());
    }

    /**
     * Get all supported serializer types.
     */
    public static Collection<String> getSupportedSerizlier() {
        return SerializerFactory.serializers.keySet();
    }

    /**
     * Register a custom serializer.
     */
    public static void registerSerializer(String type, Serializer serializer) {
        SerializerFactory.serializers.put(type, serializer);
    }

}
