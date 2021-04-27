package com.hyg.widgets.dialog;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * @Author 韩永刚
 * @Date 2021/02/01
 * @Desc
 */
public interface IViewHandle {

    @NonNull
    View getView();

    void handle(@NonNull CustomDialog dialog, @NonNull BuilderConfig config);
}
