package com.hyg.hlog.data;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc
 */
public class LogModel {
    //日志等级
    private int priority;
    private String tag;
    private String message;
    private long createTime;

    public LogModel() {
        createTime = System.currentTimeMillis();
    }

    public LogModel(int priority, String tag, String message) {
        this.priority = priority;
        this.tag = tag;
        this.message = message;
        createTime = System.currentTimeMillis();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreateTime() {
        return createTime;
    }

}
