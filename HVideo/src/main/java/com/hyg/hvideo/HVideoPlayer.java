package com.hyg.hvideo;

import android.content.Context;
import android.view.Surface;

import androidx.annotation.NonNull;

import com.hyg.hvideo.model.Error;
import com.hyg.hvideo.model.PlayInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public abstract class HVideoPlayer {

    private Context mContext;
    protected OnMediaErrorListener mOnMediaErrorListener;
    protected OnMediaPlayListener mOnMediaPlayListener;
    protected List<HVideoSource> mVideoSources;
    protected int currentIndex;

    public HVideoPlayer(@NonNull Context context) {
        mContext = context;
        mVideoSources = new ArrayList<>();
    }

    public final Context getContext() {
        return mContext;
    }

    /**
     * 初始化播放器
     */
    public abstract void initPlayer();

    /**
     * 准备视频
     */
    public abstract void prepare();

    /**
     * 开始播放
     */
    public abstract void play();

    /**
     * 暂停
     */
    public abstract void pause();

    /**
     * 停止播放
     */
    public abstract void stop();

    /**
     * 重置播放器
     */
    public abstract void reset();

    public abstract boolean isPlaying();

    /**
     * 设置播放位置
     *
     * @param currentPosition
     */
    public abstract void seekTo(long currentPosition);

    /**
     * 获取视频总长度
     *
     * @return
     */
    public abstract long getDuration();

    public abstract void setCurrentPosition(int currentPosition);

    /**
     * 获取视频当前播放位置
     *
     * @return
     */
    public abstract long getCurrentPosition();

    /**
     * 设置播放速度
     *
     * @param speed
     */
    public abstract void setSpeed(float speed);

    /**
     * 获取播放速度
     *
     * @return
     */
    public abstract float getSpeed();

    /**
     * 设置音量
     *
     * @param volume
     */
    public abstract void setVolume(float volume);

    /**
     * 获取当前缓冲位置
     *
     * @return
     */
    public abstract long getBufferedPosition();

    public abstract void setSurface(@NonNull Surface surface);

    public void addVideoSource(@NonNull HVideoSource videoSource) {
        mVideoSources.add(videoSource);
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void addErrorListener(OnMediaErrorListener onMediaErrorListener) {
        mOnMediaErrorListener = onMediaErrorListener;
    }

    public void addMediaPlayListener(OnMediaPlayListener onMediaPlayListener) {
        mOnMediaPlayListener = onMediaPlayListener;
    }

    protected void setInfo(@NonNull PlayInfo info) {
        if (mOnMediaPlayListener == null) {
            return;
        }
        mOnMediaPlayListener.onPlayInfo(info);
    }

    protected void setError(@NonNull Error error) {
        if (mOnMediaErrorListener == null) {
            return;
        }
        mOnMediaErrorListener.onError(error);
    }

    public void release() {
        mContext = null;
        mVideoSources.clear();

    }
}
