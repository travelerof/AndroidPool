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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SuspendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend);

        findViewById(R.id.suspend_show_btn).setOnClickListener(this::onClick);
        findViewById(R.id.suspend_hide_btn).setOnClickListener(this::onClick);
        findViewById(R.id.suspend_log_btn).setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        int id = v.getId();
        if (id == R.id.suspend_show_btn) {
        } else if (id == R.id.suspend_hide_btn) {
        } else if (id == R.id.suspend_log_btn) {
            HLog.i("test","=======12==========");
            HLog.i("test","=======123==========");
            HLog.i("test","wrfw");
            HLog.i("test","========fd=========");
            HLog.e("test","=========dfsdf========");
            HLog.w("test","=========dfsdf========");
            HLog.w("test","=========dfsdf========");
            HLog.v("test","=========dfsdf========");
            HLog.v("test","=========dfsdf========");
            HLog.json("test",getObject());
            HLog.json("test",getArray());
        }
    }

    private String getObject(){
        JSONObject object = new JSONObject();
        try {
            object.put("name","张三");
            object.put("age",18);
            object.put("address","西安市");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    private String getArray(){
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < 3; i++) {
                JSONObject object = new JSONObject();
                object.put("name","张三");
                object.put("age",18);
                object.put("address","西安市");
                array.put(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }

}