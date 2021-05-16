package com.hyg.mch.suspend;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hyg.hlog.suspend.FloatBall;
import com.hyg.mch.R;

public class SuspendActivity extends AppCompatActivity {

    private FloatBall mFloatBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend);
        mFloatBall = new FloatBall.Builder(this)
                .create();
        findViewById(R.id.suspend_show_btn).setOnClickListener(this::onClick);
        findViewById(R.id.suspend_hide_btn).setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        int id = v.getId();
        if (id == R.id.suspend_show_btn) {
            mFloatBall.show();
        } else if (id == R.id.suspend_hide_btn) {
            mFloatBall.dismiss();
        }
    }
}