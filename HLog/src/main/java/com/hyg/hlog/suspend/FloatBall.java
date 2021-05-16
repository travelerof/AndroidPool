package com.hyg.hlog.suspend;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hyg.hdialog.HDialog;
import com.hyg.hlog.R;

/**
 * @Author hanyonggang
 * @Date 2021/5/15 0015
 * @Desc
 */
public class FloatBall implements OnActivityListener {

    private Context mContext;
    private BuilderOptions mOptions;
    private final WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private final ActivityControl mActivityControl;
    private Activity currentActivity;
    private boolean isUserActive;
    private float startX;
    private float startY;
    private HDialog mDialog;
    private int mScreenWidth;
    private int mScreenHeight;

    private FloatBall(@NonNull Context context, @NonNull BuilderOptions options) {
        mContext = context;
        mOptions = options;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mActivityControl = ActivityControl.getInstance();
        mActivityControl.addActivityListener(this);
        initLayoutParams();
        initView();
        initDialog();
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

    private void initDialog() {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.item_log_dialog_layout,null);
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        mDialog = new HDialog.Builder(mContext)
                .width(displayMetrics.widthPixels)
                .height(displayMetrics.heightPixels/2)
                .gravity(Gravity.BOTTOM)
                .view(dialogView)
                .create();
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
        mWindowManager.addView(mOptions.view, mParams);
    }

    public void dismiss() {
        mWindowManager.removeView(mOptions.view);
    }

    public boolean isShow() {
        return mOptions.view.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onResume(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onStop(Activity activity) {
        if (currentActivity == activity) {
            currentActivity = null;
        }
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
                if (mParams.x < 0){
                    mParams.x = 0;
                }
                int valueX = mScreenWidth - mOptions.view.getWidth();
                if (mParams.x > valueX){
                    mParams.x = valueX;
                }
                if (mParams.y < 0){
                    mParams.y = 0;
                }
                int valueY = mScreenHeight - mOptions.view.getHeight();
                if (mParams.y > valueY){
                    mParams.y = valueY;
                }
                mWindowManager.updateViewLayout(mOptions.view,mParams);
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
    private void absorbAnimation(){
        int x = mParams.x;
        if (x <= 0 || x >= mScreenWidth - mOptions.view.getWidth()) {
            return;
        }
        ValueAnimator valueAnimator;
        if (x <= mScreenWidth/2 ) {
            valueAnimator = ValueAnimator.ofInt(mParams.x,0);
        }else {
            valueAnimator = ValueAnimator.ofInt(mParams.x,mScreenWidth - mOptions.view.getWidth());
        }
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            mParams.x = value;
            mWindowManager.updateViewLayout(mOptions.view,mParams);
        });
        valueAnimator.start();
    }
    private void click(View v){
        if (mDialog == null) {
            return;
        }
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }else {
            mDialog.show();
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

        public FloatBall create() {
            return new FloatBall(mContext, mOptions);
        }
    }

    private static class BuilderOptions {
        public View view;
        public boolean touch = true;
        public int gravity = Gravity.LEFT | Gravity.TOP;
    }
}
