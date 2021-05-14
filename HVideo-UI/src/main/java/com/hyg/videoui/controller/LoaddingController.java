package com.hyg.videoui.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.hyg.videoui.HConstant;
import com.hyg.videoui.R;
import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public class LoaddingController extends AbstractController {

    private final View mView;

    public LoaddingController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider) {
        super(containerView, operateProvider);
        mView = LayoutInflater.from(mContext).inflate(R.layout.video_loadding_layout, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mView, params);
    }

    @Override
    public void operate(int code) {
        if (isPause()) {
            return;
        }
        switch (code) {
            case HConstant.SHOW:
            case HConstant.PREPARE_START:
            case HConstant.BUFFERING:
                mView.setVisibility(View.VISIBLE);
                break;
            case HConstant.HIDE:
            case HConstant.PREPARE_COMPLETE:
            case HConstant.BUFFER_COMPLETE:
                mView.setVisibility(View.GONE);
                break;
        }
    }

}
