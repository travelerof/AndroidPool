package com.hyg.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author 韩永刚
 * @Date 2021/02/10
 * @Desc 日期转换类
 */
public class DateUtils {

    public static final String FORMAT_STYLE_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_STYLE_1 = "yyyy-MM-dd";
    public static final String FORMAT_STYLE_2 = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_STYLE_3 = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMAT_STYLE_4 = "yyyy/MM/dd HH:mm";
    public static final String FORMAT_STYLE_5 = "HH:mm";

    /**
     * ms转换为日期格式
     * @param time
     * @param formatStyle
     * @return
     */
    public static String getTime(long time, String formatStyle) {
        return getTime(new Date(time), formatStyle);
    }

    public static String getTime(Date date, String formatStyle) {
        if (date == null) {
            return null;
        }
        return getDateFormat(formatStyle).format(date);
    }

    public static Date getDate(String formatDate, String formatStyle) {
        if (StringUtils.isEmpty(formatDate)) {
            throw new NullPointerException("转换时间不能为空");
        }
        try {
            return getDateFormat(formatStyle).parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getLongTime(String formatDate, String formatStyle) {
        Date date = getDate(formatDate, formatStyle);
        return date == null ? 0 : date.getTime();
    }

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat getDateFormat(String formatStyle) {
        if (StringUtils.isEmpty(formatStyle)) {
            formatStyle = FORMAT_STYLE_DEFAULT;
        }
        return new SimpleDateFormat(formatStyle);
    }

    public static int getMonthOfMaxDay(Date date) {
        Calendar calendar = getCalender();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间年份
     * @return
     */
    public static int getCurrentYear() {
        return getYear(new Date());
    }

    /**
     * 获取当前时间月份
     * @return
     */
    public static int getCurrentMonth() {
        return getMonth(new Date());
    }

    /**
     * 获取当前时间日
     * @return
     */
    public static int getCurrentDay() {
        return getDay(new Date());
    }

    /**
     * 获取当前时间星期
     * @return
     */
    public static int getCurrentWeekOfDay() {
        return getWeekOfDay(new Date());
    }

    public static int getYear(Date date) {
        Calendar calendar = getCalender();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = getCalender();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar calendar = getCalender();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekOfDay(Date date) {
        Calendar calendar = getCalender();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static Calendar getCalender() {
        return Calendar.getInstance();
    }
}
