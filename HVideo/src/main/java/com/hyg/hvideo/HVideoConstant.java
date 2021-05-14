package com.hyg.hvideo;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public class HVideoConstant {

    public static class ERROR {
        /**
         * 视频资源为空
         */
        public static final int VIDEO_SOURCE_EMPTY = -1;
        /**
         * 资源组装失败
         */
        public static final int VIDEO_INSTALL_FAILED = -2;
        /**
         * 资源准备失败
         */
        public static final int VIDEO_PREPARE_FAILED = -3;
    }

    public static class STATUS {
        /**
         * 媒体开始准备
         */
        public static final int PREPARE_START = 0;
        /**
         * 媒体准备中
         */
        public static final int PREPAREING = 1;
        /**
         * 媒体准备就绪
         */
        public static final int PREPARE_COMPLETE = 2;
        /**
         * 开始播放
         */
        public static final int PLAY_START = 3;
        /**
         * 播放中
         */
        public static final int PLAYING = 4;
        /**
         * 暂停中
         */
        public static final int PAUSE = 5;
        /**
         * 停止播放
         */
        public static final int STOP = 6;
        /**
         * 播放完成
         */
        public static final int COMPLETE = 7;
        /**
         * 正在缓冲
         */
        public static final int BUFFERING = 8;
        /**
         * 缓冲完成
         */
        public static final int BUFFER_COMPLETE = 10;
    }

}
