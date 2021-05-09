package com.hyg.hdialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * @Author 韩永刚
 * @Date 2021/04/30
 * @Desc
 */
public class HDialog extends BaseDialog {
    private BuilderOptions mBuilderOptions;
    private View mRootView;
    private TextView mTvTitle;
    private View mTitleLine;
    private FrameLayout mMessageLayout;
    private TextView mTvMessage;
    private View mMessageLine;
    private LinearLayout mBottomLayout;
    private TextView mTvCancel;
    private TextView mTvFinish;

    public HDialog(@NonNull Context context, BuilderOptions options) {
        super(context);
        init(context, options);
    }

    public HDialog(@NonNull Context context, int themeResId, BuilderOptions options) {
        super(context, themeResId);
        init(context, options);
    }

    private void init(Context context, BuilderOptions options) {
        mBuilderOptions = options;
        mRootView = LayoutInflater.from(context).inflate(R.layout.item_dialog_layout, null);
        initView();
        setListener();
        initData();
        setContentView(mRootView);
        setCanceledOnTouchOutside(mBuilderOptions.isOutside);
    }

    private void initView() {
        mTvTitle = mRootView.findViewById(R.id.hdialog_title_tv);
        mTitleLine = mRootView.findViewById(R.id.hdialog_title_line_view);
        mMessageLayout = mRootView.findViewById(R.id.hdialog_message_layout);
        mTvMessage = mRootView.findViewById(R.id.hdialog_message_tv);
        mMessageLine = mRootView.findViewById(R.id.hdialog_bottom_line_view);
        mBottomLayout = mRootView.findViewById(R.id.hdialog_bottom_layout);
        mTvCancel = mRootView.findViewById(R.id.hdialog_cancel_tv);
        mTvFinish = mRootView.findViewById(R.id.hdialog_finish_tv);
    }

    private void setListener() {

        setOnDismissListener(mBuilderOptions.mOnDismissListener);
        setOnCancelListener(mBuilderOptions.mOnCancelListener);

        mTvCancel.setOnClickListener(v -> {
            if (mBuilderOptions.mNegativeClickListener != null) {
                mBuilderOptions.mNegativeClickListener.onClick(HDialog.this, v);
            }
        });

        mTvFinish.setOnClickListener(v -> {
            if (mBuilderOptions.mPositiveClickListener != null) {
                mBuilderOptions.mPositiveClickListener.onClick(HDialog.this, v);
            }
        });
    }

    private void initData() {
        if (mBuilderOptions.width != -1) {
            width(mBuilderOptions.width);
        }
        if (mBuilderOptions.height != -1) {
            height(mBuilderOptions.height);
        }
        initTitle();
        initMessage();
        View view = mBuilderOptions.view;
        if (view != null) {
            mTvMessage.setVisibility(View.GONE);
            mMessageLayout.addView(view);
        }
        initBottomLayout();
    }

    private void initTitle() {
        if (TextUtils.isEmpty(mBuilderOptions.titleText)) {
            mTvTitle.setVisibility(View.GONE);
            mTitleLine.setVisibility(View.GONE);
            return;
        }
        mTvTitle.setText(mBuilderOptions.titleText);
        if (mBuilderOptions.mTitleTextListener != null) {
            mBuilderOptions.mTitleTextListener.onText(mTvTitle);
        }
    }

    private void initMessage() {
        if (TextUtils.isEmpty(mBuilderOptions.messageText)) {
            mTvMessage.setVisibility(View.GONE);
            mMessageLine.setVisibility(View.GONE);
            return;
        }
        mTvMessage.setText(mBuilderOptions.messageText);
        if (mBuilderOptions.mMessageTextListener != null) {
            mBuilderOptions.mMessageTextListener.onText(mTvMessage);
        }
    }

