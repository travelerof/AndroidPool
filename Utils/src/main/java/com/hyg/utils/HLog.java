package com.hyg.utils;

import android.util.Log;

/**
 * @Author 韩永刚
 * @Data 2021/01/30
 * @Desc 日志打印
 */
public class HLog {

    private static final String TAG = HLog.class.getSimpleName();

    private static boolean debug = true;

    public static void debug() {
        debug = true;
    }

    public static int i(String message) {
        return debug ? Log.i(TAG, message) : -1;
    }

    public static int i(String tag, String message) {
        return debug ? Log.i(tag, message) : -1;
    }

    public static int i(String tag, String message, Throwable throwable) {
        return debug ? Log.i(tag, message, throwable) : -1;
    }

    public static int w(String message) {
        return debug ? Log.w(TAG, message) : -1;
    }

    public static int w(String tag, String message) {
        return debug ? Log.w(tag, message) : -1;
    }

    public static int w(String tag, String message, Throwable throwable) {
        return debug ? Log.w(tag, message, throwable) : -1;
    }

    public static int d(String message) {
        return debug ? Log.d(TAG, message) : -1;
    }

    public static int d(String tag, String message) {
        return debug ? Log.d(tag, message) : -1;
    }

    public static int d(String tag, String message, Throwable throwable) {
        return debug ? Log.d(tag, message, throwable) : -1;
    }

    public static int e(String message) {
        return debug ? Log.e(TAG, message) : -1;
    }

    public static int e(String tag, String message) {
        return debug ? Log.e(tag, message) : -1;
    }

    public static int e(String tag, String message, Throwable throwable) {
        return debug ? Log.e(tag, message, throwable) : -1;
    }

}
