package com.hyg.utils;

/**
 * @Author 韩永刚
 * @Date 2021/02/07
 * @Desc 基本数据结构转换
 */
public class DataUtils {

    /**
     * 转换为int
     * @param value
     * @return
     */
    public static int toInt(String value){
        if (StringUtils.isEmpty(value)) {
            return -1;
        }
        try {
            return Integer.parseInt(value);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 转换为double
     * @param value
     * @return
     */
    public static double toDouble(String value){
        if (StringUtils.isEmpty(value)) {
            return -1;
        }
        try {
            return Double.parseDouble(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
