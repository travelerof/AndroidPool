package com.hyg.hvideo;

import android.net.Uri;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public class HVideoSource {

    private String url;
    private Uri uri;

    public Uri getUrl() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
