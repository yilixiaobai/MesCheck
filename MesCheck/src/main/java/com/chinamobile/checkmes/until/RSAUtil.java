package com.chinamobile.checkmes.until;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    public static final int ENCRYPT_MODE = Cipher.ENCRYPT_MODE;

    public static final int DECRYPT_MODE = Cipher.DECRYPT_MODE;

    public static final Charset UTF8 = Charset.forName("UTF-8");

    private static final String ALGORITHM = "RSA";
    private static final String pubkey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAM59NlOY5ldDMojxDE4wvIJoaqHJ1D1QCX7sM/2RUpQedqxiCeO8w3OY7GbM5fFXlYjkySINX4NpT59K9i9MfN0CAwEAAQ==";
    private static final String prikey="MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAzn02U5jmV0MyiPEMTjC8gmhqocnUPVAJfuwz/ZFSlB52rGIJ47zDc5jsZszl8VeViOTJIg1fg2lPn0r2L0x83QIDAQABAkAVNSD+RwbpUXqAPBmicjj6P9Ur1rnYrj67bXENiv5rPeJ91PcadLeIyceM7MIU5XYj8czXheLgGCKNdTy/JOJ1AiEA7BOxYXyhHnj15VvAKIQJoeFGbOzApMWT1hn7OZQXyDsCIQDf6kqeJ+ds7/AICxN4tuprBlxrS4V1ouf43uMbVPkVxwIhAIEwZamLokAiwa25//YsC3li5j9FcEa7OKO5pBlmSX1xAiB6PWvS/7Hl1O9TVMiihClhmC1oHeiUGgVN5lBRoYgCDQIhAJ3rO0YsjU+DZrA+WB2N3NiYQna+2C9n0hXtZj1gHDvA";
    private Cipher cipher;
    private static Key getEncKey(String encKey){
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(encKey.getBytes(UTF8)));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    private static Key getDecKey(String decKey){
        PrivateKey privateKey = null;
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(decKey.getBytes(UTF8)));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {

            e.printStackTrace();
        }
        return privateKey;
    }
    public boolean init(int mode, String key){
        //参数校验code
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (NoSuchPaddingException e1) {
            e1.printStackTrace();
        }
        Key secKey = null;
        //靠mode判断是加密还是解密
        if (mode == ENCRYPT_MODE) {
            secKey = getEncKey(key);
        }
        else if (mode == DECRYPT_MODE) {
            secKey = getDecKey(key);
        }
        if (secKey == null) {
            return false;
        }
        try {
            cipher.init(mode, secKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public String encrypt(String text){
        byte[] data = null;
        try {
            data = cipher.doFinal(text.getBytes(UTF8));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return new String(Base64.getEncoder().encode(data), UTF8);
    }

    public String decrypt(String text){
        byte[] data = null;
        try {
            data = cipher.doFinal(Base64.getDecoder().decode(text.getBytes(UTF8)));
        } catch (IllegalBlockSizeException e) {

            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return new String(data, UTF8);
    }


    public  static String  deCode(String secret){
        RSAUtil rsaUtils = new RSAUtil();
        rsaUtils.init(Cipher.DECRYPT_MODE, prikey);
        return rsaUtils.decrypt(secret);
    }
    public  static String  enCode(String secret){
        RSAUtil rsaUtils = new RSAUtil();
        rsaUtils.init(Cipher.ENCRYPT_MODE, pubkey);
        return rsaUtils.encrypt(secret);
    }

    public static void main(String[] args) {
        String q = "0123456789!qwe@3as";
        RSAUtil rsaUtils = new RSAUtil();
        rsaUtils.init(Cipher.ENCRYPT_MODE, pubkey);
        //得到密文
        String cipherText = rsaUtils.encrypt(q);
       System.out.println("cipherText: " + cipherText);
        rsaUtils.init(Cipher.DECRYPT_MODE, prikey);
        //得到明文
        String plaintext = rsaUtils.decrypt(cipherText);
     //   System.out.println("plaintext: " + plaintext);
    }
}
