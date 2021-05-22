package com.hyg.hpermission.request;

import android.content.Context;

import androidx.annotation.NonNull;

import com.hyg.hpermission.OnPermissionCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
final class RequestOptions {
    /**
     * 权限请求类型
     */
    @RequestType
    public int requestType = RequestType.NORMAL_PERMISSION;
    /**
     * 权限列表
     */
    public List<String> permissions = new ArrayList<>();
    /**
     * 请求code码
     */
    public int requestCode = 100;

    /**
     * 请求回调
     */
    public OnPermissionCallback mOnPermissionCallback;

}
