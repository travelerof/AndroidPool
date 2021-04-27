package com.hyg.widgets.load;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

/**
 * @Author 韩永刚
 * @Date 2021/02/18
 * @Desc
 */
class Draw1 implements IDraw1 {

    private static final int TOTAL = 12;
    private static final int DEGRESS_PER = 360/ TOTAL;

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;

    /**
     * view大小
     */
    private int size;
    private ValueAnimator animator;

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull Paint paint, int degress) {
        int width = size/16;
        int height = size/8;
        paint.setStrokeWidth(width);
        canvas.rotate(degress, size / 2, size / 2);
        canvas.translate(size / 2, size / 2);

        for (int i = 0; i < TOTAL; i++) {
            canvas.rotate(DEGRESS_PER);
            paint.setAlpha((int) (255f * (i + 1) / TOTAL));
            canvas.translate(0, -size / 2 + width / 2);
            canvas.drawLine(0, 0, 0, height, paint);
            canvas.translate(0, size / 2 - width / 2);
        }
    }

    @Override
    public void onAttachToWindow() {
        start();
    }

    @Override
    public void onDetachFromWindow() {
        stop();
    }

    @Override
    public void destory() {

    }

    @Override
    public void setAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.animatorUpdateListener = animatorUpdateListener;
    }

    @Override
    public int getDegressPer() {
        return DEGRESS_PER;
    }

    @Override
    public void onVisibilityChanged(boolean visibility) {
        if (visibility) {
            start();
        }else {
            stop();
        }
    }

    private void start(){
        if (animator == null) {
            animator = ValueAnimator.ofInt(0,TOTAL - 1);
            animator.addUpdateListener(animatorUpdateListener);
            animator.setDuration(600);
            animator.setRepeatMode(ValueAnimator.RESTART);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(ValueAnimator.INFINITE);
        }
        if (!animator.isStarted()) {
            animator.start();
        }

    }

    private void stop(){
        if (animator != null) {
            animator.removeAllUpdateListeners();
            animator.removeUpdateListener(animatorUpdateListener);
            animator.cancel();
            animator = null;
        }
    }
}
