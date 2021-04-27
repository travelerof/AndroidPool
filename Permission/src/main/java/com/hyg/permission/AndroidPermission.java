package com.hyg.permission;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hyg.permission.interceptor.Interceptor;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author 韩永刚
 * @Date 2021/03/22
 * @Desc
 */
public class AndroidPermission {
    private LinkedBlockingQueue<Request> mRequestQuene;
    private RequestBuilder builder;

    private AndroidPermission(Context context) {
        builder = new RequestBuilder(context);
    }

    public static AndroidPermission with(@NonNull Context context) {
        return new AndroidPermission(context);
    }

    public static AndroidPermission with(@NonNull Fragment fragment) {
        return with(fragment.getActivity());
    }

    public AndroidPermission requestCode(int requestCode) {
        builder.setRequestCode(requestCode);
        return this;
    }

    public AndroidPermission permission(@NonNull String permission) {
        builder.setPermission(permission);
        return this;
    }

    public AndroidPermission permissions(@NonNull List<String> permissions) {
        builder.setPermissions(permissions);
        return this;
    }

    public AndroidPermission callBack(OnRequestPermissionCallback onRequestPermissionCallback) {
        builder.setOnRequestPermissionCallback(onRequestPermissionCallback);
        return this;
    }

    public AndroidPermission requestInterceptor(@NonNull Interceptor interceptor) {
        builder.addRequestInterceptor(interceptor);
        return this;
    }

    public AndroidPermission priority(int priority) {
        builder.setPriority(priority);
        return this;
    }

    public void request() {
        RequestManger instance = RequestManger.getInstance();
        instance.add(new Request(builder));
        instance.request();
    }
}
