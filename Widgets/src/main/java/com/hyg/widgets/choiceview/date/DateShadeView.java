package com.hyg.widgets.choiceview.date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hyg.utils.DensityUtils;
import com.hyg.widgets.R;
import com.hyg.widgets.choiceview.ChoiceView;

/**
 * @Author 韩永刚
 * @Date 2021/02/10
 * @Desc
 */
public class DateShadeView extends View {

    @ChoiceView.Limit
    private int limit = ChoiceView.LIMIT_5;
    private int itemHeight;
    private Paint paint;
    private int shadeColor;
    public DateShadeView(Context context) {
        this(context, null);
    }

    public DateShadeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateShadeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        itemHeight = DensityUtils.dip2Px(getContext(),40);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_99ffffff));
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(widthMeasureSize,itemHeight*limit);
    }

    public void setLimit(@ChoiceView.Limit int limit){
        this.limit = limit;
        invalidate();
    }
    public void setItemHeight(int itemHeight){
        this.itemHeight = itemHeight;
        invalidate();
    }
    public void setShadeColor(int color){
        this.shadeColor = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int averageLimit = limit/2;

        paint.setColor(shadeColor);
        canvas.drawRect(0,0,width,itemHeight*averageLimit,paint);
        canvas.drawRect(0,height - itemHeight*averageLimit,width,height,paint);

        paint.setColor(getResources().getColor(R.color.color_555555));
        canvas.drawLine(0,itemHeight*averageLimit,width,itemHeight*averageLimit,paint);
        canvas.drawLine(0,height - itemHeight*averageLimit,width,height - itemHeight*averageLimit,paint);
    }
}
