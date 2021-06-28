package com.hyg.videoui.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.hyg.videoui.R;
import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/11 0011
 * @Desc
 */
public class FuncHorizontalProgressController extends AbstractProgressController {

    private final View mView;

    public FuncHorizontalProgressController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider) {
        super(containerView, operateProvider);
        mView = LayoutInflater.from(mContext).inflate(R.layout.video_bottom_horizontal_layout,null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        addView(mView,params);
    }

    @Override
    public void updateTime(String time) {

    }

    @Override
    public void updateLongTime(String time) {

    }

    @Override
    public void updateProgress(int progress) {

    }

    @Override
    public void updateBufferedProgress(int bufferedProgress) {

    }

}
