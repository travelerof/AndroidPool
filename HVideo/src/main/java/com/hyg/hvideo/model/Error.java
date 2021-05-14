package com.hyg.hvideo.model;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public class Error {
    public int code;
    public String message;
    public Throwable mThrowable;

    public Error() {
    }

    public Error(int code) {
        this.code = code;
    }

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Error(int code, String message, Throwable throwable) {
        this.code = code;
        this.message = message;
        mThrowable = throwable;
    }
}
