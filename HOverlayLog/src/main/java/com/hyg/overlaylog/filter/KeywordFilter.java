package com.hyg.overlaylog.filter;

import android.text.TextUtils;
import android.widget.Filter;

import androidx.annotation.NonNull;

import com.hyg.overlaylog.log.LogDataManager;
import com.hyg.overlaylog.log.LogModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/05/23
 * @Desc
 */
class KeywordFilter implements IFilter<LogModel> {

    private String keyword = "";

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @NonNull
    @NotNull
    @Override
    public List<LogModel> filter(@NonNull @NotNull List<LogModel> list) {
        if (TextUtils.isEmpty(keyword)) {
            return list;
        }
        List<LogModel> logs = new ArrayList<>();
        for (LogModel logDatum : list) {
            if (logDatum.tag.contains(keyword)) {
                logs.add(logDatum);
            }
        }
        return logs;
    }
}
