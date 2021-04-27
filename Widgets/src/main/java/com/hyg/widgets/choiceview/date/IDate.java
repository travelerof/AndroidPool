package com.hyg.widgets.choiceview.date;

import androidx.annotation.NonNull;


import com.hyg.widgets.choiceview.ChoiceView;

import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/02/09
 * @Desc
 */
interface IDate {
    /**
     * 添加view
     *
     * @param choiceView
     */
    void addChildView(@NonNull ChoiceView... choiceView);

    /**
     * 获取item高度
     *
     * @return
     */
    int getItemHeight();

    /**
     * 根据开始角标和结束角标组装数据
     *
     * @param startIndex
     * @param endIndex
     * @return
     */
    List<String> getData(int startIndex, int endIndex);

    /**
     * 获取最小年
     *
     * @return
     */
    int getMinYear();

    /**
     * 获取当前年
     *
     * @return
     */
    int getNowYear();

    /**
     * 获取当前月
     *
     * @return
     */
    int getNowMonth();

    /**
     * 获取当前日
     *
     * @return
     */
    int getNowDay();

    /**
     * 获取当前小时
     *
     * @return
     */
    int getNowHour();

    /**
     * 获取当前分钟
     *
     * @return
     */
    int getNowMinute();

    /**
     * 根据年月获取当前月天数
     *
     * @param year
     * @param month
     * @return
     */
    int getMaxDayofMonth(int year, int month);

    /**
     * 获取当前value对应的index
     *
     * @param sources
     * @param value
     * @return
     */
    int getPosition(@NonNull List<String> sources, String value);

    /**
     * @param date
     * @return
     */
    String getDateValue(int date);

    /**
     * 是否显示单位，如年，月等
     *
     * @return
     */
    boolean isShowUnit();

    /**
     * 设置日期
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     */
    void setDate(int year, int month, int day, int hour, int minute);

}
