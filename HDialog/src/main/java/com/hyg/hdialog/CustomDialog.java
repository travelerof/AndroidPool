package com.hyg.hdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author 韩永刚
 * @Date 2021/02/01
 * @Desc
 */
public class CustomDialog extends Dialog {
    public static final String TAG = CustomDialog.class.getSimpleName();

    @IntDef({CustomDialog.DEFAULT, CustomDialog.BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowType {
    }

    public static final int DEFAULT = 0;
    public static final int BOTTOM = 1;

    private Context context;
    private BuilderConfig builderConfig;

    private CustomDialog(@NonNull Context context, @NonNull BuilderConfig builderConfig, IViewHandle iViewHandle) {
        this(context, R.style.CustomDialog, builderConfig, iViewHandle);
    }

    private CustomDialog(@NonNull Context context, int themeResId, @NonNull BuilderConfig builderConfig, IViewHandle iViewHandle) {
        super(context, themeResId);
        init(context, builderConfig, iViewHandle);
    }

    private void init(Context context, BuilderConfig builderConfig, IViewHandle iViewHandle) {
        this.context = context;
        this.builderConfig = builderConfig;
        if (iViewHandle == null) {
            iViewHandle = createDefaultViewHolder();
        }
        setContentView(iViewHandle.getView());
        setCanceledOnTouchOutside(builderConfig.isOutSide());
//        getWindow().setWindowAnimations(builderConfig.getAnimationId());
        iViewHandle.handle(this, builderConfig);
    }

    private IViewHandle createDefaultViewHolder() {
        IViewHandle iViewHandle;
        if (builderConfig.getType() == BOTTOM) {
            iViewHandle = new BottomViewHolder(context);
        } else {
            iViewHandle = new ViewHolder(context);
        }
        return iViewHandle;
    }

    @Override
    public void show() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = builderConfig.getWindowWidth();
        attributes.height = builderConfig.getWindowHeight();
        attributes.gravity = builderConfig.getGravity();
        window.setAttributes(attributes);
        super.show();
    }

    public static class Builder {
        private Context context;
        private BuilderConfig config;
        private IViewHandle iViewHandle;

        public Builder(@NonNull Context context) {
            this(context, false);
        }

        public Builder(@NonNull Context context, boolean isBottom) {
            this.context = context;
            config = new BuilderConfig();
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            if (isBottom) {
                config.setWindowWidth(screenWidth);
            } else {
                //设置默认宽度
                config.setWindowWidth(screenWidth / 3 * 2);
                //设置默认背景
                config.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_dialog_default_bg));
//                config.setAnimationId(R.anim.anim_dialog_default);
            }
        }

        public Builder setWindowWidth(int width) {
            config.setWindowWidth(width);
            return this;
        }

        public Builder setGravity(int gravity) {
            config.setGravity(gravity);
            return this;
        }


        public Builder setType(@ShowType int type) {
            config.setType(type);
            return this;
        }

        public Builder setViewHolder(IViewHandle iViewHandle) {
            this.iViewHandle = iViewHandle;
            return this;
        }

        public Builder setOutSideCancel(boolean outSide) {
            config.setOutSide(outSide);
            return this;
        }

        public Builder setTheme(int themeId) {
            config.setThemeResId(themeId);
            return this;
        }

        public Builder setTitle(int titleId) {
            config.setTitleId(titleId);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            config.setTitleText(title);
            return this;
        }

        public Builder setTitleColor(int titleColor) {
            config.setTitleColor(titleColor);
            return this;
        }

        public Builder setTitleSize(float titleSize) {
            config.setTitleSize(titleSize);
            return this;
        }

        public Builder setTitleBold() {
            config.setBold(true);
            return this;
        }

        public Builder setTitleLine(boolean titleLine) {
            config.setTitleLine(titleLine);
            return this;
        }

        public Builder setTitleLineColor(int titleLineColor) {
            config.setTitleLineColor(titleLineColor);
            return this;
        }

        public Builder setMessage(int messageId) {
            config.setMessageId(messageId);
            return this;
        }

        public Builder setMessage(CharSequence messageText) {
            config.setMessageText(messageText);
            return this;
        }

        public Builder setMessageColor(int messageColor) {
            config.setMessageColor(messageColor);
            return this;
        }

        public Builder setMessageSize(float messageSize) {
            config.setMessageSize(messageSize);
            return this;
        }

        public Builder setContentView(View contentView) {
            config.setChildView(contentView);
            return this;
        }

        public Builder setNegationButton(int textId, OnDialogClickListener onDialogClickListener) {
            config.setNegationTextId(textId);
            config.setOnNegationListener(onDialogClickListener);
            return this;
        }

        public Builder setNegationButton(CharSequence charSequence, OnDialogClickListener onDialogClickListener) {
            config.setNegationText(charSequence);
            config.setOnNegationListener(onDialogClickListener);
            return this;
        }

        public Builder setPositiveButton(int textId, OnDialogClickListener onDialogClickListener) {
            config.setPositiveTextId(textId);
            config.setOnPositiveListener(onDialogClickListener);
            return this;
        }

        public Builder setPositiveButton(CharSequence charSequence, OnDialogClickListener onDialogClickListener) {
            config.setPositiveText(charSequence);
            config.setOnPositiveListener(onDialogClickListener);
            return this;
        }

        public CustomDialog create() {
            return new CustomDialog(context, config, iViewHandle);
        }
    }
}
