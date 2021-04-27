package com.hyg.hvideo;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author 韩永刚
 * @Date 2021/04/02
 * @Desc
 */
@IntDef({Speed.SPEED_0_25X, Speed.SPEED_0_5X, Speed.SPEED_0_75X, Speed.SPEED_1X, Speed.SPEED_1_5X,
        Speed.SPEED_2X, Speed.SPEED_3X})
@Retention(RetentionPolicy.SOURCE)
public @interface Speed {
    int SPEED_0_25X = 0;
    int SPEED_0_5X = 1;
    int SPEED_0_75X = 2;
    int SPEED_1X = 3;
    int SPEED_1_5X = 4;
    int SPEED_2X = 5;
    int SPEED_3X = 6;
}
