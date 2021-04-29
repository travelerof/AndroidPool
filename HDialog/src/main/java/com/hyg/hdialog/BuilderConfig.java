package com.hyg.hdialog;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author 韩永刚
 * @Date 2021/02/01
 * @Desc
 */
class BuilderConfig {

    /**
     * 点击dialog外部是否关闭
     */
    private boolean outSide = true;
    /**
     * 主题
     */
    private int themeResId;
    /**
     * 类型
     */
    @CustomDialog.ShowType
    private int type = CustomDialog.DEFAULT;
    /**
     * 左侧按钮文本id
     */
    private int positiveTextId;
    /**
     * 左侧按钮文本
     */
    private CharSequence positiveText;
    /**
     * 右侧按钮文本id
     */
    private int negationTextId;
    /**
     * 右侧按钮文本
     */
    private CharSequence negationText;
    /**
     * 标题文本id
     */
    private int titleId = -1;
    /**
     * 标题文本
     */
    private CharSequence titleText;
    /**
     * 标题文本大小
     */
    private float titleSize;
    /**
     * 标题颜色
     */
    private int titleColor;
    /**
     * 标题是否加粗
     */
    private boolean bold;
    /**
     * 标题下方线
     */
    private boolean titleLine = true;
    /**
     * 标题下方线颜色
     */
    private int titleLineColor;
    /**
     * 文本id
     */
    private int messageId;
    /**
     * 文本
     */
    private CharSequence messageText;
    /**
     * 文本大小
     */
    private float messageSize;
    /**
     * 文本颜色
     */
    private int messageColor;
    /**
     * 子view
     */
    private View childView;
    /**
     * 背景
     */
    private Drawable backgroundDrawable;
    /**
     * 左侧按钮监听
     */
    private OnDialogClickListener onPositiveListener;
    /**
     * 右侧按钮监听
     */
    private OnDialogClickListener onNegationListener;

    private int windowWidth;

    private int windowHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int gravity = Gravity.CENTER;
    private int animationId;


    public boolean isOutSide() {
        return outSide;
    }

    public void setOutSide(boolean outSide) {
        this.outSide = outSide;
    }

    public int getThemeResId() {
        return themeResId;
    }

    public void setThemeResId(int themeResId) {
        this.themeResId = themeResId;
    }

    @CustomDialog.ShowType
    public int getType() {
        return type;
    }

    public void setType(@CustomDialog.ShowType int type) {
        this.type = type;
    }

    public int getPositiveTextId() {
        return positiveTextId;
    }

    public void setPositiveTextId(int positiveTextId) {
        this.positiveTextId = positiveTextId;
    }

    public CharSequence getPositiveText() {
        return positiveText;
    }

    public void setPositiveText(CharSequence positiveText) {
        this.positiveText = positiveText;
    }

    public int getNegationTextId() {
        return negationTextId;
    }

    public void setNegationTextId(int negationTextId) {
        this.negationTextId = negationTextId;
    }

    public CharSequence getNegationText() {
        return negationText;
    }

    public void setNegationText(CharSequence negationText) {
        this.negationText = negationText;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public CharSequence getTitleText() {
        return titleText;
    }

    public void setTitleText(CharSequence titleText) {
        this.titleText = titleText;
    }

    public float getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isTitleLine() {
        return titleLine;
    }

    public void setTitleLine(boolean titleLine) {
        this.titleLine = titleLine;
    }

    public int getTitleLineColor() {
        return titleLineColor;
    }

    public void setTitleLineColor(int titleLineColor) {
        this.titleLineColor = titleLineColor;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public CharSequence getMessageText() {
        return messageText;
    }

    public void setMessageText(CharSequence messageText) {
        this.messageText = messageText;
    }

    public float getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(float messageSize) {
        this.messageSize = messageSize;
    }

    public int getMessageColor() {
        return messageColor;
    }

    public void setMessageColor(int messageColor) {
        this.messageColor = messageColor;
    }

    public View getChildView() {
        return childView;
    }

    public void setChildView(View childView) {
        this.childView = childView;
    }

    public Drawable getBackgroundDrawable() {
        return backgroundDrawable;
    }

    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
    }

    public OnDialogClickListener getOnPositiveListener() {
        return onPositiveListener;
    }

    public void setOnPositiveListener(OnDialogClickListener onPositiveListener) {
        this.onPositiveListener = onPositiveListener;
    }

    public OnDialogClickListener getOnNegationListener() {
        return onNegationListener;
    }

    public void setOnNegationListener(OnDialogClickListener onNegationListener) {
        this.onNegationListener = onNegationListener;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getAnimationId() {
        return animationId;
    }

    public void setAnimationId(int animationId) {

        this.animationId = animationId;
    }

}
