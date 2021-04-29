package com.hyg.hdialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


/**
 * @Author 韩永刚
 * @Date 2021/02/01
 * @Desc
 */
class ViewHolder implements IViewHandle {

    public Context context;

    private final View rootView;
    private final LinearLayout rootLayout;
    private final TextView tvTitle;
    private final View titleLine;
    private final FrameLayout messageLayout;
    private final TextView tvMessage;
    private final View bottomLine;
    private final LinearLayout bottomLayout;
    private final TextView tvNegation;
    private final View positiveLine;
    private final TextView tvPositive;

    public ViewHolder(Context context) {
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.item_dialog_layout,null);
        rootLayout = rootView.findViewById(R.id.dialog_root_layout);
        tvTitle = rootView.findViewById(R.id.dialog_title_tv);
        titleLine = rootView.findViewById(R.id.dialog_title_line_view);
        messageLayout = rootView.findViewById(R.id.dialog_message_layout);
        tvMessage = rootView.findViewById(R.id.dialog_message_tv);
        bottomLine = rootView.findViewById(R.id.dialog_bottom_line_view);
        bottomLayout = rootView.findViewById(R.id.dialog_bottom_layout);
        tvNegation = rootView.findViewById(R.id.dialog_bottom_negation_tv);
        positiveLine = rootView.findViewById(R.id.dialog_bottom_positive_line);
        tvPositive = rootView.findViewById(R.id.dialog_bottom_positive_tv);
    }

    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void handle(@NonNull CustomDialog dialog, @NonNull BuilderConfig config) {
        initRootLayout(config);
        initTitle(config);
        initTitleLine(config);
        initMessageLayout(config);
        initBottomLayout(dialog,config);
    }

    private void initRootLayout(BuilderConfig config) {
        Drawable backgroundDrawable = config.getBackgroundDrawable();
        rootLayout.setBackground(backgroundDrawable);
    }

    private void initTitle(BuilderConfig config){
        String title = getText(config.getTitleId(),config.getTitleText());
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
            titleLine.setVisibility(View.GONE);
            return;
        }
        int titleColor = config.getTitleColor();
        if (titleColor > 0) {
            tvTitle.setTextColor(titleColor);
        }
        float titleSize = config.getTitleSize();
        if (titleSize > 0) {
            tvTitle.setTextSize(titleSize);
        }
        tvTitle.setText(title);
    }

    private void initTitleLine(BuilderConfig config) {
        if (!config.isTitleLine()) {
            titleLine.setVisibility(View.GONE);
            return;
        }
        int titleLineColor = config.getTitleLineColor();
        if (titleLineColor > 0) {
            titleLine.setBackgroundColor(titleLineColor);
        }
    }

    private void initMessageLayout(BuilderConfig config){
        View childView = config.getChildView();
        if (childView != null) {
            tvMessage.setVisibility(View.GONE);
            rootLayout.addView(childView);
            return;
        }
        String message = getText(config.getMessageId(),config.getMessageText());
        if (TextUtils.isEmpty(message)) {
            return;
        }
        int messageColor = config.getMessageColor();
        if (messageColor > 0) {
            tvMessage.setTextColor(messageColor);
        }
        float messageSize = config.getMessageSize();
        if (messageSize > 0) {
            tvMessage.setTextSize(messageSize);
        }
        tvMessage.setText(message);
    }

    private void initBottomLayout(CustomDialog dialog, BuilderConfig config){
        String negationText = getText(config.getNegationTextId(),config.getNegationText());
        String positiveText = getText(config.getPositiveTextId(),config.getPositiveText());
        //左侧按钮为空
        if (TextUtils.isEmpty(negationText) && !TextUtils.isEmpty(positiveText)) {
            positiveLine.setVisibility(View.GONE);
            tvNegation.setVisibility(View.GONE);
            initPositive(dialog,positiveText,config);
        } else if (!TextUtils.isEmpty(negationText) && TextUtils.isEmpty(positiveText)) {
            tvPositive.setVisibility(View.GONE);
            positiveLine.setVisibility(View.GONE);
            initNegative(dialog,negationText,config);
        } else if (TextUtils.isEmpty(negationText) && TextUtils.isEmpty(positiveText)){
            bottomLine.setVisibility(View.GONE);
            bottomLayout.setVisibility(View.GONE);
        }
    }

    private void initPositive(final CustomDialog dialog, String positiveText, final BuilderConfig config) {
        tvPositive.setText(positiveText);
        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogClickListener onPositiveListener = config.getOnPositiveListener();
                if (onPositiveListener != null) {
                    onPositiveListener.onDialogClick(dialog,v);
                }
            }
        });
    }

    private void initNegative(final CustomDialog dialog, String negationText, final BuilderConfig config) {
        tvNegation.setText(negationText);
        tvNegation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDialogClickListener onNegationListener = config.getOnNegationListener();
                if (onNegationListener != null) {
                    onNegationListener.onDialogClick(dialog,v);
                }
            }
        });
    }

    private String getText(int textId,CharSequence text){
        if (textId > 0) {
            return context.getString(textId);
        }
        if (text == null) {
            return "";
        }
        return text.toString();
    }

}
