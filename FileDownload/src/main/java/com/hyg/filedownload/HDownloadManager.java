package com.hyg.filedownload;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author 韩永刚
 * @Date 2021/04/02
 * @Desc
 */
public class HDownloadManager {

    private static HDownloadManager manager;

    private final ThreadPoolExecutor mPoolExecutor;

    private HDownloadManager() {
        mPoolExecutor = new ThreadPoolExecutor(
                3,
                5,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
    }

}
