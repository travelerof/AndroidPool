package com.hyg.dialog;

import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;


/**
 * @Author 韩永刚
 * @Date 2021/04/30
 * @Desc
 */
final class BuilderOptions {

    public int width = -1;
    public int height = -1;
    /**
     * 是否允许点击外部关闭
     */
    public boolean isOutside = true;
    public boolean cancelable = true;
    public int gravity  = Gravity.CENTER;
    public int background = R.drawable.shape_dialog_default_bg;
    public CharSequence titleText;
    public OnTextListener mTitleTextListener;
    public CharSequence messageText;
    public OnTextListener mMessageTextListener;
    public CharSequence positiveText;
    public OnTextListener mPositiveTextListener;
    public CharSequence negativeText;
    public OnTextListener mNegativeTextListener;
    public OnDialogClickListener mPositiveClickListener;
    public OnDialogClickListener mNegativeClickListener;

    public DialogInterface.OnDismissListener mOnDismissListener;
    public DialogInterface.OnCancelListener mOnCancelListener;

    public View view;
    public FrameLayout.LayoutParams mLayoutParams;
}
