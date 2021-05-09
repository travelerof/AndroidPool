package com.hyg.mch.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hyg.hdialog.BaseDialog;
import com.hyg.hdialog.HDialog;
import com.hyg.hdialog.OnDialogClickListener;
import com.hyg.hdialog.OnTextListener;
import com.hyg.mch.R;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        setListener();

    }

    private void setListener() {
        findViewById(R.id.dialog_default_btn).setOnClickListener(this);
        findViewById(R.id.dialog_custom_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dialog_default_btn:
                BaseDialog dialog = new BaseDialog(this);
                dialog.show();
                break;
            case R.id.dialog_custom_btn:
                new HDialog.Builder(this)
                        .title("测试标题")
                        .message("测试文本", new OnTextListener() {
                            @Override
                            public void onText(TextView textView) {
                                textView.setGravity(Gravity.CENTER);
                            }
                        })
                        .finish("完成", new OnDialogClickListener() {
                            @Override
                            public void onClick(BaseDialog dialog, View v) {
                                dialog.dismiss();
                            }
                        })
                        .cancel("取消", new OnDialogClickListener() {
                            @Override
                            public void onClick(BaseDialog dialog, View v) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }
}