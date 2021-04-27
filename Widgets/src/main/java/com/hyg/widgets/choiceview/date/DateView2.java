package com.hyg.widgets.choiceview.date;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.hyg.utils.DataUtils;
import com.hyg.widgets.choiceview.ChoiceView;
import com.hyg.widgets.choiceview.OnChoiceScrollListener;

/**
 * @Author 韩永刚
 * @Date 2021/02/09
 * @Desc yyyy年MM月选择器
 */
class DateView2 implements IDateHandler, OnChoiceScrollListener {

    private IDate iDate;

    private ChoiceView yearView;
    private ChoiceView monthView;

    private int year;
    private int month;

    @Override
    public void init(@NonNull Context context, AttributeSet attrs, @NonNull IDate iDate) {
        this.iDate = iDate;
        yearView = new ChoiceView(context,attrs,0,iDate.isShowUnit() ? "年" : "");
        monthView = new ChoiceView(context,attrs,0,iDate.isShowUnit() ? "月" : "");
        yearView.setOnChoiceScrollListener(this);
        monthView.setOnChoiceScrollListener(this);
        iDate.addChildView(yearView,monthView);
        yearView.setData(iDate.getData(iDate.getMinYear(),iDate.getNowYear()));
        monthView.setData(iDate.getData(1,12));
        choiceItem(iDate.getDateValue(iDate.getNowYear()), iDate.getDateValue(iDate.getNowMonth()));
    }

    @Override
    public void choiceItem(String... values) {
        if (values == null || values.length < 2) {
            return;
        }
        year = DataUtils.toInt(values[0]);
        month = DataUtils.toInt(values[1]);
        yearView.choice(iDate.getPosition(yearView.getData(), values[0]));
        monthView.choice(iDate.getPosition(monthView.getData(), values[1]));
    }

    @Override
    public int getType() {
        return DateType.YEAR_MONTH;
    }

    @Override
    public void onChoiced(ChoiceView view, String choice) {
        int value = DataUtils.toInt(choice);
        if (view == yearView) {
            year = value;
        } else {
            month = value;
        }
        iDate.setDate(year,month,-1,-1,-1);
    }
}
