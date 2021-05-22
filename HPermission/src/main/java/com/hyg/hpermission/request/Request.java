package com.hyg.hpermission.request;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
class Request extends HRequest {
    public Request(@NonNull @org.jetbrains.annotations.NotNull Context context, @NonNull @org.jetbrains.annotations.NotNull RequestOptions options) {
        super(context, options);
    }

    @NonNull
    @NotNull
    @Override
    protected ArrayList<String> checkedPermission(@NonNull @NotNull List<String> permissions) {
        ArrayList<String> applies = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (getContext().checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    applies.add(permission);
                }
            }
        }
        return applies;
    }


}
