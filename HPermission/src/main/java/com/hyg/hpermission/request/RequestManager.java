package com.hyg.hpermission.request;

import androidx.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 请求管理类
 */
final class RequestManager implements OnRequestListener {

    private static RequestManager requestManger = new RequestManager();

    private final LinkedBlockingQueue<IRequest> mRequestQuene;
    private boolean isRequest;

    private RequestManager() {
        mRequestQuene = new LinkedBlockingQueue<>();
    }

    public static RequestManager getInstance() {
        return requestManger;
    }

    public boolean add(@NonNull IRequest request) {
        request.addOnRequestListener(this);
        return mRequestQuene.offer(request);
    }

    public boolean hasNext() {
        return !mRequestQuene.isEmpty();
    }

    public IRequest next() {
        return mRequestQuene.poll();
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void clear() {
        mRequestQuene.clear();
    }

    public void request() {
        if (isRequest || !hasNext()) {
            return;
        }
        isRequest = true;
        IRequest req = next();
        req.request();
    }

    @Override
    public void onComplete() {
        isRequest = false;
        request();
    }
}
