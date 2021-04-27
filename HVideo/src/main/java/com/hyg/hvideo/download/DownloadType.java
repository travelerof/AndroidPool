package com.hyg.hvideo.download;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author 韩永刚
 * @Date 2021/04/02
 * @Desc
 */
@IntDef({DownloadType.DEFAULT, DownloadType.USER})
@Retention(RetentionPolicy.SOURCE)
public @interface DownloadType {
    /**
     * 默认方式
     */
    int DEFAULT = 0;
    /**
     * 开发者自己下载
     */
    int USER = 1;
}
