package com.hyg.hlog;

import android.content.ContentProvider;

import androidx.core.content.FileProvider;

import java.security.Provider;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc
 */
public class HLogManager extends FileProvider {
    @Override
    public boolean onCreate() {
        return super.onCreate();
    }
}
