package com.hyg.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @Author 韩永刚
 * @Data 2021/01/26
 * @Desc 分辨率相关工具类
 */
public class DensityUtils {

    public static DisplayMetrics getDisplayMetrics(Context context){
        return context.getResources().getDisplayMetrics();
    }
    /**
     * 获取屏幕分辨率
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        return getDisplayMetrics(context).density;
    }

    /**
     * dp ——> px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2Px(Context context, int dpValue) {
        float density = getDensity(context);
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * px ——> dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2Dip(Context context, int pxValue) {
        float density = getDensity(context);
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 获取屏幕dpi
     *
     * @param context
     * @return
     */
    public static int getDensityDpi(Context context){
        return getDisplayMetrics(context).densityDpi;
    }
}
