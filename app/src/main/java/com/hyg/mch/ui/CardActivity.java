package com.hyg.mch.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hyg.mch.R;
import com.hyg.widgets.swipecard.SwipeCardView;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {

    private List<SwipeCardBean> data;
    private SwipeCardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        mCardView = findViewById(R.id.card_view);
        init();
    }

    private void init() {
        initData();
        CardAdapter1 cardAdapter1 = new CardAdapter1(this,data);
        mCardView.setAdapter(cardAdapter1);
    }

    private void initData() {
        data = new ArrayList<>();
        SwipeCardBean bean1 = new SwipeCardBean();
        bean1.resId = R.drawable.shape_black_bg;
        data.add(bean1);
        SwipeCardBean bean2 = new SwipeCardBean();
        bean2.resId = R.drawable.shape_blue_bg;
        data.add(bean2);
        SwipeCardBean bean3 = new SwipeCardBean();
        bean3.resId = R.drawable.shape_red_bg;
        data.add(bean3);
        SwipeCardBean bean4 = new SwipeCardBean();
        bean4.resId = R.drawable.shape_yellow_bg;
        data.add(bean4);

    }
}