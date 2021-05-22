package com.hyg.hpermission;

import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 权限请求回调
 */
public interface OnPermissionCallback {

    void onSuccess(int requestCode, List<String> permission);

    void onFailed(int requestCode, List<String> permission);
}
