package com.hyg.overlay;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * @Author hanyonggang
 * @Date 2021/5/15
 * @Desc
 */
public final class LifecycleControl implements Application.ActivityLifecycleCallbacks {
    private static LifecycleControl lifecycleControl = new LifecycleControl();
    private Application mApplication;
    private int activityCount;
    private WeakReference<Activity> currentActivity;
    private OnLifecycleListener mOnLifecycleListener;

    private LifecycleControl() {

    }

    public static LifecycleControl getInstance() {
        return lifecycleControl;
    }

    public Context getContext() {
        return mApplication;
    }

    public Activity getActivity() {
        if (currentActivity == null) {
            return null;
        }
        return currentActivity.get();
    }

    public int getActivityCount() {
        return activityCount;
    }

    public void addActivityListener(OnLifecycleListener onLifecycleListener) {
        mOnLifecycleListener = onLifecycleListener;
    }

    public void registerActivityLifecyclerCallbacks(@NonNull Application application) {
        mApplication = application;
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        activityCount++;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = new WeakReference<>(activity);
        if (mOnLifecycleListener != null) {
            mOnLifecycleListener.start();
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activityCount--;
        if (mOnLifecycleListener != null) {
            mOnLifecycleListener.stop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
