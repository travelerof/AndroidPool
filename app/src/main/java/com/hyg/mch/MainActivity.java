package com.hyg.mch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById(R.id.main_progress).setOnClickListener(this);
        findViewById(R.id.main_loadding).setOnClickListener(this);
        findViewById(R.id.main_permission_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.main_progress:
                intent.setClass(this,ProgressActivity.class);
                break;
            case R.id.main_loadding:
                break;
            case R.id.main_permission_btn:
                break;
        }

        startActivity(intent);
    }
}