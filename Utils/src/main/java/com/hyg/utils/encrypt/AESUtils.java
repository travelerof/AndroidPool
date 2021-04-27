package com.hyg.utils.encrypt;

import com.hyg.utils.Base64Utils;
import com.hyg.utils.StringUtils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author 韩永刚
 * @Date 2021/03/04
 * @Desc AES对称加密
 */
public class AESUtils {

    /**
     * 默认加密key
     */
    private static final String DEFAULT_SECRETKEY = "fgTv5a3c56I0P1zlbDNRViOCgYQBy5R0zis7oMwEuuU=";

    private AESUtils() {
    }

    public static String getDefaultSecretkey() {
        return DEFAULT_SECRETKEY;
    }

    /**
     * 随机创建key
     *
     * @return
     */
    public static String createSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64Utils.encode(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKeySpec createSecretKey(String secretKey) {
        return new SecretKeySpec(Base64Utils.decode(secretKey), "AES");
    }

    private static Cipher initCipher(final String secretKey, final int opmode) {
        if (StringUtils.isEmpty(secretKey)) {
            return null;
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(opmode, createSecretKey(secretKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipher;
    }

    /**
     * @param srcBytes
     * @param secretKey
     * @return
     */
    public static byte[] encryptAES(final byte[] srcBytes, final String secretKey) {
        if (srcBytes == null) {
            return null;
        }
        Cipher cipher = initCipher(secretKey, Cipher.ENCRYPT_MODE);
        if (cipher != null) {
            try {
                return cipher.doFinal(srcBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param srcText
     * @param secretKey
     * @return
     */
    public static String encryptAES(String srcText, String secretKey) {
        byte[] bytes = encryptAES(srcText.getBytes(), secretKey);
        if (bytes == null) {
            return null;
        }
        return Base64Utils.encode(bytes);
    }

    public static byte[] decryptAES(byte[] encryptBytes, String secretKey) {
        if (encryptBytes == null) {
            return null;
        }
        Cipher cipher = initCipher(secretKey, Cipher.DECRYPT_MODE);
        if (cipher != null) {
            try {
                return cipher.doFinal(encryptBytes);
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String decryptAES(String encryptText, String secretKey) {
        byte[] decode = Base64Utils.decode(encryptText);
        if (decode == null) {
            return null;
        }
        byte[] bytes = decryptAES(decode, secretKey);
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }
}
