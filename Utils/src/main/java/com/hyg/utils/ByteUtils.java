package com.hyg.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Author 韩永刚
 * @Data 2021/01/26
 * @Desc byte相关工具类
 */
public class ByteUtils {

    public static final String DEFAULT_CHARSET_NAME = "utf-8";

    public static byte[] toByteArray(String text, String charset) {
        if (TextUtils.isEmpty(charset)) {
            charset = DEFAULT_CHARSET_NAME;
        }
        try {
            return text.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] toByteArray(String text) {
        return toByteArray(text, DEFAULT_CHARSET_NAME);
    }

    /**
     * byte数组转换为16进制string
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes){
        if (bytes == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer(bytes.length*2);
        String temp = "";
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                temp = "0";
            }
            sb.append(temp);
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] hex2Byte(String text){
        if (StringUtils.isEmpty(text) || text.length() < 2) {
            return new byte[0];
        }
        text = text.toLowerCase();
        int l = text.length()/2;
        byte[] result = new byte[l];
        String temp = "";
        for (int i = 0; i < l; i++) {
            temp = text.substring(i*2,i*2+2);
            result[i] = (byte)(Integer.parseInt(temp,16) & 0xFF);
        }
        return result;
    }

}
