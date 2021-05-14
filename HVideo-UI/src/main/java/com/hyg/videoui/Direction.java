package com.hyg.videoui;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author hanyonggang
 * @Date 2021/5/11 0011
 * @Desc 屏幕方向
 */
@IntDef({Direction.VERICAL, Direction.HORIZONTAL})
@Retention(RetentionPolicy.SOURCE)
public @interface Direction {
    /**
     * 竖屏
     */
    int VERICAL = 0;
    /**
     * 横屏
     */
    int HORIZONTAL = 1;
}
