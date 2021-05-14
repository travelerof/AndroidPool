package com.hyg.videoui;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author hanyonggang
 * @Date 2021/5/10 0010
 * @Desc
 */
@IntDef({AnimationStatus.SHOW, AnimationStatus.HIDE})
@Retention(RetentionPolicy.SOURCE)
public @interface AnimationStatus {
    int SHOW = 0;
    int HIDE = 1;
}
