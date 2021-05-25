package com.hyg.hvideo.exo;

import android.content.Context;
import android.os.Handler;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.video.VideoListener;
import com.hyg.hlog.HLog;
import com.hyg.hvideo.HVideoConstant;
import com.hyg.hvideo.HVideoPlayer;
import com.hyg.hvideo.HVideoSource;
import com.hyg.hvideo.model.Error;
import com.hyg.hvideo.model.PlayInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @Author hanyonggang
 * @Date 2021/5/12 0012
 * @Desc
 */
public class HExoPlayer extends HVideoPlayer implements VideoListener, Player.EventListener {

    public static final String TAG = HExoPlayer.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;

    private RenderersFactory mRenderersFactory;
    private TrackSelector mTrackSelector;
    private MediaSourceFactory mMediaSourceFactory;
    private LoadControl mLoadControl;
    private BandwidthMeter mBandwidthMeter;
    private AnalyticsCollector mAnalyticsCollector;
    private boolean isPreparing;
    private boolean isBuffering;
    private HExoVideoHelper mExoVideoHelper;

    private MediaSourceEventListener mMediaSourceEventListener = new MediaSourceEventListener() {
        @Override
        public void onLoadStarted(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            HLog.i(TAG,">>>>onLoadStarted>>>>:");
        }

        @Override
        public void onLoadCompleted(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            HLog.i(TAG,">>>>onLoadCompleted>>>>:");
        }

        @Override
        public void onLoadCanceled(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
            HLog.i(TAG,">>>>onLoadCanceled>>>>:");
        }

        @Override
        public void onLoadError(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException error, boolean wasCanceled) {
            HLog.i(TAG,">>>>onLoadError>>>>:");
            setError(new Error(HVideoConstant.ERROR.VIDEO_PREPARE_FAILED,"视频准备失败"));
            isPreparing = false;
        }

        @Override
        public void onUpstreamDiscarded(int windowIndex, MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            HLog.i(TAG,">>>>onUpstreamDiscarded>>>>:");
        }

        @Override
        public void onDownstreamFormatChanged(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
            HLog.i(TAG,">>>>onDownstreamFormatChanged>>>>:");
            isPreparing = false;
            setInfo(new PlayInfo(HVideoConstant.STATUS.PREPARE_COMPLETE));
        }
    };


    public HExoPlayer(@NonNull @org.jetbrains.annotations.NotNull Context context) {
        super(context);
    }

    @Override
    public void initPlayer() {
        initConfig();
        mExoPlayer = new SimpleExoPlayer.Builder(
                getContext(),
                mRenderersFactory,
                mTrackSelector,
                mMediaSourceFactory,
                mLoadControl,
                mBandwidthMeter,
                mAnalyticsCollector)
                .build();
        mExoPlayer.addVideoListener(this);
        mExoPlayer.addListener(this);
        mExoVideoHelper = new HExoVideoHelper(mMediaSourceFactory);
    }

    private void initConfig() {
        if (mRenderersFactory == null) {
            setRenderersFactory(new DefaultRenderersFactory(getContext()));
        }
        if (mTrackSelector == null) {
            setTrackSelector(new DefaultTrackSelector(getContext()));
        }
        if (mMediaSourceFactory == null) {
            setMediaSourceFactory(new DefaultMediaSourceFactory(getContext()));
        }
        if (mLoadControl == null) {
            setLoadControl(new DefaultLoadControl());
        }
        if (mBandwidthMeter == null) {
            setBandwidthMeter(DefaultBandwidthMeter.getSingletonInstance(getContext()));
        }
        if (mAnalyticsCollector == null) {
            setAnalyticsCollector(new AnalyticsCollector(Clock.DEFAULT));
        }
    }



    @Override
    public void prepare() {
        if (mExoPlayer != null && !isPreparing) {
            setInfo(new PlayInfo(HVideoConstant.STATUS.PREPARE_START));
            HVideoSource hVideoSource = mVideoSources.get(currentIndex);
            if (hVideoSource == null) {
                setError(new Error(HVideoConstant.ERROR.VIDEO_SOURCE_EMPTY,"视频资源为空"));
                return;
            }
            MediaSource mediaSource = mExoVideoHelper.getMedia(hVideoSource);
            if (mediaSource == null) {
                setError(new Error(HVideoConstant.ERROR.VIDEO_INSTALL_FAILED,"视频资源组装失败"));
                return;
            }
            isPreparing = true;
            Handler handler = new Handler();
            mediaSource.addEventListener(handler,mMediaSourceEventListener);
            mediaSource.addDrmEventListener(handler, new DrmSessionEventListener() {
                @Override
                public void onDrmSessionAcquired(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                    HLog.i(TAG,">>>>>>>>>onDrmSessionAcquired>>>>");
                }

                @Override
                public void onDrmKeysLoaded(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                    HLog.i(TAG,">>>>>>>>>onDrmKeysLoaded>>>>");
                }

                @Override
                public void onDrmSessionManagerError(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId, Exception error) {
                    HLog.i(TAG,">>>>>>>>>onDrmSessionManagerError>>>>");
                }

                @Override
                public void onDrmKeysRestored(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                    HLog.i(TAG,">>>>>>>>>onDrmKeysRestored>>>>");
                }

                @Override
                public void onDrmKeysRemoved(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId) {
                    HLog.i(TAG,">>>>>>>>>onDrmKeysRemoved>>>>");
                }

                @Override
                public void onDrmSessionReleased(int windowIndex, @Nullable @org.jetbrains.annotations.Nullable MediaSource.MediaPeriodId mediaPeriodId) {

                }
            });
            setInfo(new PlayInfo(HVideoConstant.STATUS.PREPAREING));
            mExoPlayer.setMediaSource(mediaSource);
            mExoPlayer.prepare();
        }
    }

