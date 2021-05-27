package com.hyg.hpermission.request;

import android.content.Context;

import androidx.annotation.NonNull;

import com.hyg.hpermission.OnPermissionCallback;

import java.util.Arrays;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 请求参数配置
 */
public final class RequestBuilder {
    private Context mContext;
    private RequestOptions mOptions;

    public RequestBuilder(Context context) {
        mContext = context;
        mOptions = new RequestOptions();
    }

    /**
     * 本次请求类型 {@link RequestType}
     * @param requestType
     * @return
     */
    public RequestBuilder requestType(@RequestType int requestType) {
        mOptions.requestType = requestType;
        return this;
    }

    /**
     * 请求码
     *
     * @param requestCode
     * @return
     */
    public RequestBuilder requestCode(int requestCode) {
        mOptions.requestCode = requestCode;
        return this;
    }

    /**
     * 申请的权限
     * @param permission
     * @return
     */
    public RequestBuilder permission(@NonNull String permission) {
        mOptions.permissions.add(permission);
        return this;
    }

    /**
     * 设置申请权限
     *
     * @param permission
     * @param args
     * @return
     */
    public RequestBuilder permission(@NonNull String permission, String... args) {
        mOptions.permissions.add(permission);
        if (args != null) {
            mOptions.permissions.addAll(Arrays.asList(args));
        }
        return this;
    }

    public RequestBuilder permissions(@NonNull List<String> permissions) {
        mOptions.permissions.addAll(permissions);
        return this;
    }

    /**
     * 申请结果监听
     *
     * @param onPermissionCallback
     * @return
     */
    public RequestBuilder callBack(OnPermissionCallback onPermissionCallback) {
        mOptions.mOnPermissionCallback = onPermissionCallback;
        return this;
    }

    public void request() {
        IRequest request;
        if (mOptions.requestType == RequestType.SPECIAL_PERMISSION) {
            request = new SpecialRequest(mContext, mOptions);
        } else {
            request = new Request(mContext, mOptions);
        }
        //添加本次请求，并开始
        RequestManager.getInstance().add(request);
        RequestManager.getInstance().request();
    }

}
