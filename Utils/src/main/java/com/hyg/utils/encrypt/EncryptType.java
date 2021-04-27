package com.hyg.utils.encrypt;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author 韩永刚
 * @Date 2021/03/05
 * @Desc
 */
public class EncryptType {
    /**
     * AES对称加密
     */
    public static final int AES = 0;
    /**
     * DES对称加密
     */
    public static final int DES = 1;
    /**
     * RSA非对称加密
     */
    public static final int RSA = 2;
    /**
     * MD5不可逆加密
     */
    public static final int MD5 = 3;

    @IntDef({AES, DES, RSA, MD5})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }
}
