package com.hyg.hvideo.exo;

import android.content.Context;

import com.hyg.hvideo.HVideoPlayerFactory;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public class HExoFactory extends HVideoPlayerFactory<HExoPlayer> {

    private HExoFactory() {
    }

    public static HVideoPlayerFactory create() {
        return new HExoFactory();
    }

    @Override
    public HExoPlayer createPlayer(Context context) {
        return new HExoPlayer(context);
    }
}
