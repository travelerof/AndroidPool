package com.hyg.hpermission;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.hyg.hpermission.permission.Permission;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
public class HPermissionUtils {

    public static boolean hasOverlayPermission(@NonNull Context context) {
        int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.O) {//8.0及以上
            AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (appOpsMgr == null)
                return false;
            int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                    .getPackageName());
            return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
        }else if (version >= Build.VERSION_CODES.M){//6.0及以上
            return Settings.canDrawOverlays(context);
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
            default:
                return -1;
        }
    }
}
