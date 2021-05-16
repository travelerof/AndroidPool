package com.hyg.hlog.suspend;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Author hanyonggang
 * @Date 2021/5/15
 * @Desc
 */
public final class ActivityControl {
    private static ActivityControl activityControl = new ActivityControl();
    private Application mApplication;
    private Activity currentActivity;
    private OnActivityListener mOnActivityListener;

    private ActivityControl() {

    }

    public static ActivityControl getInstance() {
        return activityControl;
    }

    public Context getContext() {
        return activityControl.mApplication;
    }

    public Activity getActivity() {
        return activityControl.currentActivity;
    }

    public void addActivityListener(OnActivityListener onActivityListener) {
        activityControl.mOnActivityListener = onActivityListener;
    }

    public void registerActivityLifecyclerCallbacks(@NonNull Application application) {
        mApplication = application;
        mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                activityControl.currentActivity = activity;
                if (activityControl.mOnActivityListener != null) {
                    activityControl.mOnActivityListener.onResume(activity);
                }
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                if (activityControl.currentActivity == activity) {
                    activityControl.currentActivity = null;
                }
                if (activityControl.mOnActivityListener != null) {
                    activityControl.mOnActivityListener.onStop(activity);
                }
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }
}
