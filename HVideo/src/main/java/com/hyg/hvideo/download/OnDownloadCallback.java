package com.hyg.hvideo.download;

/**
 * @Author 韩永刚
 * @Date 2021/04/02
 * @Desc
 */
public interface OnDownloadCallback {
    /**
     * 网络请求失败
     *
     * @param message
     */
    void requestFailed(String message);

    /**
     * 请求成功
     * @param totalProgress 总长度
     */
    void requestSuccess(long totalProgress);

    /**
     * 保存长度
     *
     * @param currentprogress
     */
    void udpateProgress(long currentprogress);

    /**
     * 保存成功
     *
     * @param filepath 文件绝对路径
     */
    void onSuccess(String filepath);

    /**
     * 文件保存失败
     *
     * @param message
     */
    void onFailed(String message);
}
