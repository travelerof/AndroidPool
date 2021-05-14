package com.hyg.hvideo;

import android.util.Log;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public class HVideoLog {
    public static boolean debug = true;

    public static void debug(boolean debug) {
        HVideoLog.debug = debug;
    }

    public static int i(String tag, String message) {
        return debug ? Log.i(tag, message) : -1;
    }

    public static int e(String tag, String message) {
        return debug ? Log.e(tag, message) : -1;
    }

    public static int w(String tag, String message) {
        return debug ? Log.w(tag, message) : -1;
    }

    public static int d(String tag, String message) {
        return debug ? Log.d(tag, message) : -1;
    }
}
