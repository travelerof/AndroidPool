package com.hyg.videoui.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author hanyonggang
 * @Date 2021/5/11 0011
 * @Desc
 */
public class AnimationControllerHelper extends AnimatorListenerAdapter {

    private static final int CLOCK_WHAT = -2;
    /**
     * 动画最大值
     */
    private int maxValue;
    /**
     * 动画执行时间
     */
    private long duration;
    /**
     * 定时器时长
     */
    private long clock;

    private ValueAnimator mValueAnimator;

    /**
     * 是否正在执行动画
     */
    private boolean isAnimationing;

    private Set<AbstractAnimationController> mControllers;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == CLOCK_WHAT) {
                start();
                return true;
            }
            return false;
        }
    });

    public AnimationControllerHelper(int maxValue, long duration, long clock) {
        this.maxValue = maxValue;
        this.duration = duration;
        this.clock = clock;
        mControllers = new HashSet<>();
        mValueAnimator = ValueAnimator.ofInt(0, maxValue);
        mValueAnimator.setDuration(duration);
        mValueAnimator.addListener(this);
        mValueAnimator.addUpdateListener(this::onAnimationUpdate);
    }

    /**
     * 添加动画控制器
     *
     * @param controller
     */
    public void addController(@NonNull AbstractAnimationController controller) {
        mControllers.add(controller);
    }

    private void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();
        animationing(value);
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        endAnimation();
    }

    /**
     * 开始动画
     */
    public void start() {
        if (isAnimationing) {
            return;
        }
        startAnimation();
        mValueAnimator.start();
    }

    private void startAnimation() {
        isAnimationing = true;
        for (AbstractAnimationController controller : mControllers) {
            controller.startAnimation(maxValue);
        }
    }

    private void animationing(int value) {
        for (AbstractAnimationController controller : mControllers) {
            controller.animationing(value);
        }
    }

    private void endAnimation() {
        isAnimationing = false;
        for (AbstractAnimationController controller : mControllers) {
            controller.endAnimation();
        }
        startClock();
    }

    /**
     * 开启定时器
     */
    public void startClock() {
        //移除废弃的message
        mHandler.removeCallbacksAndMessages(null);
        //如果view显示，则开启定时器
        if (!isShow()) {
            return;
        }
        mHandler.sendEmptyMessageDelayed(CLOCK_WHAT, clock);
    }

    /**
     * 释放资源
     */
    public void release() {
        mControllers.clear();
        mHandler.removeCallbacksAndMessages(null);
        mValueAnimator.cancel();
    }

    /**
     * 判断需要执行动画的view是否显示
     *
     * @return
     */
    public boolean isShow() {
        int size = mControllers.size();
        int count = 0;
        for (AbstractAnimationController controller : mControllers) {
            if (controller.isVisible()) {
                count++;
            }
        }
        if (count >= size / 2) {
            return true;
        }
        return false;
    }
}
