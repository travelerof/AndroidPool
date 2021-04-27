package com.hyg.widgets.label;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyg.utils.DensityUtils;
import com.hyg.widgets.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/02/10
 * @Desc 自适应标签view
 */
public class LabelView extends ViewGroup {

    private List<String> data;
    /**
     * 横向padding
     */
    private int labelHorizontalPadding;
    /**
     * 纵向padding
     */
    private int labelVerticalPadding;
    /**
     * 标签纵向间距
     */
    private int labelVerticalMargin;
    /**
     * 标签横向间距
     */
    private int labelHorizontalMargin;
    /**
     * 标签字体大小
     */
    private float labelSize;
    /**
     * 标签字体颜色
     */
    private int labelColor;
    /**
     * 标签背景id
     */
    private int labelBgId;
    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        data = new ArrayList<>();
        labelHorizontalPadding = DensityUtils.dip2Px(context,8);
        labelVerticalPadding = DensityUtils.dip2Px(context,3);
        labelHorizontalMargin = DensityUtils.dip2Px(context,5);
        labelVerticalMargin = DensityUtils.dip2Px(context,5);
        labelBgId = R.drawable.shape_lable_bg;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.LabelView);
            labelHorizontalPadding = array.getDimensionPixelSize(R.styleable.LabelView_labelHorizontalPadding,DensityUtils.dip2Px(context,8));
            labelVerticalPadding = array.getDimensionPixelSize(R.styleable.LabelView_labelVerticalPadding,DensityUtils.dip2Px(context,3));
            labelHorizontalMargin = array.getDimensionPixelSize(R.styleable.LabelView_labelHorizontalMargin,DensityUtils.dip2Px(context,8));
            labelVerticalMargin = array.getDimensionPixelSize(R.styleable.LabelView_labelVerticalMargin,DensityUtils.dip2Px(context,5));
            labelBgId = array.getResourceId(R.styleable.LabelView_labelResource,R.drawable.shape_lable_bg);
            array.recycle();
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int startX = labelHorizontalMargin;
        int startY = labelVerticalMargin;
        //根据子view大小测量容器高度
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            int childMeasureWidth = child.getMeasuredWidth();
            int childMeasureHeight = child.getMeasuredHeight();
            if (startX + childMeasureWidth > widthMeasureSize - labelHorizontalMargin) {
                startX = labelHorizontalMargin;
                startY += childMeasureHeight+labelVerticalMargin;
            }
            child.setTag(new Location(startX,startY,startX+childMeasureWidth,startY+childMeasureHeight));
            startX += childMeasureWidth+labelHorizontalMargin;
            if (i == childCount - 1) {
                startY += childMeasureHeight+labelVerticalMargin;
            }
        }
        //测量完成设置容器高度
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),startY);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        //重新摆放子view
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            Location location = (Location) child.getTag();
            child.layout(location.left,location.top,location.right,location.bottom);
        }
    }

    /**
     * 添加标签
     * @param label
     */
    public void addLabel(String label){
        if (TextUtils.isEmpty(label)) {
            return;
        }
        data.add(label);
        addView(createLabelView(label));
        requestLayout();
    }

    /**
     * 返回数据源
     * @return
     */
    public List<String> getData(){
        return data;
    }

    /**
     * 创建标签
     * @param text
     * @return
     */
    private TextView createLabelView(String text){
        TextView tv = new TextView(getContext());
        tv.setBackgroundResource(labelBgId);
        tv.setPadding(labelHorizontalPadding,labelVerticalPadding,labelHorizontalPadding,labelVerticalPadding);
        tv.setTextColor(labelColor);
        tv.setTextSize(labelSize);
        tv.setText(text);
        return tv;
    }

    /**
     * 重新排序
     */
    public void reSort(){
        Collections.sort(data,new SortComparator());
        requestLayout();
    }

    public static class SortComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.length() - o2.length();
        }
    }

    private static class Location{

        public int left;
        public int top;
        public int right;
        public int bottom;

        public Location(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }
    }
}
