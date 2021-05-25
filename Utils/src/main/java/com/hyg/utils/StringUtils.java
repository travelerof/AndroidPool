package com.hyg.utils;

/**
 * @Author 韩永刚
 * @Date 2021/02/10
 * @Desc
 */
public class StringUtils {

    public static boolean isEmpty(String text) {
        return text == null || "".equals(text);
    }

    public static boolean equal(String eq1, String eq2) {
        if (eq1 == null) {
            throw new NullPointerException("params\"eq1\" is not null!");
        }
        return eq1.equals(eq2);
    }
}
