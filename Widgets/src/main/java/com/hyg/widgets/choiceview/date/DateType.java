package com.hyg.widgets.choiceview.date;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author 韩永刚
 * @Date 2021/02/05
 * @Desc 时间选择器支持的类型
 */
class DateType {
    /**
     * 年
     */
    public static final int YEAR = 0;
    /**
     * 年，月
     */
    public static final int YEAR_MONTH = 1;
    /**
     * 年，月，日
     */
    public static final int YEAR_MONTH_DAY = 2;
    /**
     * 年，月，日，时，分
     */
    public static final int YEAR_MONTH_DAY_HOUR_MINUTE = 3;
    /**
     * 时，分
     */
    public static final int HOUR_MINUTE = 4;

    @IntDef({YEAR,YEAR_MONTH,YEAR_MONTH_DAY,YEAR_MONTH_DAY_HOUR_MINUTE,HOUR_MINUTE})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type{}
}