    @Override
    public void play() {
        if (mExoPlayer != null && !mExoPlayer.isPlaying()) {
            mExoPlayer.play();
            setInfo(new PlayInfo(HVideoConstant.STATUS.PLAY_START));
            setInfo(new PlayInfo(HVideoConstant.STATUS.PLAYING));
        }
    }

    @Override
    public void pause() {
        if (mExoPlayer != null && mExoPlayer.isPlaying()) {
            mExoPlayer.pause();
            setInfo(new PlayInfo(HVideoConstant.STATUS.PAUSE));
        }
    }

    @Override
    public void stop() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            setInfo(new PlayInfo(HVideoConstant.STATUS.PAUSE));
        }
    }

    @Override
    public void reset() {
        if (mExoPlayer != null) {
            mExoPlayer.stop(true);
        }
        isBuffering = false;
        isPreparing = false;
    }

    @Override
    public boolean isPlaying() {
        return mExoPlayer != null && mExoPlayer.isPlaying();
    }

    @Override
    public void seekTo(long currentPosition) {
        if (mExoPlayer != null) {
            mExoPlayer.seekTo(currentPosition);
        }
    }

    @Override
    public long getDuration() {
        return mExoPlayer == null ? 0 : mExoPlayer.getDuration();
    }

    @Override
    public void setCurrentPosition(int currentPosition) {
        if (mExoPlayer != null) {

        }
    }

    @Override
    public long getCurrentPosition() {
        return mExoPlayer == null ? 0 : mExoPlayer.getCurrentPosition();
    }

    @Override
    public void setSpeed(float speed) {
        if (mExoPlayer != null) {
            mExoPlayer.setPlaybackParameters(new PlaybackParameters(speed));
        }
    }

    @Override
    public float getSpeed() {
        return mExoPlayer == null ? 1f : mExoPlayer.getPlaybackParameters().speed;
    }

    @Override
    public void setVolume(float volume) {
        if (mExoPlayer != null) {
            mExoPlayer.setVolume(volume);
        }
    }

    @Override
    public long getBufferedPosition() {
        return mExoPlayer == null ? 0 : mExoPlayer.getBufferedPosition();
    }

    @Override
    public void setSurface(@NonNull @NotNull Surface surface) {
        if (mExoPlayer != null) {
            mExoPlayer.setVideoSurface(surface);
        }
    }

    @Override
    public void onPlaybackStateChanged(int state) {
        HLog.i(TAG,">>>>onPlaybackStateChanged>>>>:state="+state);
        if (isPreparing) {
            return;
        }
        switch (state) {
            case Player.STATE_BUFFERING:
                isBuffering = true;
                setInfo(new PlayInfo(HVideoConstant.STATUS.BUFFERING));
                break;
            case Player.STATE_READY:
                isBuffering = false;
                setInfo(new PlayInfo(HVideoConstant.STATUS.BUFFER_COMPLETE));
                break;
        }
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        HLog.i(TAG,">>>>onVideoSizeChanged>>>>:");
    }

    @Override
    public void onSurfaceSizeChanged(int width, int height) {
        HLog.i(TAG,">>>>onSurfaceSizeChanged>>>>:");
    }

    @Override
    public void onRenderedFirstFrame() {
        HLog.i(TAG,">>>>onRenderedFirstFrame>>>>:");
    }

    public void setRenderersFactory(RenderersFactory renderersFactory) {
        mRenderersFactory = renderersFactory;
    }

    public void setTrackSelector(TrackSelector trackSelector) {
        mTrackSelector = trackSelector;
    }

    public void setMediaSourceFactory(MediaSourceFactory mediaSourceFactory) {
        mMediaSourceFactory = mediaSourceFactory;
    }

    public void setLoadControl(LoadControl loadControl) {
        mLoadControl = loadControl;
    }

    public void setBandwidthMeter(BandwidthMeter bandwidthMeter) {
        mBandwidthMeter = bandwidthMeter;
    }

    public void setAnalyticsCollector(AnalyticsCollector analyticsCollector) {
        mAnalyticsCollector = analyticsCollector;
    }

    @Override
    public void release() {
        super.release();
        mExoPlayer.release();
        mExoPlayer.removeListener(this);
        mExoPlayer.removeVideoListener(this);
        mExoVideoHelper = null;
    }
}
