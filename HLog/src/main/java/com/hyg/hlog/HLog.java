package com.hyg.hlog;

import android.util.Log;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc
 */
public final class HLog {

    private static boolean debug = false;
    private static IPrinter iPrinter;

    private static final Object LOCK = new Object();

    public static void debug(boolean debug) {
        debug(debug, null);
    }

    public static void debug(boolean debug, IPrinter printer) {
        HLog.debug = debug;
        if (debug) {
            HLog.iPrinter = printer;
        }
    }

    public static int i(String tag, String message) {
        return i(tag, message, null);
    }

    public static int i(String tag, String message, Throwable tr) {
        return println(Log.INFO, tag, message, tr);
    }

    public static int d(String tag, String message) {
        return d(tag, message, null);
    }

    public static int d(String tag, String message, Throwable tr) {
        return println(Log.DEBUG, tag, message, tr);
    }

    public static int e(String tag, String message) {
        return e(tag, message, null);
    }

    public static int e(String tag, String message, Throwable tr) {
        return println(Log.ERROR, tag, message, tr);
    }

    public static int w(String tag, String message) {
        return w(tag, message, null);
    }

    public static int w(String tag, String message, Throwable tr) {
        return println(Log.WARN, tag, message, tr);
    }

    public static int v(String tag, String message) {
        return v(tag, message, null);
    }

    public static int v(String tag, String message, Throwable tr) {
        return println(Log.VERBOSE, tag, message, tr);
    }

    public static int json(String tag, String message) {

        return w(tag, message);
    }

    public static int println(int priority, String tag, String message, Throwable tr) {
        if (!debug) {
            return -1;
        }
        synchronized (LOCK) {
            message += "\n" + Log.getStackTraceString(tr);
            if (iPrinter != null) {
                iPrinter.println(priority, tag, message);
            }
            return Log.println(priority, tag, message);
        }

    }
}
