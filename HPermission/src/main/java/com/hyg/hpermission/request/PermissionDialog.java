package com.hyg.hpermission.request;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hyg.hpermission.R;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc 特殊权限申请弹框
 */
class PermissionDialog extends Dialog {

    private View mView;
    private TextView mTvMessage;
    private TextView mTvApply;
    private TextView mTvRefuse;
    private OnPermissionListener mOnPermissionListener;
    private ImageView mIvTitle;

    public PermissionDialog(@NonNull Context context) {
        super(context, R.style.PermissionDialog);
        init(context);
    }

    private void init(Context context) {
        initView();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(mView);
        Resources resources = context.getResources();
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = resources.getDisplayMetrics().widthPixels - resources.getDimensionPixelSize(R.dimen.dimen_15) * 2;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    private void initView() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_permission_layout, null);
        mTvMessage = mView.findViewById(R.id.dialog_permission_message_tv);
        mTvApply = mView.findViewById(R.id.dialog_permission_apply_tv);
        mTvRefuse = mView.findViewById(R.id.dialog_permission_refuse_tv);
        mIvTitle = mView.findViewById(R.id.dialog_permission_title_iv);
        mTvApply.setOnClickListener(this::onClick);
        mTvRefuse.setOnClickListener(this::onClick);
    }

    public void setMessage(String message) {
        mTvMessage.setText(message);
    }

    public void setTitle(int titleId) {
        if (titleId == -1) {
            mIvTitle.setVisibility(View.GONE);
            return;
        }
        mIvTitle.setVisibility(View.VISIBLE);
        mIvTitle.setImageResource(titleId);
    }

    private void onClick(View view) {
        if (mOnPermissionListener == null) {
            dismiss();
            return;
        }
        int id = view.getId();
        if (id == R.id.dialog_permission_apply_tv) {
            mOnPermissionListener.onApply(this, view);
        } else if (id == R.id.dialog_permission_refuse_tv) {
            mOnPermissionListener.onRefuse(this, view);
        }
    }

    public void setOnPermissionListener(OnPermissionListener onPermissionListener) {
        mOnPermissionListener = onPermissionListener;
    }


    public interface OnPermissionListener {
        void onApply(PermissionDialog dialog, View v);

        void onRefuse(PermissionDialog dialog, View v);
    }
}
