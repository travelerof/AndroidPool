package com.hyg.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @Author 韩永刚
 * @Date 2021/02/22
 * @Desc base64编解码
 */
public class Base64Utils {

    /**
     * 默认编码格式
     */
    public static final String DEFAULT_CHARSET = "utf-8";
    public static final int DEFAULT_FLAGS = Base64.NO_WRAP;

    /**
     * 编码 String——>String
     * @param text
     * @return
     */
    public static String encodeToString(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        try {
            Base64.encodeToString(text.getBytes(DEFAULT_CHARSET), DEFAULT_FLAGS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(byte[] bytes){
        if (bytes == null) {
            return null;
        }
        return Base64.encodeToString(bytes,DEFAULT_FLAGS);
    }
    /**
     * 编码 String——>byte[]
     * @param text
     * @return
     */
    public static byte[] encode(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            return encode(text.getBytes(DEFAULT_CHARSET), DEFAULT_FLAGS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解码 String——>byte[]
     * @param encodeText
     * @return
     */
    public static byte[] decode(String encodeText) {
        if (StringUtils.isEmpty(encodeText)) {
            return null;
        }
        return Base64.decode(encodeText, DEFAULT_FLAGS);
    }

    /**
     * 解码 String ——> String
     * @param encodeText
     * @return
     */
    public static String decodeToString(String encodeText) {
        byte[] decode = decode(encodeText);
        if (decode != null) {
            try {
                return new String(decode, DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 编码
     *
     * @param src
     * @param flags
     * @return
     */
    public static byte[] encode(byte[] src, int flags) {
        return Base64.encode(src, flags);
    }

    /**
     * 解码
     * @param encodeBytes
     * @param flags
     * @return
     */
    public static byte[] decode(byte[] encodeBytes, int flags) {
        return Base64.decode(encodeBytes, flags);
    }
}
