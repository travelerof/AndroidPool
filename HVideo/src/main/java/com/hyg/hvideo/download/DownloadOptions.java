package com.hyg.hvideo.download;

/**
 * @Author 韩永刚
 * @Date 2021/04/02
 * @Desc
 */
public final class DownloadOptions {
    /**
     * 是否允许下载
     */
    public boolean allowDownload = false;
    /**
     * 下载保存地址
     */
    public String downloadPath;
    /**
     * 下载方式
     */
    public @DownloadType int downloadType = DownloadType.DEFAULT;
}
