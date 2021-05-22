package com.hyg.hpermission.request;

import android.content.Context;

import androidx.annotation.NonNull;

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
class SpecialRequest extends HRequest {
    public SpecialRequest(@NonNull @NotNull Context context, @NonNull @NotNull RequestOptions options) {
        super(context, options);
    }

    @NonNull
    @NotNull
    @Override
    protected ArrayList<String> checkedPermission(@NonNull @NotNull List<String> permissions) {
        ArrayList<String> applies = new ArrayList<>();
        for (String permission : permissions) {
            if (!hasSpecialPermission(permission)) {
                applies.add(permission);
            }
        }
        return applies;
    }

    private boolean hasSpecialPermission(@Permission String permission){
        switch (permission) {
            case Permission.APPLICATION_WINDOW_OVERLAY:
                return HPermissionUtils.hasOverlayPermission(getContext());
        }
        return false;
    }
}
