package com.hyg.videoui.controller;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hyg.videoui.AnimationStatus;
import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/10 0010
 * @Desc
 */
public abstract class AbstractAnimationController extends AbstractController {

    protected int maxValue;
    @AnimationStatus
    int animStatus;

    public AbstractAnimationController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider) {
        super(containerView, operateProvider);
    }

    public void startAnimation(int maxAnimValue) {
        maxValue = maxAnimValue;
    }

    public abstract void animationing(int value);

    public abstract void endAnimation();

    protected boolean isVisible() {
        return false;
    }
}
