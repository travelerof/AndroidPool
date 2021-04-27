package com.hyg.permission.interceptor;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/03/22
 * @Desc
 */
public final class DefaultCheckedInterceptor implements Interceptor {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public PRequest intercept(PRequest request) {
        Context context = request.getContext();
        List<String> permissions = request.getPermissions();
        if (context == null || permissions.isEmpty()) {
            request.setStatus(InterceptorType.PERMISSION_INTERCEPTOR);
            return request;
        }
        List<String> nRpermissions = checkPermission(context, permissions);
        if (nRpermissions.isEmpty()) {
            request.setStatus(InterceptorType.PERMISSION_APPLIED);
            return request;
        }
        request.clearPermissions();
        request.setPermissions(nRpermissions);
        return request;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.M)
    private List<String> checkPermission(@NonNull Context context, @NonNull List<String> allPermissions){

        List<String> nRpermissions = new ArrayList<>();
        for (String permission : allPermissions) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                nRpermissions.add(permission);
            }
        }
        return nRpermissions;
    }
}
