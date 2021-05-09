package com.hyg.widgets.swipecard;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author Administrator
 * @Date 2021/5/9 0009
 * @Desc
 */
@IntDef({SwipeType.HORIZONTAL,SwipeType.VERTICAL})
@Retention(RetentionPolicy.SOURCE)
@interface SwipeType {
    /**
     * 横向
     */
    int HORIZONTAL = 0;
    /**
     * 纵向
     */
    int VERTICAL = 1;

    int LEFT = 0;
    int TOP = 1;
    int RIGHT = 2;
    int BOTTOM = 3;

}
