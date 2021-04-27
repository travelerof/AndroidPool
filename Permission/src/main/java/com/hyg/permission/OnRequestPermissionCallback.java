package com.hyg.permission;

import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/03/22
 * @Desc
 */
public interface OnRequestPermissionCallback {

    void onSuccess(int requestCode, List<String> permissions);

    void onFailed(int requestCode, List<String> permissions);
}
