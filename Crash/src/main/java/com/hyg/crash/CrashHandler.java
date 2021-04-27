package com.hyg.crash;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.hyg.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/03/30
 * @Desc 崩溃日志收集
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final int READ_CRASH = -1010;

    private static CrashHandler crashHandler;

    private Context mContext;
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    private String mLocationPath;
    private CrashReadHandler mCrashReadHandler;

    private CrashHandler() {
        throw new CrashException("不能使用该构造方法");
    }

    private CrashHandler(Context context, String localPath, OnCrashFileNameListener onCrashFileNameListener) {
        mContext = context;
        mLocationPath = localPath;
        if (onCrashFileNameListener != null) {
            mCrashReadHandler = new CrashReadHandler(onCrashFileNameListener);
        }
        if (TextUtils.isEmpty(mLocationPath)) {
            mLocationPath = getDefaultDir().getAbsolutePath();
        }
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        readFile();
    }

    public static void init(Application application) {
        init(application, null, null);
    }

    public static void init(Application application, String localPath) {
        init(application, localPath, null);
    }

    public static void init(Application application, OnCrashFileNameListener onCrashFileNameListener) {
        init(application, null, onCrashFileNameListener);
    }

    public static void init(Application application, String localPath, OnCrashFileNameListener onCrashFileNameListener) {
        if (crashHandler == null) {
            crashHandler = new CrashHandler(application, localPath, onCrashFileNameListener);
        }
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        dumpExceptionInfo(e);
        if (mUncaughtExceptionHandler != null) {
            mUncaughtExceptionHandler.uncaughtException(t, e);
            return;
        }
        System.exit(0);
    }

    private void dumpExceptionInfo(Throwable e) {
        File file = createExceptionFile();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            e.printStackTrace(pw);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    private File getDefaultDir() {
        File file = new File(mContext.getCacheDir(), "crash");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private File createExceptionFile() {
        long systemTime = System.currentTimeMillis();
        File file = new File(mLocationPath, systemTime + ".track");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private void readFile() {
        if (mCrashReadHandler == null) {
            return;
        }
        new Thread(() -> {
            Message msg = Message.obtain();
            msg.what = READ_CRASH;
            msg.obj = FileUtils.readFileFormDir(mLocationPath);
            mCrashReadHandler.sendMessage(msg);
        }).start();
    }

    private static class CrashReadHandler extends Handler {
        private OnCrashFileNameListener mOnCrashFileNameListener;

        public CrashReadHandler(OnCrashFileNameListener onCrashFileNameListener) {
            mOnCrashFileNameListener = onCrashFileNameListener;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what != READ_CRASH) {
                return;
            }
            mOnCrashFileNameListener.onList((List<String>) msg.obj);
        }
    }
}
