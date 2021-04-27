package com.hyg.widgets.load;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

/**
 * @Author 韩永刚
 * @Date 2021/02/18
 * @Desc
 */
interface IDraw {
    /**
     * 设置view尺寸
     *
     * @param size
     */
    void setSize(int size);

    /**
     * 绘制
     *
     * @param canvas
     * @param paint
     * @param degress
     */
    void draw(@NonNull Canvas canvas, @NonNull Paint paint, int degress);

    /**
     * view进入window时执行
     */
    void onAttachToWindow();

    /**
     * view离开window时执行
     */
    void onDetachFromWindow();

    void destory();
}
