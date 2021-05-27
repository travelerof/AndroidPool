package com.hyg.hpermission;

import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 权限请求回调
 */
public interface OnPermissionCallback {
    /**
     * 权限申请成功
     *
     * @param requestCode
     * @param permission
     */
    void onSuccess(int requestCode, List<String> permission);

    /**
     * 权限申请失败
     *
     * @param requestCode
     * @param permission
     */
    void onFailed(int requestCode, List<String> permission);
}
