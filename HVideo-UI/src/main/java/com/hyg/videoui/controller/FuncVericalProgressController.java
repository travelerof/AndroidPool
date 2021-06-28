package com.hyg.videoui.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hyg.videoui.HConstant;
import com.hyg.videoui.R;
import com.hyg.videoui.utils.HVideoUtils;
import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/11 0011
 * @Desc
 */
public class FuncVericalProgressController extends AbstractProgressController {

    private final View mView;
    private ImageView mIvPlay;
    private TextView mTvTime;
    private SeekBar mSeekBar;
    private TextView mTvTotalTime;
    private ImageView mIvScreen;

    public FuncVericalProgressController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider) {
        super(containerView, operateProvider);
        mView = LayoutInflater.from(mContext).inflate(R.layout.video_bottom_vertical_layout,null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mContext.getResources().getDimensionPixelSize(R.dimen.dimen_45));
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        addView(mView,params);
        init();
    }

    private void init() {
        mIvPlay = mView.findViewById(R.id.video_bottom_verical_play_iv);
        mTvTime = mView.findViewById(R.id.video_bottom_verical_time_tv);
        mSeekBar = mView.findViewById(R.id.video_bottom_verical_seekbar);
        mTvTotalTime = mView.findViewById(R.id.video_bottom_verical_total_time_tv);
        mIvScreen = mView.findViewById(R.id.video_bottom_verical_screen_changed_iv);
        mIvPlay.setOnClickListener(this::click);
        mIvScreen.setOnClickListener(this::click);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void operate(int code) {
        super.operate(code);
        switch (code) {
            case HConstant.PLAYING:
                mIvPlay.setImageResource(R.mipmap.ic_pause);
                break;
            case HConstant.PAUSE:
                mIvPlay.setImageResource(R.mipmap.ic_play);
                break;
        }
    }

    @Override
    public void updateTime(String time) {
        mTvTime.setText(time);
    }

    @Override
    public void updateLongTime(String time) {
        mTvTotalTime.setText(time);
    }

    @Override
    public void updateProgress(int progress) {
        mSeekBar.setProgress(progress);
    }

    @Override
    public void updateBufferedProgress(int bufferedProgress) {
        mSeekBar.setSecondaryProgress(bufferedProgress);
    }

    private void click(View v){
        int id = v.getId();
        if (id == R.id.video_bottom_verical_play_iv) {
            play();
        }else if (id == R.id.video_bottom_verical_screen_changed_iv){

        }
    }

}
