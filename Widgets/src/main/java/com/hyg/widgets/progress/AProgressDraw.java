package com.hyg.widgets.progress;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * @Author 韩永刚
 * @Date 2021/04/17
 * @Desc
 */
abstract class AProgressDraw {

    protected IMeasure mIMeasure;
    /**
     * 默认颜色
     */
    protected int bgColor;
    /**
     * 预加载颜色
     */
    protected int previewColor;
    /**
     * 进度颜色
     */
    protected int color;
    /**
     * 是否又预加载
     */
    protected boolean isPreview;

    protected Paint mPaint;

    /**
     * 测量宽高
     */
    protected int measureWidth;
    protected int measureHeight;

    /**
     * 总进度
     */
    protected long totalProgress;
    /**
     * 预览进度
     */
    protected long previewProgress;
    /**
     *
     */
    protected long progress;


    public AProgressDraw(IMeasure progress, int bgColor, int previewColor, int color, boolean isPreview) {
        mIMeasure = progress;
        this.bgColor = bgColor;
        this.previewColor = previewColor;
        this.color = color;
        this.isPreview = isPreview;
    }

    protected void setPaint(Paint paint) {
        mPaint = paint;
    }

    protected void measureSize(int measureWidth, int measureHeight) {
        this.measureWidth = measureWidth;
        this.measureHeight = measureHeight;
    }

    protected void setTotalProgress(long totalProgress) {
        this.totalProgress = totalProgress;
    }

    protected void setProgress(long progress) {
        this.progress = progress;
    }

    protected void setPreviewProgress(long previewProgress) {
        this.previewProgress = previewProgress;
    }

    protected double getDpi(long progress) {
        double dpi = progress / (double) totalProgress;
        if (dpi >= 1) {
            dpi = 1;
        }
        return dpi;
    }

    protected void touch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down(event);
                break;
            case MotionEvent.ACTION_MOVE:
                move(event);
                break;
            case MotionEvent.ACTION_UP:
                up(event);
                break;
        }
        mIMeasure.refresh();
    }

    protected void down(MotionEvent event) {

    }

    protected void move(MotionEvent event) {

    }

    protected void up(MotionEvent event) {

    }

    protected abstract void measure(int widthMeasureMode, int widthMeasureSize, int heightMeasureMode, int heightMeasureSize);

    protected abstract void draw(Canvas canvas);
}
