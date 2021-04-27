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
 * @Desc 年份选择器
 */
class DateView1 implements IDateHandler, OnChoiceScrollListener {

    private IDate iDate;
    private ChoiceView yearView;

    private int year;

    @Override
    public void init(@NonNull Context context, AttributeSet attrs, @NonNull IDate iDate) {
        this.iDate = iDate;
        yearView = new ChoiceView(context, attrs,0,iDate.isShowUnit() ? "年" : "");
        yearView.setOnChoiceScrollListener(this);
        iDate.addChildView(yearView);
        yearView.setData(iDate.getData(iDate.getMinYear(), iDate.getNowDay()));
        choiceItem(iDate.getDateValue(iDate.getNowYear()));
    }

    @Override
    public void choiceItem(String... values) {
        if (values == null || values.length < 1) {
            return;
        }
        year = DataUtils.toInt(values[0]);
        yearView.choice(iDate.getPosition(yearView.getData(), values[0]));
    }

    @Override
    public int getType() {
        return DateType.YEAR;
    }

    @Override
    public void onChoiced(ChoiceView view, String choice) {
        year = DataUtils.toInt(choice);
        iDate.setDate(year,-1,-1,-1,-1);
    }
}
