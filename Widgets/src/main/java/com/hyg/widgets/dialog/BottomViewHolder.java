package com.hyg.widgets.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hyg.widgets.R;


/**
 * @Author 韩永刚
 * @Date 2021/02/04
 * @Desc 底部dialog
 */
class BottomViewHolder implements IViewHandle {

    private Context context;
    private final View rootView;
    private final LinearLayout rootLayout;
    private final RelativeLayout titleLayout;
    private final TextView tvCancel;
    private final TextView tvTitle;
    private final TextView tvOk;
    private final View titleLine;

    public BottomViewHolder(Context context){
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.item_bottom_dialog_layout,null);
        rootLayout = rootView.findViewById(R.id.dialog_bottom_rootview_layout);
        titleLayout = rootView.findViewById(R.id.dialog_bottom_title_layout);
        tvCancel = rootView.findViewById(R.id.dialog_bottom_title_cancel_tv);
        tvTitle = rootView.findViewById(R.id.dialog_bottom_title_tv);
        tvOk = rootView.findViewById(R.id.dialog_bottom_title_ok_tv);
        titleLine = rootView.findViewById(R.id.dialog_bottom_line_view);
    }
    @NonNull
    @Override
    public View getView() {
        return rootView;
    }

    @Override
    public void handle(@NonNull CustomDialog dialog, @NonNull BuilderConfig config) {
        initTitleLayout(dialog,config);
        View childView = config.getChildView();
        if (childView != null) {
            rootLayout.addView(childView);
        }
    }

    private void initTitleLayout(CustomDialog dialog, BuilderConfig config) {
        if (!initCancelView(dialog,config)&& !initOkView(dialog,config)&& !initTitleView(config)) {
            titleLayout.setVisibility(View.GONE);
            titleLine.setVisibility(View.GONE);
            return;
        }
        int titleLineColor = config.getTitleLineColor();
        if (titleLineColor > 0) {
            titleLine.setBackgroundColor(titleLineColor);
        }
    }

    private boolean initTitleView(BuilderConfig config) {
        String titleText = getText(config.getTitleId(),config.getTitleText());
        if (TextUtils.isEmpty(titleText)) {
            tvTitle.setVisibility(View.GONE);
            return false;
        }
        int titleColor = config.getTitleColor();
        if (titleColor > 0) {
            tvTitle.setTextColor(titleColor);
        }
        float titleSize = config.getTitleSize();
        if (titleSize > 0) {
            tvTitle.setTextSize(titleSize);
        }
        tvTitle.setText(titleText);
        return true;
    }

    private boolean initCancelView(final CustomDialog dialog, final BuilderConfig config) {
        String cancelText = getText(config.getNegationTextId(),config.getNegationText());
        if (TextUtils.isEmpty(cancelText)) {
            tvCancel.setVisibility(View.INVISIBLE);
            return false;
        }
        tvCancel.setText(cancelText);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.getOnNegationListener() != null) {
                    config.getOnNegationListener().onDialogClick(dialog,v);
                }
            }
        });
        return true;
    }

    private boolean initOkView(final CustomDialog dialog, final BuilderConfig config) {
        String okText = getText(config.getPositiveTextId(),config.getPositiveText());
        if (TextUtils.isEmpty(okText)) {
            tvOk.setVisibility(View.INVISIBLE);
            return false;
        }
        tvOk.setText(okText);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (config.getOnPositiveListener() != null) {
                    config.getOnPositiveListener().onDialogClick(dialog,v);
                }
            }
        });
        return true;
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
