package com.hyg.overlaylog.log;

import android.renderscript.RenderScript;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc Log日志实体类
 */
public class LogModel {
    /**
     * 日志等级
     */
    public int priority;
    /**
     * 日志tag
     */
    public String tag;
    /**
     * 日志信息
     */
    public String message;
    /**
     * 日志打印时间
     */
    public long createTime;

    public LogModel() {
        createTime = System.currentTimeMillis();
    }
}
