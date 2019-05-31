package com.example.demo.Encryption;

import org.bson.BSONCallback;
import org.bson.BSONObject;
import org.bson.types.Binary;

import java.io.IOException;
import java.util.function.Function;

import static com.example.demo.Service.DecryptService.decryptDirect;
import static org.bson.BSON.decode;


public class Decoder extends BasicDBObjectCallback implements Function<Object, Object> {

    public Object apply(Object o) {
        byte[] data;

        if (o instanceof Binary) data = ((Binary) o).getData();
        else if (o instanceof byte[]) data = (byte[]) o;
        else throw new IllegalStateException("Got " + o.getClass() + ", expected: Binary or byte[]");

        try {
            byte[] serialized = decryptDirect((data));
            //BSONCallback bsonCallback = new BasicDBObjectCallback();
            BSONObject deserialized = decode(serialized);
           // return deserialized;
            return deserialized.get("");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
