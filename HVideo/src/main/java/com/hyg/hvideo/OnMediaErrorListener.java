package com.hyg.hvideo;

import androidx.annotation.NonNull;

import com.hyg.hvideo.model.Error;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public interface OnMediaErrorListener {

    void onError(@NonNull Error error);
}
