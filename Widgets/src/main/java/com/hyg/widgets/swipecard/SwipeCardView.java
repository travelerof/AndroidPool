package com.hyg.widgets.swipecard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author Administrator
 * @Date 2021/5/8 0008
 * @Desc
 */
public class SwipeCardView extends FrameLayout implements OnSwipeCallback, ISwipe {

    @SwipeType
    private int swipeType = SwipeType.HORIZONTAL;
    private int maxShow = 4;
    private int xy_gap = 40;
    private float scale_gap = 0.05f;
    private RecyclerView mRecyclerView;
    private SwipeCardAdapter mAdapter;
    private SwipeCardHelper mSwipeCardHelper;
    private SwipeCardLayoutManager mSwipeCardLayoutManager;

    public SwipeCardView(@NonNull Context context) {
        this(context, null);
    }

    public SwipeCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mRecyclerView = new RecyclerView(context);
        addRecycerView();
        mSwipeCardHelper = new SwipeCardHelper(this,this);
        mSwipeCardLayoutManager = new SwipeCardLayoutManager(this);
        setSwipeType(swipeType);
        mRecyclerView.setLayoutManager(mSwipeCardLayoutManager);
        //item滑动绑定recyclerview
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSwipeCardHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 将recyclerview添加到当前布局中
     */
    private void addRecycerView() {
        FrameLayout.LayoutParams params = new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, params);
    }

    /**
     * @param swipeType
     */
    public void setSwipeType(@SwipeType int swipeType) {
        this.swipeType = swipeType;
        if (mSwipeCardHelper != null) {
            mSwipeCardHelper.update();
        }
    }


    public void setAdapter(@NonNull SwipeCardAdapter adapter) {
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
    }

    public void notifyAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSwipe(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        viewHolder.getAdapterPosition();
        Object o = mAdapter.data.remove(viewHolder.getAdapterPosition());
        mAdapter.data.add(0,o);
        notifyAdapter();
    }

    @Override
    public int maxShow() {
        return maxShow;
    }

    @Override
    public int getSwipeType() {
        return swipeType;
    }

    @Override
    public float getScaleGap() {
        return scale_gap;
    }

    @Override
    public int getXyGap() {
        return xy_gap;
    }
}
