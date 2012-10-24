package com.immomo.matrix.remoting;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public interface Serializer {

    byte[] serialize(Object object) throws Exception;

    Object deserialize(byte[] bytes) throws Exception;

}
