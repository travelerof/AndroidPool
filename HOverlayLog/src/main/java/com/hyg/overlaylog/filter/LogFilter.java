package com.hyg.overlaylog.filter;

import android.os.Handler;
import android.widget.Filter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.hyg.overlaylog.log.LogDataManager;
import com.hyg.overlaylog.log.LogModel;

import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/05/23
 * @Desc
 */
public final class LogFilter extends Filter {

    /**
     * 关键字过滤
     */
    private KeywordFilter mKeywordFilter = new KeywordFilter();
    /**
     * 日志等级过滤
     */
    private PriorityFilter mPriorityFilter = new PriorityFilter();
    /**
     * 日志结果观察者
     */
    private MutableLiveData<List<LogModel>> mLiveData = new MutableLiveData<>();


    /**
     * 设置观察者
     * @param observer
     */
    public void addObserve(Observer<List<LogModel>> observer){
        mLiveData.observeForever(observer);
    }

    /**
     * 开启过滤
     */
    private void filter(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                filter();
            }
        },50);
        filter(null);
    }

    /**
     * 设置日志等级
     *
     * @param priority
     */
    public void setPriority(int priority) {
        mPriorityFilter.setPriority(priority);
        filter();
    }

    /**
     * 设置关键字
     *
     * @param keyword
     */
    public void setKeyword(String keyword){
        mKeywordFilter.setKeyword(keyword);
        filter();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        List<LogModel> logData = LogDataManager.getInstance().getLogData();
        logData = mPriorityFilter.filter(logData);
        logData = mKeywordFilter.filter(logData);
        FilterResults filterResults = new FilterResults();
        filterResults.values = logData;
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        try {
            List<LogModel> list = (List<LogModel>) results.values;
            mLiveData.postValue(list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
