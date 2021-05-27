package com.hyg.hpermission.request;

import androidx.annotation.NonNull;

import com.hyg.hpermission.HPermissionUtils;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 请求管理类
 */
public final class RequestManager implements OnRequestListener {

    private static RequestManager requestManger = new RequestManager();
    /**
     * 请求任务队列
     */
    private final LinkedBlockingQueue<IRequest> mRequestQuene;
    /**
     * 当前请求状态
     */
    private boolean isRequest;

    private RequestManager() {
        mRequestQuene = new LinkedBlockingQueue<>();
    }

    public static RequestManager getInstance() {
        return requestManger;
    }

    /**
     * 添加请求
     *
     * @param request
     * @return
     */
    public boolean add(@NonNull IRequest request) {
        HPermissionUtils.print(request.getTag());
        request.addOnRequestListener(this);
        return mRequestQuene.offer(request);
    }

    public boolean hasNext() {
        return !mRequestQuene.isEmpty();
    }

    public IRequest next() {
        return mRequestQuene.poll();
    }

    /**
     * 是否正在执行任务
     * @return
     */
    public boolean isRequest() {
        return isRequest;
    }

    /**
     * 清空所有请求任务
     *
     */
    public void clear() {
        mRequestQuene.clear();
    }

    public void request() {
        if (isRequest || !hasNext()) {
            return;
        }
        isRequest = true;
        IRequest req = next();
        HPermissionUtils.print("执行"+req.getTag());
        req.request();
    }

    @Override
    public void onComplete() {
        isRequest = false;
        request();
    }
}
