package com.example.demo.Service;

import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class DecryptService {
    public static byte[] decryptDirect(byte[] secret){
        //byte[] secret=((String)object).getBytes();
        byte[] Key = Base64.getDecoder().decode(secret);
        //String str = new String(Key, StandardCharsets.UTF_8);
        //use the actual decryption function here
        return Key;
    }


}
