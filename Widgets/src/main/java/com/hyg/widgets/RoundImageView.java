package com.hyg.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author 韩永刚
 * @Date 2021/02/22
 * @Desc
 */
public class RoundImageView extends View {

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
