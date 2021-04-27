package com.hyg.widgets.progress;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * @Author 韩永刚
 * @Date 2021/04/17
 * @Desc
 */
final class HStyleDraw2 extends AProgressDraw {

    public HStyleDraw2(IMeasure progress, int bgColor, int previewColor, int color, boolean isPreview) {
        super(progress, bgColor, previewColor, color, isPreview);
    }

    @Override
    protected void setPaint(Paint paint) {
        super.setPaint(paint);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void measure(int widthMeasureMode, int widthMeasureSize, int heightMeasureMode, int heightMeasureSize) {
        if (widthMeasureMode == View.MeasureSpec.AT_MOST) {
            widthMeasureSize = 200;
        }
        if (heightMeasureMode == View.MeasureSpec.AT_MOST) {
            heightMeasureSize = 200;
        }
        mIMeasure.measureProgress(widthMeasureSize,heightMeasureSize);
    }

    @Override
    protected void draw(Canvas canvas) {
        int centerX = measureWidth/2;
        int centerY = measureHeight/2;
        int radius = getRadius() - 20;
        mPaint.setColor(bgColor);
        RectF rectF = new RectF(centerX - radius,centerY - radius,centerX+radius,centerY+radius);
        canvas.drawArc(rectF,0,360,false,mPaint);
        if (isPreview) {
            mPaint.setColor(previewColor);
            canvas.drawArc(rectF,270,measureDegree(previewProgress),false,mPaint);
        }
        mPaint.setColor(color);
        canvas.drawArc(rectF,270,measureDegree(progress),false,mPaint);
    }

    private int getRadius(){
        return Math.min(measureWidth,measureHeight)/2;
    }

    private int measureDegree(long progress){
        return (int) (getDpi(progress)*360);
    }
}
