package com.hyg.hvideo;

import android.net.Uri;

/**
 * @Author 韩永刚
 * @Date 2021/04/02
 * @Desc
 */
interface HmMediaItem {

    /**
     * 多媒体uri
     *
     * @return
     */
    Uri url();

    /**
     * 广告uri
     *
     * @return
     */
    Uri adUrl();

    boolean clip();

    long clipStartMillisecond();

    long clipEndMillisecond();


}
