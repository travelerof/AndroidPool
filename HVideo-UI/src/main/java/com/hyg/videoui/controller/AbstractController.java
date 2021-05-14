package com.hyg.videoui.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hyg.videoui.widget.InteractiveHandler;


/**
 * @Author hanyonggang
 * @Date 2021/5/10
 * @Desc
 */
public abstract class AbstractController {
    protected Context mContext;
    protected ViewGroup mContainerView;
    protected InteractiveHandler mInteractiveHandler;
    /**
     * 当前控制器是否被挂起
     */
    private boolean pause;

    public AbstractController(@NonNull ViewGroup containerView, @NonNull InteractiveHandler operateProvider) {
        mContainerView = containerView;
        mInteractiveHandler = operateProvider;
        mContext = containerView.getContext();
    }

    protected void addView(View view) {
        mContainerView.addView(view);
    }

    protected void addView(View view, ViewGroup.LayoutParams params) {
        mContainerView.addView(view, params);
    }

    public abstract void operate(int code);

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isPause() {
        return pause;
    }

    public void release(){
        mInteractiveHandler = null;
        mContainerView = null;
        mContext = null;
    }
}
