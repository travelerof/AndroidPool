package com.hyg.widgets.choiceview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import androidx.annotation.IntDef;

import com.hyg.utils.DensityUtils;
import com.hyg.widgets.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/02/03
 * @Desc
 */
public class ChoiceView extends LinearLayout {

    public static final String TAG = ChoiceView.class.getSimpleName();

    public static final int LIMIT_3 = 3;
    public static final int LIMIT_5 = 5;
    public static final int LIMIT_7 = 7;

    private String choiceTAG;
    /**
     * item字体颜色
     */
    private int textColor;
    /**
     * item字体大小
     */
    private float textSize;

    @IntDef({LIMIT_3, LIMIT_5, LIMIT_7})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Limit {
    }

    /**
     * 滑动辅助
     */
    private OverScroller mScroller;
    /**
     * 触摸位置
     */
    private float mYMove;
    private float mYLastMove;

    private Context context;
    /**
     * 数据源
     */
    private List<String> data;
    /**
     * item高度
     */
    private int itemHeight;
    /**
     * 可见item条数
     */
    @Limit
    private int limit = LIMIT_5;
    private int mMaxScrollY;
    private int mScrollRange;
    private VelocityTracker mVelocityTracker;
    private OnChoiceScrollListener onChoiceScrollListener;
    /**
     * 选中角标
     */
    private int choiceIndex = -1;

    public ChoiceView(Context context) {
        this(context, null);
    }

    public ChoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChoiceView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null);

    }

    public ChoiceView(Context context, AttributeSet attrs, int defStyle, String choiceTAG) {
        super(context, attrs, defStyle);
        this.choiceTAG = choiceTAG;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        this.context = context;
        data = new ArrayList<>();
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ChoiceView);
            limit = array.getInt(R.styleable.ChoiceView_choiceLimit, LIMIT_5);
            textColor = array.getColor(R.styleable.ChoiceView_choiceTextColor, getResources().getColor(R.color.color_555555));
            textSize = array.getDimension(R.styleable.ChoiceView_choiceTextSize, getResources().getDimension(R.dimen.textsize_12)) / 2;
            itemHeight = array.getDimensionPixelSize(R.styleable.ChoiceView_choiceItemHeight, DensityUtils.dip2Px(context, 40));
            array.recycle();
        } else {
            itemHeight = DensityUtils.dip2Px(context, 40);
            textColor = getResources().getColor(R.color.color_555555);
            textSize = getResources().getDimension(R.dimen.textsize_12) / 2;
            limit = LIMIT_5;
            itemHeight = DensityUtils.dip2Px(context, 30);
        }
        HLog.i(TAG, "=========itemHeight===========" + itemHeight);
        mScroller = new OverScroller(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mYMove = ev.getY();
                mYLastMove = mYMove;
                break;
            case MotionEvent.ACTION_MOVE:
                mYMove = ev.getY();
                int scrolledY = (int) (mYLastMove - mYMove);
                if (scrolledY < 0 && getScrollY() + scrolledY < 0) {//向下滚动
                    scrolledY = -getScrollY();
                }
                if (scrolledY > 0 && getScrollY() + scrolledY > mScrollRange) {//向上滚动
                    scrolledY = mScrollRange - getScrollY();
                }
                if (mScrollRange > 0) {
                    scrollBy(0, scrolledY);
                    invalidate();
                }
                mYLastMove = mYMove;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mVelocityTracker != null && mScrollRange > 0) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxScrollY);
                    mScroller.fling(getScrollX(),//最终滑动的x坐标
                            getScrollY(),//最终滑动的y坐标
                            (int) mVelocityTracker.getXVelocity(0),//水平方向滑动加速度
                            -(int) mVelocityTracker.getYVelocity(0),
                            0,
                            0,
                            0,
                            mScrollRange);
                    invalidate();
                }
                releaseVelocityTracker();
                break;
        }
        return true;
    }

    /**
     * 重新计算纵向滑动距离
     *
     * @param scrollY 为itemHeight参数的倍数
     * @return
     */
    private int getScrollY(int scrollY) {
        int integer = scrollY / itemHeight;//取整
        if (scrollY % itemHeight > itemHeight / 2) {//判断滑动是否超过itemHeight/2，如果超过则整数+1
            integer += 1;
        }
        return integer * itemHeight;
    }

    public void setOnChoiceScrollListener(OnChoiceScrollListener onChoiceScrollListener) {
        this.onChoiceScrollListener = onChoiceScrollListener;
    }

    public List<String> getData() {
        return data;
    }

    public int getChoiceIndex() {
        return choiceIndex - (limit / 2) * 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DensityUtils.dip2Px(context, 60), itemHeight * limit);
        } else {
            setMeasuredDimension(widthMeasureSize, itemHeight * limit);
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            LayoutParams params = new LayoutParams(getMeasuredWidth(), itemHeight);
            childAt.setLayoutParams(params);
        }
    }

    public void setData(List<String> data) {
        this.data.clear();
        this.data.addAll(data);
        addView();
        mMaxScrollY = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        //每次设置值重新计算滑动界限
        mScrollRange = itemHeight * (data.size() + (limit / 2) * 2) - itemHeight * limit;
    }

    public void choice(int position) {
        if (position >= data.size()) {
            position = data.size() - 1;
        }
        choiceIndex = position;
        int index = getNowIndex();
        int scrollY = (choiceIndex + limit / 2 - index) * itemHeight;
        mScroller.startScroll(mScroller.getCurrX(), mScroller.getCurrY(), mScroller.getCurrX(), scrollY);
    }

    private void addView() {
        int childCount = getChildCount();
        int size = data.size() + (limit / 2) * 2;
        if (childCount < size) {
            for (int i = 0; i < size - childCount; i++) {
                addView(createTextView(), new LayoutParams(LayoutParams.MATCH_PARENT, itemHeight));
            }
        } else if (childCount > size) {
            removeViews(size, childCount - size);
        }
        setText();
    }

    private void setText() {
        int childCount = getChildCount();
        int itemLimit = limit / 2;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof TextView) {
                TextView tv = (TextView) childAt;
                if (i >= itemLimit && i < childCount - itemLimit) {
                    tv.setText(data.get(i - itemLimit));
                } else {
                    tv.setText("");
                }
            }
        }
    }

    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private TextView createTextView() {
        TextView tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(textColor);
        tv.setTextSize(textSize);
        return tv;
    }

    @Override
    public void computeScroll() {
        //mScroller.computeScrollOffset()判断是否滑动停止
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), getScrollY(mScroller.getCurrY()));
            postInvalidate();              //这必须调用刷新否则看不到效果
        } else {
            autoScrollFinish();
        }
    }

    /**
     * 滑动结束后执行
     */
    private void autoScrollFinish() {
        int index = getNowIndex();
        if (choiceIndex != index) {
            choiceIndex = index;
            View childView = getChildAt(choiceIndex);
            if (childView instanceof TextView) {
                TextView tv = (TextView) childView;
                if (onChoiceScrollListener != null) {
                    onChoiceScrollListener.onChoiced(this, tv.getText().toString());
                }
            }
        }
    }

    /**
     * 获取当前滑动位置对应的index
     *
     * @return
     */
    private int getNowIndex() {
        int scrollY = getScrollY(mScroller.getCurrY());
        return scrollY / itemHeight + limit / 2;
    }

    public String getChoiceTAG() {
        return choiceTAG;
    }

    public void setChoiceTAG(String choiceTAG) {
        this.choiceTAG = choiceTAG;
    }
}
