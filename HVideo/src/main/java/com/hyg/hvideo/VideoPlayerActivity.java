package com.hyg.hvideo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.device.DeviceInfo;
import com.google.android.exoplayer2.device.DeviceListener;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayerActivity extends AppCompatActivity {

    private static final String TAG = VideoPlayerActivity.class.getSimpleName();

    //    private StyledPlayerView mPlayerView;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;

    public static void invoke(AppCompatActivity activity, String url) {
        Intent intent = new Intent(activity, VideoPlayerActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);

    }

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        mPlayerView = findViewById(R.id.video_play_view);
        mUrl = getIntent().getStringExtra("url");
        setListener();
        init();
    }

    private void setListener() {

        mPlayerView.setAspectRatioListener(new AspectRatioFrameLayout.AspectRatioListener() {
            @Override
            public void onAspectRatioUpdated(float targetAspectRatio, float naturalAspectRatio, boolean aspectRatioMismatch) {
                Log.i(TAG, "=====onAspectRatioUpdated=======" + targetAspectRatio + "=======" + naturalAspectRatio + "======" + aspectRatioMismatch);
            }
        });
        mPlayerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                Log.i(TAG, "=====onVisibilityChange=======" + visibility);
            }
        });
//        mPlayerView.setControllerOnFullScreenModeChangedListener(new StyledPlayerControlView.OnFullScreenModeChangedListener() {
//            @Override
//            public void onFullScreenModeChanged(boolean isFullScreen) {
//                Log.i(TAG,"=====onFullScreenModeChanged======="+isFullScreen);
//            }
//        });
    }

    private void init() {
        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(mUrl)
                .setClipStartPositionMs(2000)
                .setClipEndPositionMs(15*1000)
                .build();
        MediaSourceFactory mediaSourceFactory = new DefaultMediaSourceFactory(this)
                .setAdViewProvider(mPlayerView);
        DefaultTrackSelector trackSelection = new DefaultTrackSelector(this);
        mPlayer = new SimpleExoPlayer.Builder(this)
                .setMediaSourceFactory(mediaSourceFactory)
                .setTrackSelector(trackSelection)
                .build();
        mPlayer.addAnalyticsListener(new EventLogger(trackSelection));
        mPlayer.setPlayWhenReady(true);
        mPlayer.addListener(new VideoPlayEvent());
        mPlayer.setAudioAttributes(AudioAttributes.DEFAULT, true);
        mPlayerView.setPlayer(mPlayer);

        mPlayer.setMediaItem(mediaItem);
        mPlayer.addDeviceListener(new VideoDevice());
        mPlayer.prepare();
//        mPlayer.play();
    }

    private class VideoPlayEvent implements Player.EventListener {

        @Override
        public void onEvents(Player player, Player.Events events) {
            Log.i(TAG,"=========onEvents======");
        }

        @Override
        public void onExperimentalOffloadSchedulingEnabledChanged(boolean offloadSchedulingEnabled) {
            Log.i(TAG,"=========onExperimentalOffloadSchedulingEnabledChanged======"+offloadSchedulingEnabled);
        }

        @Override
        public void onExperimentalSleepingForOffloadChanged(boolean sleepingForOffload) {
            Log.i(TAG,"=========onExperimentalSleepingForOffloadChanged======"+sleepingForOffload);
        }

        @Override
        public void onIsLoadingChanged(boolean isLoading) {
            Log.i(TAG,"=========onIsLoadingChanged======"+isLoading);
        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            Log.i(TAG,"=========onIsPlayingChanged======"+isPlaying);
        }

        @Override
        public void onTimelineChanged(Timeline timeline, int reason) {
            Log.i(TAG,"=========onTimelineChanged======"+reason);
        }
    }


    private class VideoDevice implements DeviceListener {
        @Override
        public void onDeviceInfoChanged(DeviceInfo deviceInfo) {
            Log.i(TAG, "======onDeviceInfoChanged=====");
        }

        @Override
        public void onDeviceVolumeChanged(int volume, boolean muted) {
            Log.i(TAG, "======onDeviceVolumeChanged=====" + volume + "========" + muted);
        }
    }

}