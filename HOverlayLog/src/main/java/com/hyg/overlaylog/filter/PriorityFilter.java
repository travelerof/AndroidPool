package com.hyg.overlaylog.filter;

import android.util.Log;
import android.widget.Filter;

import androidx.annotation.NonNull;

import com.hyg.overlaylog.log.LogDataManager;
import com.hyg.overlaylog.log.LogModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/05/23
 * @Desc
 */
class PriorityFilter implements IFilter<LogModel> {
    private int priority = Log.VERBOSE;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @NonNull
    @NotNull
    @Override
    public List<LogModel> filter(@NonNull @NotNull List<LogModel> list) {
        List<LogModel> logs = new ArrayList<>();
        for (LogModel logModel : list) {
            if (logModel.priority >= priority) {
                logs.add(logModel);
            }
        }
        return logs;
    }
}
