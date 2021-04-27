package com.hyg.suspendNetwork;

import androidx.annotation.NonNull;

import java.util.HashMap;

/**
 * @Author 韩永刚
 * @Date 2021/03/25
 * @Desc
 */
public class SuspendDataManager {
    private static final int MAX = 200;

    private static SuspendDataManager mDataManager;
    private HashMap<String, SuspendModel> mSuspendData;
    private int count = 0;

    private SuspendDataManager() {
        mSuspendData = new HashMap<>(MAX);
    }

    public static SuspendDataManager getInstance() {
        if (mDataManager == null) {
            synchronized (SuspendDataManager.class) {
                if (mDataManager == null) {
                    mDataManager = new SuspendDataManager();
                }
            }
        }
        return mDataManager;
    }

    private SuspendDataManager create(@NonNull final String key) {
        if (contain(key)) {
            remove(key);
        }

        return mDataManager;
    }

    public void addRequest(String url, String header, String body, long requestTime) {
        if (contain(url)) {
            remove(url);
        }
        SuspendModel model = new SuspendModel();
        model.setUrl(url);
        model.setHeader(header);
        model.setBody(body);
        model.setRequestTime("");
        mSuspendData.put(url,model);
    }

    public void addResponse(String url, String response, long responseTime) {
        if (!contain(url)) {
            return;
        }
        SuspendModel model = mSuspendData.get(url);
        if (model == null) {
            return;
        }
        model.setResponse(response);
        model.setResponseTime("");
    }

    public SuspendModel remove(@NonNull final String key) {
        return mSuspendData.remove(key);
    }

    public boolean contain(@NonNull final String key) {
        return mSuspendData.containsKey(key);
    }

}
