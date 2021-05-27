package com.hyg.hpermission.request;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
interface IRequest {
    /**
     * 执行请求
     */
    void request();

    /**
     * 请求完成监听
     * @param onRequestListener
     */
    void addOnRequestListener(OnRequestListener onRequestListener);

    /**
     *
     * @return
     */
    String getTag();

    void close();
}
