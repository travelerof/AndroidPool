package com.hyg.hlog.print;

import android.util.Log;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc
 */
public class Printer implements IPrinter {

    @Override
    public int println(int priority, String tag, String message) {
        return Log.println(priority, tag, message);
    }
}
