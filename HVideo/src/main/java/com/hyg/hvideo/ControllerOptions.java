package com.hyg.hvideo;

import android.graphics.Color;

/**
 * @Author 韩永刚
 * @Date 2021/04/02
 * @Desc
 */
final class ControllerOptions {
    /**
     * 播放器背景
     */
    public int playerBackgroundColor = Color.parseColor("#000000");
    /**
     * 控制器高度
     */
    public int controllerHeight;
    /**
     * 控制器背景
     */
    public int controllerBackgroundColor = playerBackgroundColor;
    /**
     * 进度条高度
     */
    public int progressHeight;
    /**
     * 进度条默认颜色
     */
    public int progressDefaultColor;
    /**
     * 进度条预加载颜色
     */
    public int progressReloadColor;
    /**
     * 进度条播放颜色
     */
    public int progressPlayColor;
    /**
     * 是否允许快进
     */
    public boolean allowFastForward = true;
    /**
     * 快进毫秒
     */
    public long fastForwardMillisecond = 5 * 1000;
    /**
     * 是否允许快退
     */
    public boolean allowRewind = true;
    /**
     * 快退毫秒
     */
    public long rewindMillisecond = 5 * 1000;
    /**
     * 是否允许倍速
     */
    public boolean allowSpeed = true;
    /**
     * 倍速列表
     */
    public @Speed
    int[] speed = new int[]{Speed.SPEED_0_25X, Speed.SPEED_0_5X, Speed.SPEED_0_75X,
            Speed.SPEED_1X, Speed.SPEED_1_5X, Speed.SPEED_2X, Speed.SPEED_3X};
    /**
     * 是否允许横竖屏切换
     */
    public boolean allowScreenChanged = true;
    /**
     * 是否允许双击播放，暂停
     */
    public boolean allowClickDouble = true;
    /**
     * 是否允许自动播放下一个
     */
    public boolean allowAutoPlayNext = true;
    /**
     * 是否显示下一个按钮
     */
    public boolean allowShowNext = true;



    private OnControllerListener mOnControllerListener;

}