    private void initBottomLayout() {
        //底部按钮都为空
        if (TextUtils.isEmpty(mBuilderOptions.positiveText) && TextUtils.isEmpty(mBuilderOptions.negativeText)) {
            mBottomLayout.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(mBuilderOptions.positiveText) && TextUtils.isEmpty(mBuilderOptions.negativeText)) {
            mTvCancel.setVisibility(View.GONE);
            mTvFinish.setText(mBuilderOptions.positiveText);
            if (mBuilderOptions.mPositiveTextListener != null) {
                mBuilderOptions.mPositiveTextListener.onText(mTvFinish);
            }
        } else if (TextUtils.isEmpty(mBuilderOptions.positiveText) && !TextUtils.isEmpty(mBuilderOptions.negativeText)) {
            mTvFinish.setVisibility(View.GONE);
            mTvCancel.setText(mBuilderOptions.negativeText);
            if (mBuilderOptions.mNegativeTextListener != null) {
                mBuilderOptions.mNegativeTextListener.onText(mTvCancel);
            }
        } else {
            mTvFinish.setText(mBuilderOptions.positiveText);
            if (mBuilderOptions.mPositiveTextListener != null) {
                mBuilderOptions.mPositiveTextListener.onText(mTvFinish);
            }
            mTvCancel.setText(mBuilderOptions.negativeText);
            if (mBuilderOptions.mNegativeTextListener != null) {
                mBuilderOptions.mNegativeTextListener.onText(mTvCancel);
            }
        }
    }

    public void setTitle(CharSequence charSequence) {
        if (mBuilderOptions != null) {
            mBuilderOptions.titleText = charSequence;
            initTitle();
        }
    }

    public void setMessage(CharSequence charSequence) {
        if (mBuilderOptions != null) {
            mBuilderOptions.messageText = charSequence;
            initMessage();
        }
    }

    public static class Builder {
        private final Context mContext;
        private final BuilderOptions mBuilderOptions;
        private int themeId = -1;

        public Builder(Context context) {
            mContext = context;
            mBuilderOptions = new BuilderOptions();

        }

        public Builder width(int width) {
            mBuilderOptions.width = width;
            return this;
        }

        public Builder height(int height) {
            mBuilderOptions.height = height;
            return this;
        }

        public Builder theme(int themeId) {
            this.themeId = themeId;
            return this;
        }

        public Builder outSide(boolean outSide) {
            mBuilderOptions.isOutside = outSide;
            return this;
        }

        public Builder title(CharSequence charSequence) {
            return title(charSequence, null);
        }

        public Builder title(CharSequence charSequence, OnTextListener onTextListener) {
            mBuilderOptions.titleText = charSequence;
            mBuilderOptions.mTitleTextListener = onTextListener;
            return this;
        }

        public Builder message(CharSequence charSequence) {
            return message(charSequence, null);
        }

        public Builder message(CharSequence charSequence, OnTextListener onTextListener) {
            mBuilderOptions.messageText = charSequence;
            mBuilderOptions.mMessageTextListener = onTextListener;
            return this;
        }


        public Builder view(View view) {
            mBuilderOptions.view = view;
            return this;
        }

        public Builder view(int resId) {
            return view(LayoutInflater.from(mContext).inflate(resId, null));
        }

        public Builder finish(CharSequence charSequence, OnDialogClickListener onDialogClickListener) {
            return finish(charSequence, null, onDialogClickListener);
        }

        public Builder finish(CharSequence charSequence, OnTextListener onTextListener, OnDialogClickListener onDialogClickListener) {
            mBuilderOptions.positiveText = charSequence;
            mBuilderOptions.mPositiveTextListener = onTextListener;
            mBuilderOptions.mPositiveClickListener = onDialogClickListener;
            return this;
        }

        public Builder cancel(CharSequence charSequence, OnDialogClickListener onDialogClickListener) {
            return cancel(charSequence, null, onDialogClickListener);
        }

        public Builder cancel(CharSequence charSequence, OnTextListener onTextListener, OnDialogClickListener onDialogClickListener) {
            mBuilderOptions.negativeText = charSequence;
            mBuilderOptions.mNegativeTextListener = onTextListener;
            mBuilderOptions.mNegativeClickListener = onDialogClickListener;
            return this;
        }


        public HDialog create() {
            if (themeId == -1) {
                return new HDialog(mContext, mBuilderOptions);
            }
            return new HDialog(mContext, themeId, mBuilderOptions);
        }

    }
}
