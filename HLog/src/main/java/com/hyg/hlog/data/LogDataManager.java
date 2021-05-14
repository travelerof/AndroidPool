package com.hyg.hlog.data;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc
 */
public class LogDataManager {
    private static LogDataManager manager;
    private List<LogModel> mLogs;
    private final ThreadPoolExecutor mExecutor;
    private List<OnLogChangedListener> mListeners;

    private LogDataManager() {
        mLogs = new ArrayList<>();
        mListeners = new ArrayList<>();
        mExecutor = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
    }

    public static LogDataManager getInstance() {
        if (manager == null) {
            synchronized (LogDataManager.class) {
                if (manager == null) {
                    manager = new LogDataManager();
                }
            }
        }
        return manager;
    }

    public void add(@Nullable LogModel model) {
        mLogs.add(model);
    }

    public void clear() {
        mLogs.clear();
    }

    public void find(String keyword) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void addListener(OnLogChangedListener onLogChangedListener) {
        mListeners.add(onLogChangedListener);
    }

    public void removeListener(OnLogChangedListener onLogChangedListener) {
        mListeners.remove(onLogChangedListener);
    }

    public void clearListener() {
        mListeners.clear();
    }
}
