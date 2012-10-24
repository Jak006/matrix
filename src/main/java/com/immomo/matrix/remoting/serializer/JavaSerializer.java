package com.immomo.matrix.remoting.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.immomo.matrix.remoting.Serializer;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class JavaSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.writeObject(object);
            return baos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws Exception {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
    }
}
