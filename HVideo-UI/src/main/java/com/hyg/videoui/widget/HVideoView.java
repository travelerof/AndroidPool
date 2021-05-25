package com.hyg.videoui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hyg.hlog.HLog;
import com.hyg.hvideo.HVideoConstant;
import com.hyg.hvideo.HVideoPlayer;
import com.hyg.hvideo.HVideoSource;
import com.hyg.hvideo.OnMediaErrorListener;
import com.hyg.hvideo.OnMediaPlayListener;
import com.hyg.hvideo.exo.HExoFactory;
import com.hyg.hvideo.model.Error;
import com.hyg.hvideo.model.PlayInfo;
import com.hyg.videoui.Direction;
import com.hyg.videoui.HConstant;
import com.hyg.videoui.R;
import com.hyg.videoui.controller.AbstractController;
import com.hyg.videoui.controller.AnimationControllerHelper;
import com.hyg.videoui.controller.BottomFuncController;
import com.hyg.videoui.controller.GestureController;
import com.hyg.videoui.controller.LoaddingController;
import com.hyg.videoui.controller.OnGestureListener;
import com.hyg.videoui.controller.TopTitleController;
import com.hyg.videoui.controller.TouchProgressController;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/10 0010
 * @Desc
 */
public class HVideoView extends FrameLayout implements InteractiveHandler, OnMediaErrorListener, OnMediaPlayListener, OnGestureListener {

    private static final String TAG = HVideoView.class.getSimpleName();

    private static final int MAX_ANIM_VALUE = 100;
    private static final long DURATION = 200;
    private static final long CLOCK = 15 * 1000;

    private int direction = Direction.VERICAL;
    private Context mContext;
    private HVideoPlayer mVideoPlayer;
    private FrameLayout mVideoLayout;
    private RelativeLayout mMiddleLayout;
    private RelativeLayout mTopLayout;
    private TopTitleController mTopTitleController;
    private BottomFuncController mBottomFuncController;

    private AnimationControllerHelper mAnimationControllerHelper;
    private GestureController mGestureController;
    private AbstractController mLoaddingController;
    private TouchProgressController mTouchProgressController;


    public HVideoView(@NonNull Context context) {
        this(context, null);
    }

    public HVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        initPlayer();
        initView();
        mAnimationControllerHelper.startClock();
    }

    private void initPlayer() {
        mVideoPlayer = HExoFactory.create().createPlayer(mContext);
        mVideoPlayer.addErrorListener(this);
        mVideoPlayer.addMediaPlayListener(this);
        mVideoPlayer.initPlayer();
    }

    private void initView() {
        inflate(mContext, R.layout.video_layout, this);
        SurfaceView surfaceView = new SurfaceView(mContext);
        mVideoLayout = findViewById(R.id.video_controller_layout);
        mMiddleLayout = findViewById(R.id.middle_space_layout);
        mTopLayout = findViewById(R.id.top_space_layout);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        mVideoLayout.addView(surfaceView,params);
        mVideoPlayer.setSurface(surfaceView.getHolder().getSurface());
        //初始化手势控制器
        mGestureController = new GestureController(mMiddleLayout,this,this);
        //触摸后显示滑动进度控制器
        mTouchProgressController = new TouchProgressController(mMiddleLayout,this);
        //初始化动画控制器
        mAnimationControllerHelper = new AnimationControllerHelper(MAX_ANIM_VALUE, DURATION, CLOCK);

        //加载中控制器
        mLoaddingController = new LoaddingController(mMiddleLayout,this);

        mTopTitleController = new TopTitleController(mTopLayout, this,direction);
        mAnimationControllerHelper.addController(mTopTitleController);

        mBottomFuncController = new BottomFuncController(mTopLayout, this, direction);
        mAnimationControllerHelper.addController(mBottomFuncController);
    }


    public void setVideoSource(HVideoSource videoSource){
        mVideoPlayer.addVideoSource(videoSource);
        prepare();
    }
    @Override
    public void back() {
        if (mContext instanceof Activity) {
            ((Activity)mContext).finish();
        }
    }

    @Override
    public void prepare() {
        mVideoPlayer.prepare();
    }

    @Override
    public void play() {
        mVideoPlayer.play();
    }

    @Override
    public void pause() {
        mVideoPlayer.pause();
    }

    @Override
    public void stop() {
        mVideoPlayer.stop();
    }

    @Override
    public void reset() {
        mVideoPlayer.reset();
    }

    @Override
    public void seekTo(long currentPosition) {
        mVideoPlayer.seekTo(currentPosition);
    }

    @Override
    public long getDuration() {
        return mVideoPlayer.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return mVideoPlayer.getCurrentPosition();
    }

    @Override
    public long getBufferedPosition() {
        return mVideoPlayer.getBufferedPosition();
    }

    @Override
    public void setVolume(float volume) {
        mVideoPlayer.setVolume(volume);
    }

    @Override
    public float getVolume() {
        return 0;
    }

    @Override
    public void setSpeed(float speed) {
        mVideoPlayer.setSpeed(speed);
    }

    @Override
    public float getSpeed() {
        return mVideoPlayer.getSpeed();
    }

    @Override
    public boolean isPlaying() {
        return mVideoPlayer.isPlaying();
    }

    @Override
    public void onProgressDown(long duration) {
        pause();
        mTouchProgressController.setDuration(duration);
        mTouchProgressController.operate(HConstant.SHOW);
    }

    @Override
    public void onProgress(long currentPosition) {
        mTouchProgressController.updateProgress(currentPosition);
    }

    @Override
    public void onProgressStop(long currentPosition) {
        seekTo(currentPosition);
        play();
        mTouchProgressController.operate(HConstant.HIDE);

    }

    @Override
    protected void onDetachedFromWindow() {
        release();
        super.onDetachedFromWindow();
    }

    private void release() {
        mVideoPlayer.release();
        mTopTitleController.release();
        mBottomFuncController.release();
    }

    /**
     * 屏幕切换
     *
     * @param newConfig
     */
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onError(@NonNull @NotNull Error error) {
        mLoaddingController.operate(HConstant.HIDE);
        if (mAnimationControllerHelper.isShow()) {
            mAnimationControllerHelper.start();
        }
        mGestureController.setPause(true);
    }

    @Override
    public void onPlayInfo(@NonNull @NotNull PlayInfo playInfo) {
        HLog.i(TAG,">>>>>>>>>onPlayInfo>>>>>>>>>>code:"+playInfo.code);
        switch (playInfo.code) {
            case HVideoConstant.STATUS.PREPARE_START:
                mLoaddingController.operate(HConstant.PREPARE_START);
                mBottomFuncController.operate(HConstant.PREPARE_START);
                break;
            case HVideoConstant.STATUS.PREPARE_COMPLETE:
                mLoaddingController.operate(HConstant.PREPARE_COMPLETE);
                mBottomFuncController.operate(HConstant.PREPARE_COMPLETE);
                play();
                break;
            case HVideoConstant.STATUS.PLAYING:
                mBottomFuncController.operate(HConstant.PLAYING);
                break;
            case HVideoConstant.STATUS.PAUSE:
                mBottomFuncController.operate(HConstant.PAUSE);
                break;
            case HVideoConstant.STATUS.BUFFERING:
                mLoaddingController.operate(HConstant.BUFFERING);
                break;
            case HVideoConstant.STATUS.BUFFER_COMPLETE:
                mLoaddingController.operate(HConstant.BUFFER_COMPLETE);
                break;
        }
    }

    @Override
    public void onSingleTap() {
        mAnimationControllerHelper.start();
    }
}
