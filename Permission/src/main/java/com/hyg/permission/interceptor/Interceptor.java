package com.hyg.permission.interceptor;

/**
 * @Author 韩永刚
 * @Date 2021/03/22
 * @Desc
 */
public interface Interceptor {

    PRequest intercept(PRequest request);
}
