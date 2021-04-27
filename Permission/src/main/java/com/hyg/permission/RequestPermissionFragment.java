package com.hyg.permission;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author 韩永刚
 * @Date 2021/03/12
 * @Desc
 */
public class RequestPermissionFragment extends Fragment {

    private static final String TAG = RequestPermissionFragment.class.getSimpleName();
    private IPermissionResult iPermissionResult;
    private int requstCode;
    private String[] permissions;
    public static void beginFragment(@NonNull FragmentActivity activity, @NonNull IPermissionResult result, int requestCode, @NonNull String[] permissions) {
        RequestPermissionFragment fragment = new RequestPermissionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("requsetCode_key", requestCode);
        bundle.putStringArray("permissions_key", permissions);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
        fragment.setIPermissionResult(result);
        fragment.attach(activity);
    }

    public void setIPermissionResult(IPermissionResult iPermissionResult){
        this.iPermissionResult = iPermissionResult;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        requstCode = arguments.getInt("requsetCode_key");
        permissions = arguments.getStringArray("permissions_key");
        if (permissions != null) {
            requestPermissions(permissions,requstCode);
        }
    }

    public void attach(@NonNull FragmentActivity activity){
        activity.getSupportFragmentManager().beginTransaction().add(this, TAG).commitAllowingStateLoss();
    }

    public void detach(){
        iPermissionResult = null;
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (iPermissionResult != null) {
            iPermissionResult.onPermissionResult(requestCode,permissions,grantResults);
        }
        detach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detach();
    }
}
