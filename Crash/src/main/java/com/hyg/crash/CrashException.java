package com.hyg.crash;

/**
 * @Author 韩永刚
 * @Date 2021/03/30
 * @Desc
 */
class CrashException extends RuntimeException {
    public CrashException(String msg){
        super(msg);
    }
}
