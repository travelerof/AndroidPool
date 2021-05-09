package com.hyg.widgets.swipecard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author Administrator
 * @Date 2021/5/9 0009
 * @Desc
 */
interface OnSwipeCallback {
    void onSwipe(@NonNull RecyclerView.ViewHolder viewHolder, int direction);
}
