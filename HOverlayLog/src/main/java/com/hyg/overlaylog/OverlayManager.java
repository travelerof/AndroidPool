package com.hyg.overlaylog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.hyg.overlay.HOverlay;
import com.hyg.overlay.LifecycleControl;
import com.hyg.overlay.OnOverlyListener;
import com.hyg.overlaylog.log.LogDialog;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
final class OverlayManager {

    public static OverlayManager manager = new OverlayManager();
    private HOverlay mOverlay;
    private Activity currentActivity;
    private LogDialog mLogDialog;

    private OverlayManager() {

    }

    public static OverlayManager getInstance() {
        return manager;
    }

    public void init() {
        Context context = LifecycleControl.getInstance().getContext();
        if (context == null) {
            return;
        }
        mOverlay = new HOverlay.Builder(context)
                .setOnFloatBallListener(new OnOverlyListener() {
                    @Override
                    public void onBallStatus(boolean show) {

                    }

                    @Override
                    public void onBallClick(View v) {
                        showDialog();
                    }
                })
                .create();
        mOverlay.show();
    }

    private void showDialog(){
        Activity activity = LifecycleControl.getInstance().getActivity();
        if (activity == null) {
            return;
        }
        if (activity != currentActivity) {
            currentActivity = activity;
            mLogDialog = null;
        }
        if (mLogDialog == null) {
            mLogDialog = new LogDialog(activity);
        }
        if (mLogDialog.isShowing()) {
            mLogDialog.dismiss();
        }else {
            mLogDialog.show();
        }
    }

}
