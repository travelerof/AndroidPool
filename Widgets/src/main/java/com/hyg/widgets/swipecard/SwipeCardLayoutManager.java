package com.hyg.widgets.swipecard;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author Administrator
 * @Date 2021/5/9 0009
 * @Desc
 */
final class SwipeCardLayoutManager extends RecyclerView.LayoutManager {

    @SwipeType
    private int swipeType;

    private final ISwipe mISwipe;

    public SwipeCardLayoutManager(ISwipe iSwipe) {
        mISwipe = iSwipe;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public void setSwipeType(@SwipeType int swipeType) {
        this.swipeType = swipeType;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        detachAndScrapAttachedViews(recycler);
        //总共个数
        int itemCount = getItemCount();
        int bottomPosition = 0;
        int maxShow = mISwipe.maxShow();
        int xy_gap = mISwipe.getXyGap();
        float scale_gap = mISwipe.getScaleGap();
        if (maxShow <= itemCount) {
            bottomPosition = itemCount - maxShow;
        }
        for (int i = bottomPosition; i < itemCount; i++) {
            //获取对应角标的view
            View view = recycler.getViewForPosition(i);
            //将取出的view，添加到recyclerview中
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            layoutDecorated(view, widthSpace / 2, heightSpace / 2, widthSpace / 2 + getDecoratedMeasuredWidth(view), heightSpace / 2 + getDecoratedMeasuredHeight(view));
            //等级
            int level = itemCount - i - 1;
            drawView(view, level,maxShow,xy_gap,scale_gap);
        }
    }

    private void drawView(View view, int level,int maxShow,int xy_gap, float scale_gap) {
        if (level > 0) {
            if (level < maxShow) {
                switch (swipeType) {
                    case SwipeType.HORIZONTAL:
                        view.setTranslationX(xy_gap * level);
                        break;
                    default:
                        view.setTranslationY(xy_gap * level);
                        break;
                }
                view.setScaleX(1 - scale_gap * level);
                view.setScaleY(1 - scale_gap * level);
            }
        } else {
            switch (swipeType) {
                case SwipeType.HORIZONTAL:
                    view.setTranslationX(xy_gap * (level - 1));
                    break;
                default:
                    view.setTranslationY(xy_gap * (level - 1));
                    break;
            }
            view.setScaleX(1 - scale_gap * (level - 1));
            view.setScaleY(1 - scale_gap * (level - 1));
        }
    }
}
