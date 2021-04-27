package com.hyg.widgets.load;

import android.animation.ValueAnimator;

/**
 * @Author 韩永刚
 * @Date 2021/02/18
 * @Desc
 */
interface IDraw1 extends IDraw {
    /**
     * 设置动画监听
     *
     * @param animatorUpdateListener
     */
    void setAnimatorUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener);

    /**
     * 获取平均每条对应的角度
     *
     * @return
     */
    int getDegressPer();

    /**
     * view显示隐藏
     *
     * @param visibility
     */
    void onVisibilityChanged(boolean visibility);
}
