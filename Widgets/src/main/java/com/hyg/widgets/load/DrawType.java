package com.hyg.widgets.load;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author 韩永刚
 * @Date 2021/02/18
 * @Desc
 */
class DrawType {

    public static final int DEFAULT_DRAW = 0;
    public static final int ASSET_DRAW = 1;

    @IntDef({DEFAULT_DRAW,ASSET_DRAW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type{}
}
