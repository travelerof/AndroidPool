package com.hyg.widgets.load;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

/**
 * @Author 韩永刚
 * @Date 2021/02/18
 * @Desc
 */
class Draw2 implements IDraw {
    private int size;
    private int margin = 5;
    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull Paint paint, int degress) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(margin, margin, size - margin, size - margin, 270, degress, true, paint);
    }

    @Override
    public void onAttachToWindow() {

    }

    @Override
    public void onDetachFromWindow() {

    }

    @Override
    public void destory() {

    }
}
