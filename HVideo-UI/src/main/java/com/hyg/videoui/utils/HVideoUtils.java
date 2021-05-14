package com.hyg.videoui.utils;

import java.text.SimpleDateFormat;

/**
 * @Author hanyonggang
 * @Date 2021/5/13 0013
 * @Desc
 */
public class HVideoUtils {

    public static final int HOUR = 1000 * 60 * 60;

    private HVideoUtils() {
    }

    /**
     * 根据时间获取格式
     *
     * @param time
     * @return
     */
    public static String getTimeFormat(long time) {
        if (time >= HOUR) {
            return "HH:mm:ss";
        }
        return "mm:ss";
    }

    public static String getTime(long time, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(time);
    }

    /**
     * 视频播放时间转换为进度值
     * @param position 播放时长
     * @param totalPosition 总时长
     * @return
     */
    public static int toProgress(long position, long totalPosition){
        return (int) (position*100.0/totalPosition);
    }

    /**
     * 进度值转换为视频时长
     *
     * @param progress
     * @param totalPosition
     * @return
     */
    public static long toTime(int progress, long totalPosition){
        return (long) (totalPosition*progress/100.0);
    }
}
