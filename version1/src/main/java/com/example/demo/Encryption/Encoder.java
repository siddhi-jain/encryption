package com.example.demo.Encryption;

import org.bson.BasicBSONEncoder;
import org.bson.BasicBSONObject;
import org.bson.types.Binary;

import java.util.function.Function;

import static com.example.demo.Service.EncryptService.encryptDirect;

public class Encoder extends BasicBSONEncoder implements Function<Object, Object> {
    public Object apply(Object o) {
        byte[] serialized = encode(new BasicBSONObject("", o));
        return new Binary(encryptDirect(serialized));
    }
}