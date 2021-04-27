package com.hyg.widgets.progress;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Author 韩永刚
 * @Date 2021/04/17
 * @Desc
 */
final class HStyleDraw1 extends AProgressDraw {
    public int margin = 20;

    public HStyleDraw1(IMeasure measure, int bgColor, int previewColor, int color, boolean isPreview) {
        super(measure, bgColor, previewColor, color, isPreview);
    }

    @Override
    protected void setPaint(Paint paint) {
        super.setPaint(paint);
        margin = (int) paint.getStrokeWidth();
    }

    @Override
    protected void move(MotionEvent event) {
        super.move(event);
        progressMove(event.getX());
    }

    @Override
    protected void down(MotionEvent event) {
        super.down(event);
        progressMove(event.getX());
    }

    @Override
    protected void measure(int widthMeasureMode, int widthMeasureSize, int heightMeasureMode, int heightMeasureSize) {
        if (heightMeasureMode == View.MeasureSpec.AT_MOST) {
            mIMeasure.measureProgress(widthMeasureSize, 100);
        }
    }

    @Override
    protected void draw(Canvas canvas) {
        int centerHeight = measureHeight / 2;
        mPaint.setColor(bgColor);
        canvas.drawLine(margin, centerHeight, measureWidth - margin, centerHeight, mPaint);

        //是否有预加载
        if (isPreview) {
            mPaint.setColor(previewColor);
            canvas.drawLine(margin, centerHeight, getDrawWidth(previewProgress), centerHeight, mPaint);
        }
        mPaint.setColor(color);
        canvas.drawLine(margin, centerHeight, getDrawWidth(progress), centerHeight, mPaint);
    }

    protected int getDrawWidth(long progress) {
        return (int) ((measureWidth - margin * 2) * getDpi(progress));
    }

    private void progressMove(float x) {
        float dpi = x / (measureWidth - margin * 2);
        setProgress((long) (totalProgress * dpi));
        mIMeasure.refresh();
    }
}
