package com.hyg.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentProvider;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.hyg.hlog.HLog;

/**
 * @Author 韩永刚
 * @Date 2021/05/25
 * @Desc
 */
public class ClockView extends View {

    private static final String TAG = ClockView.class.getSimpleName();
    private static final float MAX_ANIM = 100.0f;
    private int duration = 3000;
    private int measureWidth;
    private int measureHeight;
    private RectF mRectF;
    private ValueAnimator mValueAnimator;
    private TextPaint mTextPaint;
    private Paint mPaint;
    private float currentAngle;
    private int mRadius;
    private int defaultColor = Color.parseColor("#aaaaaa");
    private int clockColor = Color.parseColor("#ff0000");
    private int count;
    private CountDownTimer mTimer;
    private OnClockListener mOnClockListener;
    private float textSize;
    private int textColor;

    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ClockView);
        try {
            defaultColor = array.getColor(R.styleable.ClockView_defaultColor,context.getColor(R.color.color_eeeeee));
            clockColor = array.getColor(R.styleable.ClockView_clockColor,context.getColor(R.color.color_555555));
            textSize = array.getDimension(R.styleable.ChoiceView_choiceTextSize,context.getResources().getDimension(R.dimen.textsize_14));
            textColor = array.getColor(R.styleable.ClockView_android_textColor,context.getColor(R.color.color_555555));

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            array.recycle();
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5.0f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureWidth = getMeasuredWidth();
        measureHeight = getMeasuredHeight();
        int radius = (int) (Math.min(measureWidth, measureHeight) / 2 - mPaint.getStrokeWidth());
        mRectF = new RectF(measureWidth / 2 - radius, measureHeight / 2 - radius, measureWidth / 2 + radius, measureHeight / 2 + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRectF == null) {
            return;
        }
        mPaint.setColor(defaultColor);
        canvas.drawArc(mRectF, 0, 360, false, mPaint);
        mPaint.setColor(clockColor);
        canvas.drawArc(mRectF, 270, getSweepAngle(), false, mPaint);
        String text = count + "s";
         Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, measureWidth / 2 - rect.width() / 2, measureHeight / 2 - rect.height(), mTextPaint);
    }

    private float getSweepAngle() {
        return currentAngle / MAX_ANIM * 360;
    }

    public void start() {
        startAnimator();
    }

    private void startAnimator() {
        count = duration / 1000;
        if (mValueAnimator == null) {
            mValueAnimator = ValueAnimator.ofFloat(0, MAX_ANIM);
            mValueAnimator.setDuration(duration);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            float v = MAX_ANIM / (duration / 1000);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currentAngle = (float) animation.getAnimatedValue();
                    HLog.i("动画测试", "=============" + currentAngle);
                    invalidate();
                }
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    HLog.e("动画", "======动画结束=====");
                    if (mOnClockListener != null) {
                        mOnClockListener.onEnd();
                    }
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mTimer.start();
                    if (mOnClockListener != null) {
                        mOnClockListener.onStart();
                    }
                }
            });
        }
        if (mTimer == null) {
            mTimer = new CountDownTimer(duration, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    HLog.e("动画", "=============" + millisUntilFinished);
                    invalidate();
                }

                @Override
                public void onFinish() {
                    HLog.e("动画", "======定时结束=====");
                }
            };
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

    public void setOnClockListener(OnClockListener onClockListener) {
        mOnClockListener = onClockListener;
    }

    public interface OnClockListener {
        void onStart();

        void onEnd();
    }
}
