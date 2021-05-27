package com.hyg.hpermission.request;

import androidx.annotation.NonNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 原始请求完结果
 */
interface IRequestCallback {
    void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
