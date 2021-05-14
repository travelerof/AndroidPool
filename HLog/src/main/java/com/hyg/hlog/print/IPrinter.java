package com.hyg.hlog.print;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc
 */
public interface IPrinter {

    int println(int priority, String tag, String message);
}
