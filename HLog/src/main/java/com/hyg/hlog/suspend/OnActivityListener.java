package com.hyg.hlog.suspend;

import android.app.Activity;

/**
 * @Author hanyonggang
 * @Date 2021/5/15 0015
 * @Desc
 */
public interface OnActivityListener {

    void onResume(Activity activity);

    void onStop(Activity activity);
}
