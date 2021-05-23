package com.hyg.overlaylog.log;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.hyg.overlaylog.filter.LogFilter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
public class LogDataManager {

    /**
     * 最大值
     */
    public static final int MAX_SIZE = 500;
    public static LogDataManager manager = new LogDataManager();
    private List<LogModel> logData;
    private LogFilter mLogFilter;

    private LogDataManager() {
        logData = new ArrayList<>();
        mLogFilter = new LogFilter();
    }

    public static LogDataManager getInstance() {
        return manager;
    }

    public void add(@NonNull LogModel model) {
        checked();
        logData.add(model);
        mLogFilter.filter();
    }

    /**
     * 设置观察者
     *
     * @param observer
     */
    public void addObserve(Observer<List<LogModel>> observer) {
        mLogFilter.addObserve(observer);
    }

    public void setKeyword(String keyword) {
        mLogFilter.setKeyword(keyword);
    }

    public void setPriority(int priority) {
        mLogFilter.setPriority(priority);
    }

    public List<LogModel> getLogData() {
        return logData;
    }

    public void clear() {
        logData.clear();
        mLogFilter.filter();
    }

    /**
     * 检查存储的list是否超过了最大值
     */
    private void checked() {
        if (logData.size() > MAX_SIZE) {
            int endIndex = logData.size() - MAX_SIZE;
            if (endIndex < 0) {
                endIndex = 0;
            }
            List<LogModel> logs = logData.subList(0, endIndex);
            clear();
            if (logs != null) {
                logData.addAll(logs);
            }
        }
    }

}
