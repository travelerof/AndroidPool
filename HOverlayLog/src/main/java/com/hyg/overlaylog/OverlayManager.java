package com.hyg.overlaylog;

import android.content.Context;
import android.view.View;

import com.hyg.overlay.HOverlay;
import com.hyg.overlay.LifecycleControl;
import com.hyg.overlay.OnOverlyListener;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
final class OverlayManager {

    public static OverlayManager manager = new OverlayManager();
    private HOverlay mOverlay;

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

                    }
                })
                .create();
        mOverlay.show();
    }

    private void initDialog(){

    }

}
