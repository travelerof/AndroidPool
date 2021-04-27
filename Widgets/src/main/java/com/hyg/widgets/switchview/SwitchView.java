package com.hyg.widgets.switchview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hyg.utils.DensityUtils;

/**
 * @Author 韩永刚
 * @Data 2021/01/30
 * @Desc 开关
 */
public class SwitchView extends View implements View.OnClickListener {

    private static final String TAG = SwitchView.class.getSimpleName();

    /**
     * 圆角
     */
    public static final int ROUND = 1;
    /**
     * 圆
     */
    public static final int CIRCLE = 2;

    @FrameType
    private int frameType = CIRCLE;

    /**
     * 开关是否开启
     */
    private boolean open;
    /**
     *
     * 半径
     *
     */
    private int radius;
    private int measuredWidth;
    private int measuredHeight;

    private Paint paint;
    private boolean checked;

    public SwitchView(Context context) {
        this(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL);

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        int defaultWidthSize = DensityUtils.dip2Px(getContext(),50);
        int defaultHeightSize = DensityUtils.dip2Px(getContext(),30);

        if (widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode != MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidthSize,heightMeasureSize);
        } else if (widthMeasureMode != MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSize,defaultHeightSize);
        } else {
            setMeasuredDimension(defaultWidthSize,defaultHeightSize);
        }
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (frameType == ROUND) {
            drawRound(canvas);
        } else {
            drawCircle(canvas);
        }
    }

    private void drawRound(Canvas canvas) {

    }

    private void drawCircle(Canvas canvas) {
        paint.setColor(open ? Color.parseColor("#ff0000") : Color.parseColor("#aaaaaa"));
        RectF leftRectF = new RectF(0,0,measuredHeight,measuredHeight);
        canvas.drawArc(leftRectF,90,180,true,paint);

        RectF centerRectF = new RectF(measuredHeight/2,0,measuredWidth - measuredHeight/2,measuredHeight);
        canvas.drawRect(centerRectF,paint);

        RectF rightRectF = new RectF(measuredWidth - measuredHeight,0,measuredWidth,measuredHeight);
        canvas.drawArc(rightRectF,270,180,true,paint);

//        getMeasuredHeight()/2+1 ==== measuredWidth - getMeasuredHeight()/2 -1
        if (open) {
            paint.setColor(Color.parseColor("#ffffff"));
            canvas.drawCircle(measuredWidth - measuredHeight/2 -1,measuredHeight/2,measuredHeight/2 - 1,paint);
        } else {
            paint.setColor(Color.parseColor("#555555"));
            canvas.drawCircle(measuredHeight/2 + 1,measuredHeight/2,measuredHeight/2 - 1,paint);
        }

    }

    @Override
    public void onClick(View v) {
        open = !open;
        invalidate();
    }
}
