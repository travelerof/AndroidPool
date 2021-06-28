package com.hyg.videoui.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.hyg.hlog.HLog;
import com.hyg.videoui.AnimationStatus;
import com.hyg.videoui.Direction;
import com.hyg.videoui.HConstant;
import com.hyg.videoui.R;
import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author hanyonggang
 * @Date 2021/5/10 0010
 * @Desc
 */
public class FuncController extends AbstractAnimationController {

    private static final String TAG = FuncController.class.getSimpleName();

    private int mViewHeight;
    private final RelativeLayout mBottomLayout;

    @Direction
    int direction;

    private AbstractProgressController mController;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean isTimer;
    public FuncController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider, @Direction int direction) {
        super(containerView, operateProvider);
        this.direction = direction;
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_bottom_func_layout, null);
        mBottomLayout = view.findViewById(R.id.video_bottom_func_layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(view, params);
        init();
    }

    private void init() {

        if (direction == Direction.HORIZONTAL) {
            //初始化横向时底部菜单
            mController = new FuncHorizontalProgressController(mBottomLayout, mInteractiveHandler);
        } else {
            //初始化纵向时底部菜单
            mController = new FuncVericalProgressController(mBottomLayout, mInteractiveHandler);
        }

    }

    @Override
    public void startAnimation(int maxAnimValue) {
        super.startAnimation(maxAnimValue);
        mViewHeight = mBottomLayout.getHeight();
        animStatus = mBottomLayout.getVisibility() == View.VISIBLE ? AnimationStatus.HIDE : AnimationStatus.SHOW;
        mBottomLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void animationing(int value) {
        float v;
        if (animStatus == AnimationStatus.SHOW) {
            v = mViewHeight - (float) (mViewHeight * value / (double) maxValue);
        } else {
            v = (float) (mViewHeight * value / (double) maxValue);
        }
        mBottomLayout.setTranslationY(v);
    }

    @Override
    public void endAnimation() {
        if (animStatus == AnimationStatus.HIDE) {
            mBottomLayout.setTranslationY(-mViewHeight);
            mBottomLayout.setVisibility(View.GONE);
        } else {
            mBottomLayout.setTranslationY(0);
        }
    }

    @Override
    public void operate(int code) {
        HLog.i(TAG,"================code=========="+code);
        switch (code){
            case HConstant.PREPARE_START:
                startTimer();
                break;
            case HConstant.PAUSE:
                break;
            case HConstant.COMPLETE:
                stopTimer();
                break;
        }
        mController.operate(code);
    }

    @Override
    protected boolean isVisible() {
        return mBottomLayout.getVisibility() == View.VISIBLE;
    }

    private void startTimer(){
        if (isTimer) {
            return;
        }
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    mContainerView.post(() -> mController.updateProgress());
                }
            };
        }
        mTimer.schedule(mTimerTask,0,1000);
        isTimer = true;
    }

    public void stopTimer(){
        isTimer = false;
        mTimer.cancel();
        mTimer = null;
        mTimerTask.cancel();
        mTimerTask = null;
    }
    @Override
    public void release() {
        stopTimer();
        super.release();
        mController.release();

    }
}
