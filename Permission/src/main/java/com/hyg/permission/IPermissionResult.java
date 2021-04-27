package com.hyg.permission;

import androidx.annotation.NonNull;

/**
 * @Author 韩永刚
 * @Date 2021/03/22
 * @Desc
 */
interface IPermissionResult {
    void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
