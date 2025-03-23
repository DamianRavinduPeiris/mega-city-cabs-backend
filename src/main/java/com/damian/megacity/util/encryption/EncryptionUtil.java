package com.damian.megacity.util.encryption;

import lombok.extern.java.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
@Log
public class EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final byte[] STATIC_KEY = "12345678901234567890123456789012".getBytes();

    private EncryptionUtil() {
    }

    private static SecretKey getSecretKey() {
        return new SecretKeySpec(STATIC_KEY, ALGORITHM);
    }

    public static String encrypt(String data) {
        try{
            var cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (Exception e){
            log.warning(e.getMessage());
        }
        return null;
    }
}
