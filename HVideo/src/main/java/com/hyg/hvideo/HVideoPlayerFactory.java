package com.hyg.hvideo;

import android.content.Context;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public abstract class HVideoPlayerFactory<T extends HVideoPlayer> {

    public abstract T createPlayer(Context context);
}
