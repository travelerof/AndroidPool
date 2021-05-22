package com.hyg.hpermission.request;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hyg.hpermission.HPermission;
import com.hyg.hpermission.HPermissionUtils;
import com.hyg.hpermission.permission.Permission;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
public class RequestPermissionFragment extends Fragment implements PermissionDialog.OnPermissionListener {

    public static final String TAG = RequestPermissionFragment.class.getSimpleName();
    public static final String REQUEST_TYPE = "request_type";
    public static final String REQUEST_C0DE = "request_code";
    public static final String ARRAY_PERMISSIONS = "array_permissions";

    /**
     * 申请悬浮窗权限
     */
    private static final int APPLY_OVERLAY_REQUESTCODE = 1000;
    /**
     * 权限申请类型
     */
    @RequestType
    private int requestType;
    private int requestCode;
    /**
     * 需要申请的权限列表
     */
    private ArrayList<String> permissions;
    /**
     * 权限申请完成回调
     */
    private IRequestCallback mIRequestCallback;
    /**
     * 特殊权限申请结果
     */
    private List<String> resultPermissions;
    /**
     * 特殊权限申请结果code
     */
    private List<Integer> grantResults;
    /**
     * 当前需要申请的权限
     */
    private String mSpecialPermission;
    private PermissionDialog mDialog;

    public static RequestPermissionFragment beginFragment(@NonNull IRequestCallback iRequestCallback, @RequestType int requestType, int requestCode, @NonNull ArrayList<String> permissions) {
        RequestPermissionFragment fragment = new RequestPermissionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(REQUEST_TYPE, requestType);
        bundle.putInt(REQUEST_C0DE, requestCode);
        bundle.putStringArrayList(ARRAY_PERMISSIONS, permissions);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);
        fragment.setIResultCallback(iRequestCallback);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        requestType = bundle.getInt(REQUEST_TYPE, RequestType.NORMAL_PERMISSION);
        requestCode = bundle.getInt(REQUEST_C0DE, -1);
        permissions = bundle.getStringArrayList(ARRAY_PERMISSIONS);
        if (permissions != null && permissions.size() > 0) {
            requestPermission();
        }
    }

    private void requestPermission() {
        if (requestType == RequestType.SPECIAL_PERMISSION) {
            requstSpecialPermission();
            return;
        }
        requestPermissions(toStringArray(permissions), requestCode);
    }

    /**
     * 请求特殊权限
     */
    private void requstSpecialPermission() {
        if (permissions.size() <= 0) {
            if (mIRequestCallback != null) {
                mIRequestCallback.onPermissionResult(requestCode, toStringArray(resultPermissions), toIntArray(grantResults));
            }
            detach();
            return;
        }
        initDialog();
        mSpecialPermission = permissions.get(0);
        mDialog.setMessage(HPermissionUtils.getPermissionDescript(getContext(),mSpecialPermission));
        mDialog.setTitle(HPermissionUtils.getPermissionTitleResId(getContext(),mSpecialPermission));
        mDialog.show();
    }

    private void initDialog() {
        if (mDialog == null) {
            mDialog = new PermissionDialog(getActivity());
            mDialog.setOnPermissionListener(this);
        }
        if (resultPermissions == null) {
            resultPermissions = new ArrayList<>();
        }

        if (grantResults == null) {
            grantResults = new ArrayList<>();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestType != RequestType.SPECIAL_PERMISSION) {
            return;
        }
        switch (resultCode) {
            case APPLY_OVERLAY_REQUESTCODE:
                boolean overlay = HPermissionUtils.hasOverlayPermission(getContext());
                resultPermissions.add(mSpecialPermission);
                grantResults.add(overlay ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED);
                break;
        }
        removeCurrentPermission();
        requstSpecialPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mIRequestCallback != null) {
            mIRequestCallback.onPermissionResult(requestCode, permissions, grantResults);
        }
        detach();
    }

    public void attach(@NonNull FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction().add(this, TAG).commitAllowingStateLoss();
    }

    public void detach() {
        mIRequestCallback = null;
        mDialog = null;
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }

    public void setIResultCallback(IRequestCallback iResultCallback) {
        mIRequestCallback = iResultCallback;
    }

    /**
     * 申请悬浮窗权限
     */
    private void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0以上
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivityForResult(intent,APPLY_OVERLAY_REQUESTCODE);
        }else{
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:"+getContext().getPackageName()));
            startActivityForResult(intent,APPLY_OVERLAY_REQUESTCODE);
        }
    }

    private String[] toStringArray(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    private int[] toIntArray(List<Integer> list) {
        int[] grans = new int[list.size()];
        int count = 0;
        for (Integer integer : list) {
            grans[count++] = integer;
        }
        return grans;
    }

    @Override
    public void onApply(PermissionDialog dialog, View v) {
        dialog.dismiss();
        switch (mSpecialPermission) {
            case Permission.APPLICATION_WINDOW_OVERLAY:
                requestOverlayPermission();
                break;
        }
    }

    @Override
    public void onRefuse(PermissionDialog dialog, View v) {
        dialog.dismiss();
        resultPermissions.add(mSpecialPermission);
        grantResults.add(PackageManager.PERMISSION_DENIED);
        removeCurrentPermission();
        requstSpecialPermission();
    }

    private void removeCurrentPermission(){
        permissions.remove(mSpecialPermission);
        mSpecialPermission = null;
    }

}
