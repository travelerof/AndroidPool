package com.hyg.hpermission.request;

import androidx.annotation.NonNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
interface IRequestCallback {
    void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
