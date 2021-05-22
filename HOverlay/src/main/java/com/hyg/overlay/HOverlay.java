package com.hyg.overlay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hyg.hpermission.HPermission;
import com.hyg.hpermission.HPermissionUtils;
import com.hyg.hpermission.OnPermissionCallback;
import com.hyg.hpermission.permission.Permission;
import com.hyg.hpermission.request.RequestType;

import java.util.List;


/**
 * @Author hanyonggang
 * @Date 2021/5/15 0015
 * @Desc
 */
public class HOverlay implements OnLifecycleListener {

    private Context mContext;
    private BuilderOptions mOptions;
    private final WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private float startX;
    private float startY;
    private int mScreenWidth;
    private int mScreenHeight;
    private ValueAnimator mValueAnimator;
    private boolean isShow;

    private HOverlay(@NonNull Context context, @NonNull BuilderOptions options) {
        mContext = context;
        mOptions = options;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LifecycleControl.getInstance().addActivityListener(this);
        initLayoutParams();
        initView();
    }

    private void initView() {
        if (mOptions.view == null) {
            mOptions.view = getDefaultView();
        }
    }

    private void initLayoutParams() {
        mParams = new WindowManager.LayoutParams();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        //设置宽高
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mParams.format = PixelFormat.TRANSLUCENT;

        mParams.type = getWinwoType();

        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

        mParams.gravity = mOptions.gravity;

        mParams.x = 0;
        mParams.y = 100;
    }

    private int getWinwoType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        return WindowManager.LayoutParams.TYPE_PHONE;
    }

    private View getDefaultView() {
        TextView textView = new TextView(mContext);
        textView.setText("悬浮");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(12.0f);
        textView.setBackgroundResource(R.drawable.shape_float_ball);
        textView.setOnTouchListener(this::onTouch);
        textView.setOnClickListener(this::click);
        return textView;
    }

    public void show() {
        if (isShow()) {
            return;
        }
        if (!HPermissionUtils.hasOverlayPermission(mContext)) {
            requestOverlayPermission();
           return;
        }
        startAnimation();
    }

    public void dismiss() {
        if (isShow()) {
            startAnimation();
        }
    }

    public boolean isShow() {
        return isShow && mOptions.view.getVisibility() == View.VISIBLE;
    }

    private void requestOverlayPermission() {
        Activity activity = LifecycleControl.getInstance().getActivity();
        if (activity == null) {
            return;
        }
        HPermission.with(activity)
                .requestCode(100)
                .requestType(RequestType.SPECIAL_PERMISSION)
                .permission(Permission.APPLICATION_WINDOW_OVERLAY)
                .callBack(new OnPermissionCallback() {
                    @Override
                    public void onSuccess(int requestCode, List<String> permission) {
                        show();
                    }

                    @Override
                    public void onFailed(int requestCode, List<String> permission) {

                    }
                })
                .request();
    }

    private void startAnimation() {
        if (mValueAnimator == null) {
            mValueAnimator = ValueAnimator.ofFloat(0, 1.0f);
            mValueAnimator.setDuration(200);
            mValueAnimator.addUpdateListener(animation -> {
                float value = (float) animation.getAnimatedValue();
                if (isShow) {
                    value = 1 - value;
                }
                mOptions.view.setScaleX(value);
                mOptions.view.setScaleY(value);
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (isShow) {
                        mOptions.view.setVisibility(View.GONE);
                        mWindowManager.removeView(mOptions.view);
                        isShow = false;
                    } else {
                        isShow = true;
                    }
                    if (mOptions.mOnOverlyListener != null) {
                        mOptions.mOnOverlyListener.onBallStatus(!isShow);
                    }
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (!isShow) {
                        mWindowManager.addView(mOptions.view, mParams);
                        mOptions.view.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        mValueAnimator.start();
    }

    private boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getRawX() - startX;
                float deltaY = event.getRawY() - startY;
                startX = event.getRawX();
                startY = event.getRawY();
                mParams.x += deltaX;
                mParams.y += deltaY;
                if (mParams.x < 0) {
                    mParams.x = 0;
                }
                int valueX = mScreenWidth - mOptions.view.getWidth();
                if (mParams.x > valueX) {
                    mParams.x = valueX;
                }
                if (mParams.y < 0) {
                    mParams.y = 0;
                }
                int valueY = mScreenHeight - mOptions.view.getHeight();
                if (mParams.y > valueY) {
                    mParams.y = valueY;
                }
                mWindowManager.updateViewLayout(mOptions.view, mParams);
                break;
            case MotionEvent.ACTION_UP:
                absorbAnimation();
                break;
        }
        return false;
    }

    /**
     * 吸附动画
     */
    private void absorbAnimation() {
        int x = mParams.x;
        if (x <= 0 || x >= mScreenWidth - mOptions.view.getWidth()) {
            return;
        }
        ValueAnimator valueAnimator;
        if (x <= mScreenWidth / 2) {
            valueAnimator = ValueAnimator.ofInt(mParams.x, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(mParams.x, mScreenWidth - mOptions.view.getWidth());
        }
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            mParams.x = value;
            mWindowManager.updateViewLayout(mOptions.view, mParams);
        });
        valueAnimator.start();
    }

    private void click(View v) {
        if (mOptions.mOnOverlyListener != null) {
            mOptions.mOnOverlyListener.onBallClick(v);
        }
    }

    @Override
    public void start() {
        show();
    }

    @Override
    public void stop() {
        if (LifecycleControl.getInstance().getActivityCount() <= 0) {
            dismiss();
        }
    }

    public static class Builder {
        private Context mContext;
        private BuilderOptions mOptions;

        public Builder(Context context) {
            mContext = context;
            mOptions = new BuilderOptions();
        }

        public Builder addView(View view) {
            mOptions.view = view;
            return this;
        }

        public Builder setTouch(boolean touch) {
            mOptions.touch = touch;
            return this;
        }

        public Builder setGravity(int gravity) {
            mOptions.gravity = gravity;
            return this;
        }

        public Builder setOnFloatBallListener(OnOverlyListener onOverlyListener) {
            mOptions.mOnOverlyListener = onOverlyListener;
            return this;
        }

        public HOverlay create() {
            return new HOverlay(mContext, mOptions);
        }
    }

    private static class BuilderOptions {
        public View view;
        public boolean touch = true;
        public int gravity = Gravity.LEFT | Gravity.TOP;
        public OnOverlyListener mOnOverlyListener;
    }
}
