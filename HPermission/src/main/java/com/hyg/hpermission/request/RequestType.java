package com.hyg.hpermission.request;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 权限请求类型
 */
@IntDef({RequestType.NORMAL_PERMISSION, RequestType.SPECIAL_PERMISSION})
@Retention(RetentionPolicy.SOURCE)
public @interface RequestType {
    int NORMAL_PERMISSION = 0;
    int SPECIAL_PERMISSION = 1;
}
