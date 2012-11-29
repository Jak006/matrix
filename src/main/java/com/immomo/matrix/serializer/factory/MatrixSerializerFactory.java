package com.immomo.matrix.serializer.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.immomo.matrix.serializer.HessianSerializer;
import com.immomo.matrix.serializer.Serializer;

/**
 * @author mixueqiang
 * @since Nov 28, 2012
 * 
 */
public class MatrixSerializerFactory {

    private static Map<String, Serializer> serializers = new HashMap<String, Serializer>();

    static {
        MatrixSerializerFactory.registerSerializer("HESSIAN", new HessianSerializer());
        MatrixSerializerFactory.registerSerializer("JAVA", new HessianSerializer());
    }

    /**
     * Get a serializer.
     */
    public static Serializer getSerializer(String type) {
        return MatrixSerializerFactory.serializers.get(type.toUpperCase());
    }

    /**
     * Get all supported serializer types.
     */
    public static Collection<String> getSupportedSerizlier() {
        return MatrixSerializerFactory.serializers.keySet();
    }

    /**
     * Register a custom serializer.
     */
    public static void registerSerializer(String type, Serializer serializer) {
        MatrixSerializerFactory.serializers.put(type.toUpperCase(), serializer);
    }

}
