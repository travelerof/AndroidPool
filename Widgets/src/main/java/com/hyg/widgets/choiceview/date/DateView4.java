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
 * @Desc yyyy年MM月dd日HH时mm分
 */
class DateView4 implements IDateHandler, OnChoiceScrollListener {

    private IDate iDate;
    private ChoiceView yearView;
    private ChoiceView monthView;
    private ChoiceView dayView;
    private ChoiceView hourView;
    private ChoiceView minuteView;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    public void init(@NonNull Context context, AttributeSet attrs, @NonNull IDate iDate) {
        this.iDate = iDate;
        yearView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "年" : "");
        monthView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "月" : "");
        dayView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "日" : "");
        hourView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "时" : "");
        minuteView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "分" : "");
        yearView.setOnChoiceScrollListener(this);
        monthView.setOnChoiceScrollListener(this);
        dayView.setOnChoiceScrollListener(this);
        hourView.setOnChoiceScrollListener(this);
        minuteView.setOnChoiceScrollListener(this);
        iDate.addChildView(yearView, monthView, dayView, hourView, minuteView);
        setDate();
    }

    private void setDate() {
        yearView.setData(iDate.getData(iDate.getMinYear(), iDate.getNowYear()));
        monthView.setData(iDate.getData(1, 12));
        dayView.setData(iDate.getData(1, iDate.getMaxDayofMonth(iDate.getNowYear(), iDate.getNowDay())));
        hourView.setData(iDate.getData(0, 23));
        minuteView.setData(iDate.getData(0, 59));
        choiceItem(iDate.getDateValue(iDate.getNowYear()), iDate.getDateValue(iDate.getNowMonth()),
                iDate.getDateValue(iDate.getNowDay()), iDate.getDateValue(iDate.getNowHour()),
                iDate.getDateValue(iDate.getNowMinute()));
    }

    @Override
    public void choiceItem(String... values) {
        if (values == null || values.length < 5) {
            return;
        }
        year = DataUtils.toInt(values[0]);
        month = DataUtils.toInt(values[1]);
        day = DataUtils.toInt(values[2]);
        hour = DataUtils.toInt(values[3]);
        minute = DataUtils.toInt(values[4]);
        yearView.choice(iDate.getPosition(yearView.getData(), values[0]));
        monthView.choice(iDate.getPosition(monthView.getData(), values[1]));
        dayView.setData(iDate.getData(1, iDate.getMaxDayofMonth(year, month)));
        dayView.choice(iDate.getPosition(dayView.getData(), values[2]));
        hourView.choice(iDate.getPosition(hourView.getData(), values[3]));
        minuteView.choice(iDate.getPosition(minuteView.getData(), values[4]));
    }

    @Override
    public int getType() {
        return DateType.YEAR_MONTH_DAY_HOUR_MINUTE;
    }

    @Override
    public void onChoiced(ChoiceView view, String choice) {
        int value = DataUtils.toInt(choice);
        if (view == yearView) {
            year = value;
        } else if (view == monthView) {
            month = value;
            dayView.setData(iDate.getData(1, iDate.getMaxDayofMonth(year, month)));
            if (dayView.getChoiceIndex() >= dayView.getData().size()) {
                dayView.choice(dayView.getData().size() - 1);
            }
        } else if (view == dayView) {
            day = value;
        } else if (view == hourView) {
            hour = value;
        } else {
            minute = value;
        }
        iDate.setDate(year,month,day,hour,minute);
    }
}
