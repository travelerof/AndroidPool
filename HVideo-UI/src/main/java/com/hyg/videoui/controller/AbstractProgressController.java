package com.hyg.videoui.controller;

import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.hyg.videoui.HConstant;
import com.hyg.videoui.utils.HVideoUtils;
import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/11 0011
 * @Desc
 */
public abstract class AbstractProgressController extends AbstractController implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = AbstractProgressController.class.getSimpleName();
    /**
     * 时间格式
     */
    protected String timeFormat = "mm:ss";
    /**
     * 视频总时长
     */
    protected long duration;

    /**
     * 是否正在拖动SeekBar
     */
    protected boolean isTouchSeekBar;
    /**
     * 视频播放位置
     */
    private long currentPosition;

    public AbstractProgressController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider) {
        super(containerView, operateProvider);
    }

    /**
     * 更新进度
     */
    public void updateProgress() {
        long bufferedPosition = mInteractiveHandler.getBufferedPosition();
        long currentPosition = mInteractiveHandler.getCurrentPosition();
        if (currentPosition - this.currentPosition >= 1000) {
            updateTime(HVideoUtils.getTime(currentPosition, timeFormat));
            updateProgress(HVideoUtils.toProgress(currentPosition, duration));
            this.currentPosition = currentPosition;
        }
        updateBufferedProgress(HVideoUtils.toProgress(bufferedPosition, duration));
    }

    @Override
    public void operate(int code) {
        if (isPause()) {
            return;
        }
        switch (code) {
            case HConstant.PREPARE_COMPLETE:
                duration = mInteractiveHandler.getDuration();
                timeFormat = HVideoUtils.getTimeFormat(duration);
                updateLongTime(HVideoUtils.getTime(duration, timeFormat));
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!isTouchSeekBar) {
            return;
        }
        currentPosition = HVideoUtils.toTime(progress, duration);
        mInteractiveHandler.onProgress(currentPosition);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isTouchSeekBar = true;
        mInteractiveHandler.onProgressDown(duration);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isTouchSeekBar = false;
        mInteractiveHandler.onProgressStop(currentPosition);
    }

    protected final void play() {
        if (mInteractiveHandler.isPlaying()) {
            mInteractiveHandler.pause();
        } else {
            mInteractiveHandler.play();
        }
    }

    public abstract void updateTime(String time);

    public abstract void updateLongTime(String time);

    public abstract void updateProgress(int progress);

    public abstract void updateBufferedProgress(int bufferedProgress);
}
