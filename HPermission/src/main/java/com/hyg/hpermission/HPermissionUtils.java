package com.hyg.hpermission;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.hyg.hlog.HLog;
import com.hyg.hpermission.permission.Permission;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
public class HPermissionUtils {

    public static final String TAG = "Permission";
    private static final String HEADER = "权限>>>>:";

    /**
     * 日志打印
     *
     * @param message
     */
    public static void print(String message) {
        HLog.v(TAG, HEADER + message);
    }

    /**
     * 是否有悬浮窗权限
     *
     * @param context
     * @return
     */
    public static boolean hasOverlayPermission(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//6.0及以上
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    /**
     * 是否与有修改设置权限
     * @param context
     * @return
     */
    public static boolean hasWriteSettingsPermission(@NonNull Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.System.canWrite(context);
        }
        return true;
    }

    /**
     * 检查是否有某个权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkedPermission(@NonNull Context context,@NonNull String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static String getAppName(@NonNull Context context){

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取特殊权限请求描述
     *
     * @param context
     * @param permission
     * @return
     */
    public static String getPermissionDescript(@NonNull Context context, @Permission String permission){
        String appName = HPermissionUtils.getAppName(context);
        switch (permission) {
            case Permission.APPLICATION_WINDOW_OVERLAY:
                return "是否申请“"+appName+"“打开悬浮窗权限?";
            case Permission.APPLICATION_WRITE_SETTINGS:
                return "是否申请“"+appName+"“打开修改设置权限?";
            default:
                return "";
        }
    }

    /**
     * 获取特殊权限标题
     *
     * @param context
     * @param permission
     * @return
     */
    public static int getPermissionTitleResId(@NonNull Context context, @Permission String permission){
        switch (permission) {
            case Permission.APPLICATION_WINDOW_OVERLAY:
                return R.mipmap.ic_overlay;
            case Permission.APPLICATION_WRITE_SETTINGS:
                return R.mipmap.ic_overlay;
            default:
                return -1;
        }
    }
}
