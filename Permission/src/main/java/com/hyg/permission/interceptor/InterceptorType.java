package com.hyg.permission.interceptor;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author 韩永刚
 * @Date 2021/03/23
 * @Desc
 */
public final class InterceptorType {

    /**
     * 继续申请权限
     */
    public static final int PERMISSION_CONTINUE_APPLY = 0;
    /**
     * 权限已经申请
     */
    public static final int PERMISSION_APPLIED = 1;
    /**
     * 打断
     */
    public static final int PERMISSION_INTERCEPTOR = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PERMISSION_CONTINUE_APPLY,PERMISSION_APPLIED,PERMISSION_INTERCEPTOR})
    public @interface Type{

    }
}
