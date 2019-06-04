package com.example.demo.Encryption;

import org.bson.BasicBSONEncoder;
import org.bson.types.Binary;

import java.util.function.Function;

import static com.example.demo.Encryption.Crypt.isPrimitiveStringWrapped;
import static com.example.demo.Service.EncryptService.encryptDirect;

public class Encoder extends BasicBSONEncoder implements Function<Object, Object> {
    public Object apply(Object o) {
        byte[] data;

        if (o instanceof Binary) data = ((Binary) o).getData();
        else if (o instanceof byte[]) data = (byte[]) o;
        else if(o instanceof String) data=((String)o).getBytes();
        else if(isPrimitiveStringWrapped(o.getClass())) {
            //for now only integer case is assumed when using original encryption take care of this
            int i=((int)o)*2;
            String s=String.valueOf(i);
            return s;
        }
        else throw new IllegalStateException("Got " + o.getClass() + ", expected: Binary or byte[]");
       // byte[] serialized = encode(new BasicBSONObject("", o));
        return new String(encryptDirect(data));
    }
}