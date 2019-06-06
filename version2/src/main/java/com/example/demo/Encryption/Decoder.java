package com.example.demo.Encryption;

import org.bson.BasicBSONDecoder;
import org.bson.types.Binary;

import java.util.function.Function;

import static com.example.demo.Encryption.Crypt.isInteger;
import static com.example.demo.Encryption.Crypt.isPrimitiveStringWrapped;
import static com.example.demo.Service.DecryptService.decryptDirect;


public class Decoder extends BasicBSONDecoder implements Function<Object, Object> {

    public Object apply(Object o) {
        byte[] data;

        if (o instanceof Binary) data = ((Binary) o).getData();
        else if (o instanceof byte[]) data = (byte[]) o;
        else if(isInteger((String) o)){
       //else if(isPrimitiveStringWrapped(o.getClass())) {
            //for now only integer case is assumed when using original encryption take care of this
            int i=(Integer.parseInt((String) o))/2;
            //String s=String.valueOf(i);
            return i;
        }
        else if(o instanceof String) data=((String)o).getBytes();
        else throw new IllegalStateException("Got " + o.getClass() + ", expected: Binary or byte[]");

        try {
            byte[] serialized = decryptDirect((data));
            return new String(serialized);
           /* //BSONCallback bsonCallback = new BasicDBObjectCallback();
            BSONObject deserialized = decode(serialized);
           // return deserialized;
            return deserialized.get("");*/
           /* BSONCallback bsonCallback = new BasicDBObjectCallback();
            decode(serialized, bsonCallback);
            BSONObject deserialized = (BSONObject) bsonCallback.get();
            return deserialized.get("");*/

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
