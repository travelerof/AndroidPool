package com.hyg.videoui.widget;

/**
 * @Author hanyonggang
 * @Date 2021/5/10 0010
 * @Desc 交互
 */
public interface InteractiveHandler {

    void back();

    void prepare();

    void play();

    void pause();

    void stop();

    void reset();

    void seekTo(long currentPosition);

    long getDuration();

    long getCurrentPosition();

    long getBufferedPosition();

    void setVolume(float volume);

    float getVolume();

    void setSpeed(float speed);

    float getSpeed();

    boolean isPlaying();


    void onProgressDown(long duration);

    void onProgress(long currentPosition);

    void onProgressStop(long currentPosition);
}
