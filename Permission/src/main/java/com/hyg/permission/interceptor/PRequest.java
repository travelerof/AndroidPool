package com.hyg.permission.interceptor;

import android.content.Context;

import com.hyg.permission.OnRequestPermissionCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/03/22
 * @Desc
 */
public class PRequest {

    private Context context;
    private int requestCode;
    private List<String> permissions;
    private OnRequestPermissionCallback onRequestPermissionCallback;
    @InterceptorType.Type
    private int status = InterceptorType.PERMISSION_CONTINUE_APPLY;

    public PRequest(Context context){
        this.context = context;
        permissions = new ArrayList<>();
    }
    public Context getContext() {
        return context;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions.addAll(permissions);
    }

    public void setPermission(String permission) {
        this.permissions.add(permission);
    }

    public OnRequestPermissionCallback getOnRequestPermissionCallback() {
        return onRequestPermissionCallback;
    }

    public void setOnRequestPermissionCallback(OnRequestPermissionCallback onRequestPermissionCallback) {
        this.onRequestPermissionCallback = onRequestPermissionCallback;
    }

    public void clearPermissions(){
        permissions.clear();
    }

    @InterceptorType.Type
    public int getStatus() {
        return status;
    }

    public void setStatus(@InterceptorType.Type int status) {
        this.status = status;
    }
}
