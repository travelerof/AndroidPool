package com.hyg.hlog;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc 日志打印
 */
public final class HLog {
    /**
     * 默认tag
     */
    private static final String TAG = HLog.class.getSimpleName();
    /**
     * 打印json开始
     */
    private static final String START_LINE = "╔═══════════════════════════════════════════════════════════════════════════════════════";
    /**
     * 打印json结束
     */
    private static final String END_LINE = "╚═══════════════════════════════════════════════════════════════════════════════════════";
    private static final String CENTER = "║ ";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
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

    public static int i(@NonNull String message) {
        return i(TAG, message);
    }

    public static int i(@NonNull String tag, @NonNull String message) {
        return i(tag, message, null);
    }

    public static int i(@NonNull String tag, @NonNull String message, Throwable tr) {
        return println(Log.INFO, tag, message, tr);
    }

    public static int d(@NonNull String message) {
        return d(TAG, message);
    }

    public static int d(@NonNull String tag, @NonNull String message) {
        return d(tag, message, null);
    }

    public static int d(@NonNull String tag, @NonNull String message, Throwable tr) {
        return println(Log.DEBUG, tag, message, tr);
    }

    public static int e(@NonNull String message) {
        return e(TAG, message);
    }

    public static int e(@NonNull String tag, @NonNull String message) {
        return e(tag, message, null);
    }

    public static int e(@NonNull String tag, @NonNull String message, Throwable tr) {
        return println(Log.ERROR, tag, message, tr);
    }

    public static int w(@NonNull String message) {
        return w(TAG, message);
    }

    public static int w(@NonNull String tag, @NonNull String message) {
        return w(tag, message, null);
    }

    public static int w(@NonNull String tag, @NonNull String message, Throwable tr) {
        return println(Log.WARN, tag, message, tr);
    }

    public static int v(@NonNull String message) {
        return v(TAG, message);
    }

    public static int v(String tag, String message) {
        return v(tag, message, null);
    }

    public static int v(String tag, String message, Throwable tr) {
        return println(Log.VERBOSE, tag, message, tr);
    }

    public static int json(@NonNull String message) {
        return json(TAG, message);
    }

    public static int json(@NonNull String tag, @NonNull String message) {
        if (!debug) {
            return -1;
        }
        try {
            if (message.startsWith("{")) {
                JSONObject object = new JSONObject(message);
                message = object.toString(4);
            } else if (message.startsWith("[")) {
                JSONArray array = new JSONArray(message);
                message = array.toString(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        print(Log.WARN, tag, message);
        Log.w(tag, START_LINE);
        message = "json:" + LINE_SEPARATOR + message;
        String[] split = message.split(LINE_SEPARATOR);
        for (String s : split) {
            Log.w(tag, CENTER + s);
        }
        Log.w(tag, END_LINE);
        return 0;
    }

    public static int println(int priority, @NonNull String tag, @NonNull String message, Throwable tr) {
        if (!debug) {
            return -1;
        }
        synchronized (LOCK) {
            message += "\n" + Log.getStackTraceString(tr);
            print(priority, tag, message);
            return Log.println(priority, tag, message);
        }

    }

    private static void print(int priority, @NonNull String tag, @NonNull String message) {
        if (iPrinter != null) {
            iPrinter.println(priority, tag, message);
        }
    }
}
