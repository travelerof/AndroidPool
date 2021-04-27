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
 * @Desc yyyy年MM月dd日
 */
class DateView3 implements IDateHandler, OnChoiceScrollListener {

    private IDate iDate;
    private ChoiceView yearView;
    private ChoiceView monthView;
    private ChoiceView dayView;

    private int year;
    private int month;
    private int day;

    @Override
    public void init(@NonNull Context context, AttributeSet attrs, @NonNull IDate iDate) {
        this.iDate = iDate;
        yearView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "年" : "");
        monthView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "月" : "");
        dayView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "日" : "");
        yearView.setOnChoiceScrollListener(this);
        monthView.setOnChoiceScrollListener(this);
        dayView.setOnChoiceScrollListener(this);
        iDate.addChildView(yearView,monthView,dayView);
        yearView.setData(iDate.getData(iDate.getMinYear(), iDate.getNowYear()));
        monthView.setData(iDate.getData(1, 12));
        dayView.setData(iDate.getData(1, iDate.getMaxDayofMonth(iDate.getNowYear(), iDate.getNowMonth())));
        choiceItem(iDate.getDateValue(iDate.getNowYear()), iDate.getDateValue(iDate.getNowMonth()),
                iDate.getDateValue(iDate.getNowDay()));
    }

    @Override
    public void choiceItem(String... values) {
        if (values == null || values.length < 3) {
            return;
        }
        year = DataUtils.toInt(values[0]);
        month = DataUtils.toInt(values[1]);
        day = DataUtils.toInt(values[2]);
        yearView.choice(iDate.getPosition(yearView.getData(), values[0]));
        monthView.choice(iDate.getPosition(monthView.getData(), values[1]));
        dayView.setData(iDate.getData(1, iDate.getMaxDayofMonth(year, month)));
        dayView.choice(iDate.getPosition(dayView.getData(), values[2]));
    }

    @Override
    public int getType() {
        return DateType.YEAR_MONTH_DAY;
    }

    @Override
    public void onChoiced(ChoiceView view, String choice) {
        int value = DataUtils.toInt(choice);
        if (view == yearView) {
            year = value;
        } else if (view == monthView) {
            month = value;
            dayView.setData(iDate.getData(1, iDate.getMaxDayofMonth(year, month)));
            if (dayView.getChoiceIndex()>= dayView.getData().size()) {
                dayView.choice(dayView.getData().size() - 1);
            }
        } else {
            day = value;
        }
        iDate.setDate(year,month,day,-1,-1);
    }
}
