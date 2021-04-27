package com.hyg.widgets.choiceview.date;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

/**
 * @Author 韩永刚
 * @Date 2021/02/09
 * @Desc
 */
interface IDateHandler {

    void init(@NonNull Context context, AttributeSet attrs, @NonNull IDate iDate);

    /**
     * 选中
     * @param values 年，月，日，时，分 顺序
     */
    void choiceItem(String... values);

    /**
     *
     * @return
     */
    @DateType.Type
    int getType();
}
