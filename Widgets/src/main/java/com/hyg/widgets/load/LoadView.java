package com.hyg.widgets.load;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.hyg.utils.DensityUtils;
import com.hyg.widgets.R;

/**
 * @Author 韩永刚
 * @Data 2021/01/26
 * @Desc 加载动态view
 */
public class LoadView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final int totalDegress = 360;
    private IDraw draw;
    private int size;
    private int color;
    private int type;
    private Paint paint;
    private int total = 100;
    private int progress = 20;
    private int animatorValue;
    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadView);
            size = array.getDimensionPixelSize(R.styleable.LoadView_loadSize, DensityUtils.dip2Px(context,40));
            color = array.getColor(R.styleable.LoadView_loadColor,getResources().getColor(R.color.color_555555));
            type = array.getInt(R.styleable.LoadView_loadType,0);
            array.recycle();
        }else {
            size = DensityUtils.dip2Px(context,40);
            color = getResources().getColor(R.color.color_555555);
            type = 0;
        }
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        draw = createDraw();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(size,size);
        draw.setSize(Math.min(getMeasuredWidth(),getMeasuredHeight()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        draw.draw(canvas,paint,getDegress());
    }

    private IDraw createDraw(){
        switch (type) {
            case 1:
                return new Draw2();
            default:
                IDraw1 draw = new Draw1();
                draw.setAnimatorUpdateListener(this);
                return draw;
        }
    }

    public void setTotal(int total){
        this.total = total;
    }

    public void setProgress(int progress){
        if (progress > total) {
            this.progress = total;
        }else {
            this.progress = progress;
        }
        invalidate();
    }
    private int getDegress(){
        if (draw instanceof IDraw1) {
            IDraw1 iDraw = (IDraw1) draw;
            return animatorValue*iDraw.getDegressPer();
        }
        return (int) (((double)progress)/total*totalDegress);
    }

    @Override
    public void destroyDrawingCache() {
        draw.destory();
        draw = null;
        super.destroyDrawingCache();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        draw.onAttachToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        draw.onDetachFromWindow();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        animatorValue = (int)animation.getAnimatedValue();
        invalidate();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (draw instanceof IDraw1) {
            ((IDraw1)draw).onVisibilityChanged(visibility == VISIBLE);
        }
    }
}
