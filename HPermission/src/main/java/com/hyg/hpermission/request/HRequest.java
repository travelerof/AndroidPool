package com.hyg.hpermission.request;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.hyg.hpermission.permission.Permission;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
abstract class HRequest implements IRequest, IRequestCallback {

    private final Context mContext;
    protected RequestOptions mOptions;
    protected OnRequestListener mOnRequestListener;

    public HRequest(@NonNull Context context, @NonNull RequestOptions options) {
        mContext = context;
        mOptions = options;
    }

    protected final Context getContext() {
        return mContext;
    }

    protected final FragmentActivity getActivity() {
        Context context = mContext;
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
    public void request() {
        ArrayList<String> applyies = checkedPermission(mOptions.permissions);
        if (applyies.isEmpty()) {
            if (mOptions.mOnPermissionCallback != null) {
                mOptions.mOnPermissionCallback.onSuccess(mOptions.requestCode, mOptions.permissions);
            }
            if (mOnRequestListener != null) {
                mOnRequestListener.onComplete();
            }
            return;
        }
        attach(applyies);
    }

    protected void attach(ArrayList<String> permissions) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        RequestPermissionFragment.beginFragment(this, mOptions.requestType, mOptions.requestCode, permissions).attach(activity);
    }

    @NonNull
    protected abstract ArrayList<String> checkedPermission(@NonNull List<String> permissions);


    @Override
    public void onPermissionResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (mOnRequestListener != null) {
            mOnRequestListener.onComplete();
        }
        if (mOptions.mOnPermissionCallback == null) {
            return;
        }
        List<String> success = new ArrayList<>();
        List<String> failed = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                failed.add(permissions[i]);
            } else {
                success.add(permissions[i]);
            }
        }
        if (failed.isEmpty()) {
            mOptions.mOnPermissionCallback.onSuccess(requestCode, success);
            return;
        }
        mOptions.mOnPermissionCallback.onFailed(requestCode, failed);
    }

    @Override
    public void addOnRequestListener(OnRequestListener onRequestListener) {
        mOnRequestListener = onRequestListener;
    }
}
