package com.hyg.permission;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.hyg.permission.interceptor.Interceptor;
import com.hyg.permission.interceptor.InterceptorType;
import com.hyg.permission.interceptor.PRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/03/22
 * @Desc
 */
class Request implements IRequest, IPermissionResult {
    private final RequestBuilder builder;
    private OnRequestPermissionCallback callback;

    public Request(@NonNull RequestBuilder builder) {
        this.builder = builder;
        callback = builder.getOnRequestPermissionCallback();
    }

    @Override
    public void requst() {
        PRequest pRequest = excuteInterceptor();
        if (pRequest == null) {
            start();
            return;
        }
        attachFragment(pRequest);
    }

    private void attachFragment(PRequest pRequest) {
        FragmentActivity activity = getActivity(pRequest.getContext());
        if (activity == null) {
            return;
        }
        RequestPermissionFragment.beginFragment(activity, this, pRequest.getRequestCode(), PermissionUtils.toArray(pRequest.getPermissions()));
    }

    private FragmentActivity getActivity(Context context) {
        do {
            if (context instanceof FragmentActivity) {
                return (FragmentActivity) context;
            } else if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                return null;
            }
        } while (context != null);
        return null;
    }

    @Override
    public int priority() {
        return builder.getPriority();
    }

    public PRequest excuteInterceptor() {
        List<Interceptor> requestInterceptors = builder.getRequestInterceptor();
        PRequest pRequest = builder;
        for (Interceptor interceptor : requestInterceptors) {
            pRequest = interceptor.intercept(pRequest);
            if (pRequest.getStatus() == InterceptorType.PERMISSION_APPLIED) {
                if (callback != null) {
                    callback.onSuccess(builder.getRequestCode(), pRequest.getPermissions());
                }
                return null;
            } else if (pRequest.getStatus() == InterceptorType.PERMISSION_INTERCEPTOR) {
                if (callback != null) {
                    callback.onFailed(builder.getRequestCode(), pRequest.getPermissions());
                }
                return null;
            }
        }
        return pRequest;
    }

    @Override
    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //值
        List<String> successes = new ArrayList<>();
        List<String> failds = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            int result = grantResults[i];
            if (result != PackageManager.PERMISSION_GRANTED) {
                failds.add(permissions[i]);
            }else {
                successes.add(permissions[i]);
            }
        }
        if (callback != null) {
            if (!failds.isEmpty()) {
                callback.onFailed(requestCode, failds);
                return;
            }
            callback.onSuccess(requestCode, successes);
        }
        start();
    }

    private void start() {
        RequestManger.getInstance().requestStatus(false);
        RequestManger.getInstance().request();
    }
}
