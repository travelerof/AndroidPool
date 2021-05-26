package com.hyg.hlog;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc 通过接口输出，如果有
 */
public interface IPrinter {

    void println(int priority, String tag, String message);
}
