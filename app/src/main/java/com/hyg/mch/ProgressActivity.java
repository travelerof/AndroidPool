package com.hyg.mch;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.hyg.widgets.ClockView;
import com.hyg.widgets.progress.HProgressView;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        HProgressView progressView = findViewById(R.id.progress_view);
        HProgressView progressCircle = findViewById(R.id.progress_circle_view);
        ClockView clockView = findViewById(R.id.progress_clock_view);
        progressView.setTotalProgress(100);
        progressView.setPreviewProgress(40);
        progressView.setProgress(20);

        progressCircle.setTotalProgress(100);
        progressCircle.setPreviewProgress(40);
        progressCircle.setProgress(20);
        clockView.start();
    }
}