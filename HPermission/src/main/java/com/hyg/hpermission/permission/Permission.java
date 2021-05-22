package com.hyg.hpermission.permission;

import android.Manifest;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author hanyonggang
 * @Date 2021/5/21 0021
 * @Desc 特殊权限，如通知，悬浮窗等
 */
@StringDef({Permission.APPLICATION_WINDOW_OVERLAY})
@Retention(RetentionPolicy.SOURCE)
public @interface Permission {

    /**
     * 悬浮窗权限
     */
    String APPLICATION_WINDOW_OVERLAY = Manifest.permission.SYSTEM_ALERT_WINDOW;

}
