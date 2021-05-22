package com.hyg.mch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hyg.mch.dialog.DialogActivity;
import com.hyg.mch.suspend.SuspendActivity;
import com.hyg.mch.ui.CardActivity;
import com.hyg.mch.video.VideoPlayActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById(R.id.main_progress).setOnClickListener(this);
        findViewById(R.id.main_dialog_btn).setOnClickListener(this);
        findViewById(R.id.main_loadding).setOnClickListener(this);
        findViewById(R.id.main_permission_btn).setOnClickListener(this);
        findViewById(R.id.main_card_btn).setOnClickListener(this);
        findViewById(R.id.main_video_btn).setOnClickListener(this);
        findViewById(R.id.mian_suspend_btn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_progress:
                Intent intent1 = new Intent(this,ProgressActivity.class);
                startActivity(intent1);
                break;
            case R.id.main_dialog_btn:
                Intent intent2 = new Intent(this,DialogActivity.class);
                startActivity(intent2);
                break;
            case R.id.main_loadding:
                break;
            case R.id.main_permission_btn:
                Intent intent6 = new Intent(this,PermissionActivity.class);
                startActivity(intent6);
                break;
            case R.id.main_card_btn:
                Intent intent3 = new Intent(this, CardActivity.class);
                startActivity(intent3);
                break;
            case R.id.main_video_btn:
                Intent intent4 = new Intent(this,VideoPlayActivity.class);
                startActivity(intent4);
                break;
            case R.id.mian_suspend_btn:
                Intent intent5 = new Intent(this, SuspendActivity.class);
                startActivity(intent5);
                break;
        }
    }
}