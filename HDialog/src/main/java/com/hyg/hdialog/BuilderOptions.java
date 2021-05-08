package com.hyg.hdialog;

import android.content.DialogInterface;
import android.view.View;

/**
 * @Author 韩永刚
 * @Date 2021/04/30
 * @Desc
 */
final class BuilderOptions {

    public int width = -1;
    public boolean isOutside;
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
}
