package com.immomo.matrix.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.immomo.matrix.Serializer;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class HessianSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        output.writeObject(object);
        output.flush();
        output.close();

        return baos.toByteArray();
    }

    @Override
    public Object deserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bais);

        return input.readObject();
    }

}
