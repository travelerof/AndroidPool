package com.hyg.widgets.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hyg.widgets.R;


/**
 * @Author 韩永刚
 * @Data 2021/01/27
 * @Desc 横向进度条
 */
public class HProgressView extends View implements IMeasure {

    private static final int DEFAULT = 0;
    private static final int CIRCLE_STROKE = 1;
    private static final int CIRCLE_FILL = 2;

    private AProgressDraw mDraw;
    private int progressType = DEFAULT;
    private boolean touch = true;
    private OnTouchProgressListener onTouchProgressListener;

    public HProgressView(Context context) {
        this(context, null);
    }

    public HProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.HProgressView);
        int bgColor = context.getColor(R.color.color_555555);
        int color = context.getColor(R.color.color_FFD866);
        int previewColor = context.getColor(R.color.color_2D2A2E);
        boolean isPreview = true;
        int progressHeight = 5;
        try {
            touch = array.getBoolean(R.styleable.HProgressView_touch, true);
            progressType = array.getInt(R.styleable.HProgressView_progresstype, DEFAULT);
            bgColor = array.getColor(R.styleable.HProgressView_bg_color, context.getColor(R.color.color_555555));
            color = array.getColor(R.styleable.HProgressView_progress_color, context.getColor(R.color.color_FFD866));
            previewColor = array.getColor(R.styleable.HProgressView_preview_color, context.getColor(R.color.color_2D2A2E));
            isPreview = array.getBoolean(R.styleable.HProgressView_isPreview, true);
            progressHeight = array.getInt(R.styleable.HProgressView_progress_height, 5);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
        mDraw = createDraw(bgColor, color, previewColor, isPreview);
        initPaint(progressHeight);
    }

    private void initPaint(float progressHeight) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(progressHeight * 2);
        paint.setStrokeCap(Paint.Cap.ROUND);
        mDraw.setPaint(paint);
    }

    private AProgressDraw createDraw(int bgColor,
                                     int color,
                                     int previewColor,
                                     boolean isPreview) {
        AProgressDraw draw;
        switch (progressType) {
            case CIRCLE_STROKE:
                draw = new HStyleDraw2(this, bgColor, previewColor, color, isPreview);
                break;
            case CIRCLE_FILL:
                draw = new HStyleDraw3(this, bgColor, previewColor, color, isPreview);
                break;
            default:
                draw = new HStyleDraw1(this, bgColor, previewColor, color, isPreview);
                break;
        }
        return draw;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!touch) {
            return super.onTouchEvent(event);
        }
        mDraw.touch(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        mDraw.measure(widthMeasureMode, widthMeasureSize, heightMeasureMode, heightMeasureSize);
        mDraw.measureSize(getMeasuredWidth(), getMeasuredHeight());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDraw.draw(canvas);
    }

    public void setTotalProgress(long totalProgress) {
        mDraw.setTotalProgress(totalProgress);
        invalidate();
    }

    public void setPreviewProgress(long previewProgress) {
        mDraw.setPreviewProgress(previewProgress);
        invalidate();
    }

    public void setProgress(long progress) {
        mDraw.setProgress(progress);
        invalidate();
    }

    public void setOnTouchProgressListener(OnTouchProgressListener onTouchProgressListener) {
        this.onTouchProgressListener = onTouchProgressListener;
    }

    @Override
    public void measureProgress(int width, int height) {
        setMeasuredDimension(width, height);
    }

    @Override
    public void refresh() {
        invalidate();
    }


    public interface OnTouchProgressListener {
        void onProgress(float progress);
    }
}
