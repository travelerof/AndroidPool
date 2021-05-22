package com.hyg.overlaylog.log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

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
    private LiveData<List<LogModel>> mLiveData;

    private LogDataManager() {
//        mLiveData = new LiveData<List<LogModel>>() {
//            @Override
//            public void observeForever(@NonNull @NotNull Observer<? super List<LogModel>> observer) {
//                super.observeForever(observer);
//
//            }
//        }
//        mLiveData = new ArrayList<>();
    }

    public static LogDataManager getInstance() {
        return manager;
    }

    public void add(@NonNull LogModel model) {
        checked();
//        logData.add(model);
//        mListLiveData.observeForever(new Observer<List<LogModel>>() {
//            @Override
//            public void onChanged(List<LogModel> logModels) {
//
//            }
//        });
    }

    public void clear() {
//        logData.clear();
    }

    public void addObserver(Observer<List<LogModel>> observer){

    }
    /**
     * 检查存储的list是否超过了最大值
     */
    private void checked() {
//        if (logData.size() > MAX_SIZE) {
//            int endIndex = logData.size() - MAX_SIZE;
//            if (endIndex < 0){
//                endIndex = 0;
//            }
//            List<LogModel> logs = logData.subList(0, endIndex);
//            clear();
//            if (logs != null) {
//                logData.addAll(logs);
//            }
//        }
    }

}
