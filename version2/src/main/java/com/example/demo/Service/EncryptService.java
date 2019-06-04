package com.example.demo.Service;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EncryptService {
    public static byte[] encryptDirect(byte[] bytes){
        //String string=(String)object;
        byte[] secretKey = Base64.getEncoder().encode(bytes);
        //use the actual encryption function here
        return secretKey;
    }

}
