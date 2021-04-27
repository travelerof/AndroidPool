package com.hyg.suspendNetwork;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;

/**
 * @Author 韩永刚
 * @Date 2021/03/25
 * @Desc
 */
class SuspendManager {
    private static SuspendManager suspendManager;
    private Activity mActivity;
    private boolean isDebug;
    private int width;
    private int height;
    private View rootView;
    private final WindowManager mWindowManager;
    private final WindowManager.LayoutParams mParams;
    private boolean show;

    private SuspendManager() {
        mWindowManager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
    }

    public static SuspendManager getInstance() {
        if (suspendManager == null) {
            synchronized (SuspendManager.class) {
                if (suspendManager == null) {
                    suspendManager = new SuspendManager();
                }
            }
        }
        return suspendManager;
    }

    public SuspendManager init(Activity activity, boolean isDebug) {
        suspendManager.mActivity = activity;
        suspendManager.isDebug = isDebug;
        return suspendManager;
    }


    public boolean isShowing() {
        return show;
    }

    public void show() {
        if (!isDebug) {
            return;
        }
        if (mWindowManager == null || isShowing()) {
            return;
        }
        mWindowManager.addView(rootView, mParams);
        show = true;
    }

    public void cancel() {
        if (!isDebug) {
            return;
        }
        if (mWindowManager == null || !show) {
            return;
        }
        mWindowManager.removeView(rootView);
        show = false;
    }

}
