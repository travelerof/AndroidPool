package com.hyg.videoui.controller;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

/**
 * @Author 韩永刚
 * @Date 2021/06/28
 * @Desc
 */
class NetworkController extends AbstractController {
    public NetworkController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider) {
        super(containerView, operateProvider);
    }

    @Override
    public void operate(int code) {

    }
}
