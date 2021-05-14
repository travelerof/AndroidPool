package com.hyg.hvideo;

import androidx.annotation.NonNull;

import com.hyg.hvideo.model.PlayInfo;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public interface OnMediaPlayListener {
    void onPlayInfo(@NonNull PlayInfo playInfo);
}
