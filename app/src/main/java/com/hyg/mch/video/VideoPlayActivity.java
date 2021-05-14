package com.hyg.mch.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;

import com.hyg.hvideo.HVideoSource;
import com.hyg.mch.R;
import com.hyg.videoui.widget.HVideoView;

import java.io.IOException;

public class VideoPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        HVideoView videoView = findViewById(R.id.video_view_v);
        HVideoSource hVideoSource = new HVideoSource();
        hVideoSource.setUri(Uri.parse("android.resource://com.hyg.mch/test.mp4"));
        videoView.setVideoSource(hVideoSource);
    }
}