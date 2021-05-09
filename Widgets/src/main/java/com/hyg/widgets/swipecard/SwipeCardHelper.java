package com.hyg.widgets.swipecard;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author Administrator
 * @Date 2021/5/9
 * @Desc 滑动控制
 */
final class SwipeCardHelper extends ItemTouchHelper.SimpleCallback {
    private final OnSwipeCallback mOnSwipeCallback;
    private final ISwipe mISwipe;

    public SwipeCardHelper(@NonNull ISwipe iSwipe, @NonNull OnSwipeCallback onSwipeCallback) {
        super(0, 0);
        this.mISwipe = iSwipe;
        mOnSwipeCallback = onSwipeCallback;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (mOnSwipeCallback != null) {
            mOnSwipeCallback.onSwipe(viewHolder, direction);
        }
    }

    public void update(){
        setDefaultSwipeDirs(getSwipe());
    }
    /**
     * 获取滑动方向
     *
     * @return
     */
    private int getSwipe() {
        if (mISwipe.getSwipeType() == SwipeType.HORIZONTAL) {
            return ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.START | ItemTouchHelper.END;
        }
        return ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX);
        double fraction = distance / maxDistance;
        int childCount = recyclerView.getChildCount();
        int maxShow = mISwipe.maxShow();
        int xy_gap = mISwipe.getXyGap();
        float scale_gap = mISwipe.getScaleGap();
        for (int i = 0; i < childCount; i++) {
            View view = recyclerView.getChildAt(i);
            int level = childCount - i - 1;
            if (level > 0) {
                if (level < maxShow){
                    view.setTranslationY((float) (1 - xy_gap * level + fraction * xy_gap));
                    view.setScaleX((float) (1 - scale_gap * level + fraction * scale_gap));
                    view.setTranslationY((float) (1 - scale_gap * level + fraction * scale_gap));
                }
            }
        }
    }

}
