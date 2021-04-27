package com.hyg.widgets.choiceview.date;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.hyg.utils.DataUtils;
import com.hyg.widgets.choiceview.ChoiceView;
import com.hyg.widgets.choiceview.OnChoiceScrollListener;

/**
 * @Author 韩永刚
 * @Date 2021/02/10
 * @Desc HH时mm分
 */
class DateView5 implements IDateHandler, OnChoiceScrollListener {

    private IDate iDate;
    private ChoiceView hourView;
    private ChoiceView minuteView;

    private int hour;
    private int minute;
    @Override
    public void init(@NonNull Context context, AttributeSet attrs, @NonNull IDate iDate) {
        this.iDate = iDate;
        hourView = new ChoiceView(context,attrs,0,iDate.isShowUnit() ? "时" : "");
        minuteView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "分" : "");
        hourView.setData(iDate.getData(0,23));
        minuteView.setData(iDate.getData(0,59));
        hourView.setOnChoiceScrollListener(this);
        minuteView.setOnChoiceScrollListener(this);
        iDate.addChildView(hourView,minuteView);
        choiceItem(iDate.getDateValue(iDate.getNowHour()), iDate.getDateValue(iDate.getNowMinute()));
    }

    @Override
    public void choiceItem(String... values) {
        if (values == null || values.length < 2) {
            return;
        }
        hour = DataUtils.toInt(values[0]);
        minute = DataUtils.toInt(values[1]);
        hourView.choice(iDate.getPosition(hourView.getData(), values[0]));
        minuteView.choice(iDate.getPosition(minuteView.getData(), values[1]));
    }

    @Override
    public void onChoiced(ChoiceView view, String choice) {
        int value = DataUtils.toInt(choice);
        if (view == hourView) {
            hour = value;
        }else {
            minute = value;
        }
        iDate.setDate(-1,-1,-1,hour,minute);
    }

    @Override
    public int getType() {
        return DateType.HOUR_MINUTE;
    }
}
