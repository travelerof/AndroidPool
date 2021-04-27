package com.hyg.permission;

import android.provider.Settings;

import androidx.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author 韩永刚
 * @Date 2021/03/22
 * @Desc
 */
class RequestManger {

    private static final RequestManger requestManger = new RequestManger();

    private final LinkedBlockingQueue<IRequest> mRequestQuene;
    private boolean isRequest;
    private RequestManger() {
        mRequestQuene = new LinkedBlockingQueue<>();
    }

    public static RequestManger getInstance() {
        return requestManger;
    }

    public boolean add(@NonNull IRequest request) {
        return requestManger.mRequestQuene.offer(request);
    }

    public boolean hasNext() {
        return !requestManger.mRequestQuene.isEmpty();
    }

    public IRequest next() {
        return requestManger.mRequestQuene.poll();
    }

    public boolean isRequest(){
        return isRequest;
    }
    public void clear(){
        requestManger.mRequestQuene.clear();
    }
    public void requestStatus(boolean isRequest){
        this.isRequest = isRequest;
    }

    public void request(){
        if (isRequest || !hasNext()) {
            return;
        }
        isRequest = true;
        IRequest req = next();
        req.requst();
    }
}
