package com.hyg.hvideo;

import android.content.Context;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

/**
 * @Author 韩永刚
 * @Date 2021/04/02
 * @Desc
 */
class HmMediaPlayerManager {

    private static HmMediaPlayerManager mManager;
    private final SimpleExoPlayer mPlayer;
    private int currentPosition = 0;

    private HmMediaPlayerManager(Context context) {
        mPlayer = new SimpleExoPlayer.Builder(context).build();
        init();
    }

    public static HmMediaPlayerManager getInstance(Context context) {
        if (mManager == null) {
            synchronized (context) {
                if (mManager == null) {
                    mManager = new HmMediaPlayerManager(context);
                }
            }
        }
        return mManager;
    }

    private void init() {

    }

    public HmMediaPlayerManager bindPlayView(PlayerView view){

        return mManager;
    }
    public HmMediaPlayerManager reset() {

        return mManager;
    }

    public void play() {
        if (mPlayer != null && !mPlayer.isPlaying()) {
            mPlayer.play();
        }
    }

    public void stop() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    public void recycle() {
        if (mPlayer != null) {
            mPlayer.clearMediaItems();
            mPlayer.clearVideoSurface();
            mPlayer.clearAuxEffectInfo();
        }
    }
}
