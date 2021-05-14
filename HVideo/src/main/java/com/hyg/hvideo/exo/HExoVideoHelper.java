package com.hyg.hvideo.exo;

import android.text.TextUtils;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.hyg.hvideo.HVideoSource;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public class HExoVideoHelper {

    private MediaSourceFactory mMediaSourceFactory;

    public HExoVideoHelper(MediaSourceFactory mediaSourceFactory) {
        mMediaSourceFactory = mediaSourceFactory;
    }

    public MediaSource getMedia(HVideoSource videoSource) {
        if (videoSource == null) {
            return null;
        }
        MediaItem mediaItem = MediaItem.fromUri(videoSource.getUrl());
        return mMediaSourceFactory.createMediaSource(mediaItem);
    }
}
