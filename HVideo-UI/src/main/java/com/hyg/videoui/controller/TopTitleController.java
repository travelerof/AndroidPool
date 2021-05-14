package com.hyg.videoui.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hyg.videoui.AnimationStatus;
import com.hyg.videoui.Direction;
import com.hyg.videoui.HConstant;
import com.hyg.videoui.R;
import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/10
 * @Desc
 */
public class TopTitleController extends AbstractAnimationController {

    private final View mView;
    private final ImageView mIvBack;
    private final TextView mTvTitle;

    private int mViewHeight;

    @Direction
    int direction;
    public TopTitleController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider, @Direction int direction) {
        super(containerView, operateProvider);
        mView = LayoutInflater.from(mContext).inflate(R.layout.video_top_title_layout, null);
        mIvBack = mView.findViewById(R.id.video_top_title_back_iv);
        mTvTitle = mView.findViewById(R.id.video_top_title_info_tv);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mContext.getResources().getDimensionPixelSize(R.dimen.dimen_45));
        addView(mView, params);
        mIvBack.setOnClickListener(this::onClick);

    }

    @Override
    public void startAnimation(int maxAnimValue) {
        super.startAnimation(maxAnimValue);
        mViewHeight = mView.getHeight();
        animStatus = mView.getVisibility() == View.VISIBLE ? AnimationStatus.HIDE : AnimationStatus.SHOW;
        mView.setVisibility(View.VISIBLE);
    }

    @Override
    public void animationing(int value) {
        float v;
        if (animStatus == AnimationStatus.SHOW) {
            v = (float) (mViewHeight * value / (double) maxValue) - mViewHeight;
        } else {
            v = 0 - (float) (mViewHeight * value / (double) maxValue);
        }
        mView.setTranslationY(v);
    }

    @Override
    public void endAnimation() {
        if (animStatus == AnimationStatus.HIDE) {
            mView.setTranslationY(-mViewHeight);
            mView.setVisibility(View.GONE);
        } else {
            mView.setTranslationY(0);
        }
    }

    @Override
    public void operate(int code) {
    }


    private void onClick(View v) {
        if (isPause()) {
            return;
        }
        if (v.getId() == R.id.video_top_title_back_iv) {
            mInteractiveHandler.back();
        }
    }

    @Override
    protected boolean isVisible() {
        return mView.getVisibility() == View.VISIBLE;
    }
}
