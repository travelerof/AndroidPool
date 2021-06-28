package com.hyg.mch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hyg.utils.BitmapUtils;

import java.io.File;

public class UtilsActivity extends AppCompatActivity {

    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils);
        init();
    }

    private void init() {
        mIv = findViewById(R.id.utils_bitmap_iv);
        findViewById(R.id.utils_compress_bitmap_btn).setOnClickListener(this::onClick);
    }

    //146313216
    private void onClick(View view){
        switch (view.getId()) {
            case R.id.utils_compress_bitmap_btn:
                File file = new File(getCacheDir(),"test.png");
                boolean compress = BitmapUtils.compress(BitmapUtils.getBitmap(this, R.mipmap.splash), Bitmap.CompressFormat.PNG, 80, file.getAbsolutePath());
                if (compress) {
                    Glide.with(this)
                            .load(new File(file.getAbsolutePath()))
                            .into(mIv);
                }
                break;
        }
    }
}