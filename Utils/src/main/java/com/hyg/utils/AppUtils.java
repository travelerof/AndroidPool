package com.hyg.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author 韩永刚
 * @Data 2021/01/26
 * @Desc app相关工具类
 */
public class AppUtils {

    public static String getSHA1(Context context){
        return null;
    }

    private static PackageInfo getPackageInfo(Context context, int flags){

        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(getPackageName(context), flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static String getSignSHA(Context context, String shaType){
        PackageInfo packageInfo = getPackageInfo(context,PackageManager.GET_ACTIVITIES);
        if (packageInfo == null) {
            return null;
        }
        byte[] cert = packageInfo.signatures[0].toByteArray();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(shaType);
            byte[] signByteArray = messageDigest.digest(cert);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getPackageName(Context context){
        return context.getPackageName();
    }

    /**
     * 获取app版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context){
        return getPackageInfo(context,0).versionCode;
    }

    public static String getVersionName(Context context){
        return getPackageInfo(context,0).versionName;
    }

    public static int getUid(Context context){
        ApplicationInfo applicationInfo = getApplicationInfo(context);
        if (applicationInfo == null) {
            return -1;
        }
        return applicationInfo.uid;
    }

    public static ApplicationInfo getApplicationInfo(Context context){
        PackageInfo packageInfo = getPackageInfo(context, 0);
        if (packageInfo != null) {
            return packageInfo.applicationInfo;
        }
        return null;
    }

}
