package com.hyg.hlog;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc
 */
public interface IPrinter {

    void println(int priority, String tag, String message);
}
