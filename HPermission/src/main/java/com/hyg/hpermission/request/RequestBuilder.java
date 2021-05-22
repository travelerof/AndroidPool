package com.hyg.hpermission.request;

import android.content.Context;

import androidx.annotation.NonNull;

import com.hyg.hpermission.OnPermissionCallback;

import java.util.Arrays;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
public final class RequestBuilder {
    private Context mContext;
    private RequestOptions mOptions;

    public RequestBuilder(Context context) {
        mContext = context;
        mOptions = new RequestOptions();
    }

    public RequestBuilder requestType(@RequestType int requestType) {
        mOptions.requestType = requestType;
        return this;
    }

    public RequestBuilder requestCode(int requestCode) {
        mOptions.requestCode = requestCode;
        return this;
    }

    public RequestBuilder permission(@NonNull String permission) {
        mOptions.permissions.add(permission);
        return this;
    }

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
        RequestManager.getInstance().add(request);
        RequestManager.getInstance().request();
    }

}
