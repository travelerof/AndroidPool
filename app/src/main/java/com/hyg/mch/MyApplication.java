package com.hyg.mch;

import android.app.Application;

import com.hyg.overlaylog.HOverlayLog;

/**
 * @Author hanyonggang
 * @Date 2021/5/21 0021
 * @Desc
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HOverlayLog.debug(true);
    }
}
