package com.hyg.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * @Author 韩永刚
 * @Date 2021/04/30
 * @Desc
 */
public class BaseDialog extends Dialog {

    private Window mWindow;
    private WindowManager.LayoutParams mParams;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.HDialog);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        mWindow = getWindow();
        mParams = mWindow.getAttributes();
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mParams.width = screenWidth / 3 * 2;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public void x(int x) {
        mParams.x = x;
    }

    public void y(int y) {
        mParams.y = y;
    }

    public void gravity(int gravity) {
        mParams.gravity = gravity;
    }

    public void width(int width) {
        mParams.width = width;
    }

    public void height(int height) {
        mParams.height = height;
    }

    public WindowManager.LayoutParams getParams() {
        return mParams;
    }

    @Override
    public void show() {
        mWindow.setAttributes(mParams);
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
