package com.hyg.videoui.controller;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.hyg.videoui.widget.InteractiveHandler;

import org.jetbrains.annotations.NotNull;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public class GestureController extends AbstractController {

    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mOnGestureListener != null) {
                mOnGestureListener.onSingleTap();
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return super.onDoubleTap(e);
        }
    };
    private GestureDetector mGestureDetector;
    private OnGestureListener mOnGestureListener;

    public GestureController(@NonNull @NotNull ViewGroup containerView, @NonNull @NotNull InteractiveHandler operateProvider,OnGestureListener onGestureListener) {
        super(containerView, operateProvider);
        this.mOnGestureListener = onGestureListener;
        View view = new View(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setOnTouchListener(this::onTouch);
        mGestureDetector = new GestureDetector(mContext, mSimpleOnGestureListener);
    }

    @Override
    public void operate(int code) {

    }

    private boolean onTouch(View v, MotionEvent event) {
        if (!isPause() && mGestureDetector != null) {
            return mGestureDetector.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public void release() {
        super.release();
        mGestureDetector = null;
        mSimpleOnGestureListener = null;
    }
}
