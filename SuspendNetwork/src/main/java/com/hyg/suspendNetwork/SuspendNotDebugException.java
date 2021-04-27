package com.hyg.suspendNetwork;

/**
 * @Author 韩永刚
 * @Date 2021/03/25
 * @Desc
 */
class SuspendNotDebugException extends RuntimeException {
    public SuspendNotDebugException(){
        super("suspend is not debug mode!");
    }
}
