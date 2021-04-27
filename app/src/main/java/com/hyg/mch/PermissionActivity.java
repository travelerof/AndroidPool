package com.hyg.mch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AppOpsManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.hyg.permission.AndroidPermission;
import com.hyg.permission.OnRequestPermissionCallback;
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
        Log.i(TAG,"=======uid======"+uid+"========packageName========"+packageName);
        AppOpsManager opsManager = (AppOpsManager) getSystemService(APP_OPS_SERVICE);
        int request1 = opsManager.checkOp(opsManager.permissionToOp(Manifest.permission.WRITE_EXTERNAL_STORAGE),uid,packageName);
        int request2 = opsManager.checkOp(opsManager.permissionToOp(Manifest.permission.CAMERA),uid,packageName);
        int request3 = opsManager.checkOp(opsManager.permissionToOp(Manifest.permission.CALL_PHONE),uid,packageName);
        Log.i(TAG,"=======WRITE_EXTERNAL_STORAGE======"+request1);
        Log.i(TAG,"=======CAMERA======"+request2);
        Log.i(TAG,"=======CALL_PHONE======"+ request3);
    }

    public void requestPermission(View view) {
        AndroidPermission.with(this)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .permission(Manifest.permission.CAMERA)
                .callBack(new OnRequestPermissionCallback() {
                    @Override
                    public void onSuccess(int requestCode, List<String> permissions) {
                        Log.i("test","======WRITE_EXTERNAL_STORAGE====onSuccess=");
                    }

                    @Override
                    public void onFailed(int requestCode, List<String> permissions) {
                        Log.i("test","======WRITE_EXTERNAL_STORAGE====onFailed=");
                    }
                })
                .request();
    }
}