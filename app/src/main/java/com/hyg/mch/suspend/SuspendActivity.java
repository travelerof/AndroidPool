package com.hyg.mch.suspend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.hyg.hlog.HLog;
import com.hyg.mch.R;

import java.util.ArrayList;
import java.util.List;

public class SuspendActivity extends AppCompatActivity {

    private MutableLiveData<List<String>> mLiveData = new MutableLiveData<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend);

        findViewById(R.id.suspend_show_btn).setOnClickListener(this::onClick);
        findViewById(R.id.suspend_hide_btn).setOnClickListener(this::onClick);
        findViewById(R.id.suspend_log_btn).setOnClickListener(this::onClick);
        mLiveData.observeForever(new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                Log.i("tset","====结果==="+strings.size());
            }
        });
    }

    private void onClick(View v) {
        int id = v.getId();
        if (id == R.id.suspend_show_btn) {
            mLiveData.setValue(getStrings());
        } else if (id == R.id.suspend_hide_btn) {
            List<String> value = mLiveData.getValue();
            if (value != null) {
                Log.i("tset","====获取==="+value.size());
            }
        } else if (id == R.id.suspend_log_btn) {
            HLog.i("test11","=======12==========");
            HLog.i("test11","=======123==========");
            HLog.i("test11","wrfw");
            HLog.i("test11","========fd=========");
            HLog.e("test11","=========dfsdf========");
        }
    }

    private List<String> getStrings(){
        List<String> data = new ArrayList<>();
        int count = (int) (Math.random()*10);
        for (int i = 0; i < count; i++) {
            data.add("测试");
        }
        Log.i("tset","====组装==="+data.size());
        return data;
    }
}