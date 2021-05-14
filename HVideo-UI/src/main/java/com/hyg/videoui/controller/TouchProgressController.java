package com.hyg.videoui.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hyg.videoui.HConstant;
import com.hyg.videoui.R;
import com.hyg.videoui.utils.HVideoUtils;
import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/13 0013
 * @Desc 触摸滑动显示的控制器
 */
public class TouchProgressController extends AbstractController {

    private final View mView;
    private final ImageView mIvPreview;
    private final TextView mTvTime;
    private long duration;
    private String timeFormat = "mm:ss";

    public TouchProgressController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider) {
        super(containerView, operateProvider);
        mView = LayoutInflater.from(mContext).inflate(R.layout.video_touch_progress_layout, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mView, params);
        mIvPreview = mView.findViewById(R.id.video_touch_progress_preview_iv);
        mTvTime = mView.findViewById(R.id.video_touch_progress_time_tv);
    }

    @Override
    public void operate(int code) {
        if (isPause()) {
            return;
        }
        switch (code){
            case HConstant.SHOW:
                mView.setVisibility(View.VISIBLE);
                break;
            case HConstant.HIDE:
                mView.setVisibility(View.GONE);
                break;
        }
    }

    public void setDuration(long duration) {
        this.duration = duration;
        timeFormat = HVideoUtils.getTimeFormat(duration);
    }

    public void updateProgress(long currentPosition) {
        if (isPause()) {
            return;
        }
        mTvTime.setText(HVideoUtils.getTime(currentPosition, timeFormat));
    }
}
