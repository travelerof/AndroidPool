package com.hyg.overlay;

import android.app.Application;

import androidx.core.content.FileProvider;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc 为了监听activity状态
 */
public class RegisterLifecycleProvider extends FileProvider {

    @Override
    public boolean onCreate() {
        LifecycleControl.getInstance().registerActivityLifecyclerCallbacks((Application) getContext().getApplicationContext());
        return super.onCreate();
    }
}
