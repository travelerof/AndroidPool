package com.hyg.mch;

import android.Manifest;
import android.app.AppOpsManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hyg.hpermission.HPermission;
import com.hyg.hpermission.OnPermissionCallback;
import com.hyg.hpermission.permission.Permission;
import com.hyg.hpermission.request.RequestType;
import com.hyg.utils.AppUtils;

import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    public static final String TAG = PermissionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    public void checkPermission(View view) {
        int uid = AppUtils.getUid(this);
        String packageName = AppUtils.getPackageName(this);
        Log.i(TAG, "=======uid======" + uid + "========packageName========" + packageName);
        AppOpsManager opsManager = (AppOpsManager) getSystemService(APP_OPS_SERVICE);
        int request1 = opsManager.checkOp(opsManager.permissionToOp(Manifest.permission.WRITE_EXTERNAL_STORAGE), uid, packageName);
        int request2 = opsManager.checkOp(opsManager.permissionToOp(Manifest.permission.CAMERA), uid, packageName);
        int request3 = opsManager.checkOp(opsManager.permissionToOp(Manifest.permission.CALL_PHONE), uid, packageName);
        Log.i(TAG, "=======WRITE_EXTERNAL_STORAGE======" + request1);
        Log.i(TAG, "=======CAMERA======" + request2);
        Log.i(TAG, "=======CALL_PHONE======" + request3);
    }

    public void requestPermission(View view) {
        HPermission.with(this)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .requestCode(120)
                .callBack(new OnPermissionCallback() {
                    @Override
                    public void onSuccess(int requestCode, List<String> permission) {
                        Log.i("test1","=========onSuccess========");
                    }

                    @Override
                    public void onFailed(int requestCode, List<String> permission) {
                        Log.i("test1","========onFailed=========");
                    }
                })
                .request();
        HPermission.with(this)
                .permission(Permission.APPLICATION_WINDOW_OVERLAY,Permission.APPLICATION_WRITE_SETTINGS)
                .requestCode(123)
                .requestType(RequestType.SPECIAL_PERMISSION)
                .callBack(new OnPermissionCallback() {
                    @Override
                    public void onSuccess(int requestCode, List<String> permission) {

                    }

                    @Override
                    public void onFailed(int requestCode, List<String> permission) {

                    }
                })
                .request();
    }
}