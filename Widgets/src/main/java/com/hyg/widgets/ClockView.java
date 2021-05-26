package com.hyg.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * @Author 韩永刚
 * @Date 2021/05/25
 * @Desc
 */
public class ClockView extends View {
    private static final int MAX_ANIM = 100;
    private int duration;
    private int measureWidth;
    private int measureHeight;
    private ValueAnimator mValueAnimator;
    private TextPaint mTextPaint;
    private Paint mPaint;
    private int currentAngle;
    private int mRadius;


    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5.0f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureWidth = getMeasuredWidth();
        measureHeight = getMeasuredHeight();
        mRadius = Math.min(measureWidth, measureHeight) / 2 - 10;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(measureWidth / 2, measureHeight / 2, mRadius, mPaint);
//        canvas.drawArc(measureWidth);
    }

    public void start() {
        startAnimator();
    }

    private void startAnimator() {
        if (mValueAnimator == null) {
            mValueAnimator = ValueAnimator.ofInt(0, MAX_ANIM);
            mValueAnimator.setDuration(duration);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();

                }
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }
            });
        }
        if (mValueAnimator.isRunning()) {
            return;
        }
        mValueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mValueAnimator != null && mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
        }
        super.onDetachedFromWindow();
    }
}
