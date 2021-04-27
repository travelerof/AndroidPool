package com.hyg.permission;

import android.content.Context;

import com.hyg.permission.interceptor.DefaultCheckedInterceptor;
import com.hyg.permission.interceptor.Interceptor;
import com.hyg.permission.interceptor.PRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author 韩永刚
 * @Date 2021/03/22
 * @Desc
 */
final class RequestBuilder extends PRequest {
    private List<Interceptor> requestInterceptor;
    /**
     * 请求优先级
     */
    private int priority = 0;

    public RequestBuilder(Context context){
        super(context);
        requestInterceptor = new ArrayList<>();
        requestInterceptor.add(new DefaultCheckedInterceptor());
    }

    public List<Interceptor> getRequestInterceptor() {
        return requestInterceptor;
    }

    public void addRequestInterceptor(Interceptor interceptor) {
        requestInterceptor.add(interceptor);
    }

    public void setRequestInterceptor(List<Interceptor> requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
