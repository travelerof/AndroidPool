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

import com.hyg.hpermission.HPermissionUtils;
import com.hyg.hpermission.permission.Permission;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.hyg.hpermission.permission.PermissionCode.APPLY_OVERLAY_REQUESTCODE;
import static com.hyg.hpermission.permission.PermissionCode.APPLY_WRITE_SETTINGS_REQUESTCODE;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 申请权限的fragment
 */
public class RequestPermissionFragment extends Fragment implements PermissionDialog.OnPermissionListener {

    public static final String TAG = RequestPermissionFragment.class.getSimpleName();
    /**
     * 请求类型key
     */
    public static final String REQUEST_TYPE = "request_type";
    /**
     * 权限请求码key
     */
    public static final String REQUEST_C0DE = "request_code";
    /**
     * 需要请求的列表key
     */
    public static final String ARRAY_PERMISSIONS = "array_permissions";


    /**
     * 权限申请类型{@link RequestType}
     */
    @RequestType
    private int requestType;
    /**
     *
     */
    private int requestCode;
    /**
     * 需要申请的权限列表
     */
    private ArrayList<String> permissions;
    /**
     * 权限申请结束回调
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
    /**
     * 特殊权限dialog
     */
    private PermissionDialog mDialog;

    public static RequestPermissionFragment beginFragment(@NonNull IRequestCallback iRequestCallback, @RequestType int requestType, int requestCode, @NonNull ArrayList<String> permissions) {
        HPermissionUtils.print("开始绑定Fragment");
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
        if (permissions != null && permissions.size() > 0) {//权限列表不为空，请求
            requestPermission();
        }
    }

    private void requestPermission() {
        if (requestType == RequestType.SPECIAL_PERMISSION) {
            requstSpecialPermission();
            return;
        }
        HPermissionUtils.print("请求普通权限");
        requestPermissions(toStringArray(permissions), requestCode);
    }

    /**
     * 请求特殊权限
     */
    private void requstSpecialPermission() {
        removeCurrentPermission();
        if (permissions.size() <= 0) {
            if (mIRequestCallback != null) {
                mIRequestCallback.onPermissionResult(requestCode, toStringArray(resultPermissions), toIntArray(grantResults));
            }
            detach();
            return;
        }
        initDialog();
        mSpecialPermission = permissions.get(0);
        mDialog.setMessage(HPermissionUtils.getPermissionDescript(getContext(), mSpecialPermission));
        mDialog.setTitle(HPermissionUtils.getPermissionTitleResId(getContext(), mSpecialPermission));
        mDialog.show();
        HPermissionUtils.print("特殊权限="+mSpecialPermission);
    }

    /**
     * 初始化特殊权限dialog
     */
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
            case APPLY_OVERLAY_REQUESTCODE://悬浮窗权限结果
                boolean overlay = HPermissionUtils.hasOverlayPermission(getContext());
                resultPermissions.add(mSpecialPermission);
                grantResults.add(overlay ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED);
                break;
            case APPLY_WRITE_SETTINGS_REQUESTCODE:
                resultPermissions.add(mSpecialPermission);
                grantResults.add(HPermissionUtils.hasWriteSettingsPermission(getContext()) ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED);
                break;
        }

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
        HPermissionUtils.print("跳转悬浮窗权限页面");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
            startActivityForResult(intent, APPLY_OVERLAY_REQUESTCODE);
        }
    }

    /**
     * 申请设置权限
     */
    private void requestWriteSettingsPermission(){
        HPermissionUtils.print("跳转系统设置权限页面");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:"+getContext().getPackageName()));
            startActivityForResult(intent,APPLY_WRITE_SETTINGS_REQUESTCODE);
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

    /**
     * 同意申请特殊权限
     *
     * @param dialog
     * @param v
     */
    @Override
    public void onApply(PermissionDialog dialog, View v) {
        dialog.dismiss();
        HPermissionUtils.print("允许申请特殊权限:"+mSpecialPermission);
        switch (mSpecialPermission) {
            case Permission.APPLICATION_WINDOW_OVERLAY:
                requestOverlayPermission();
                break;
            case Permission.APPLICATION_WRITE_SETTINGS:
                requestWriteSettingsPermission();
                break;
        }
    }

    /**
     * 拒绝申请特殊权限
     * @param dialog
     * @param v
     */
    @Override
    public void onRefuse(PermissionDialog dialog, View v) {
        dialog.dismiss();
        HPermissionUtils.print("拒绝申请特殊权限:"+mSpecialPermission);
        resultPermissions.add(mSpecialPermission);
        grantResults.add(PackageManager.PERMISSION_DENIED);
        requstSpecialPermission();
    }

    /**
     * 申请完当前权限后，移除当前
     */
    private void removeCurrentPermission() {
        if (mSpecialPermission != null) {
            permissions.remove(mSpecialPermission);
            mSpecialPermission = null;
        }
    }

}
