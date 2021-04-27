package com.hyg.widgets.choiceview.date;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hyg.utils.DensityUtils;
import com.hyg.utils.StringUtils;
import com.hyg.widgets.R;
import com.hyg.widgets.choiceview.ChoiceView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/02/03
 * @Desc 时间选择器
 */
public class DateChoiceView extends FrameLayout implements IDate {

    private static final String TAG = DateChoiceView.class.getName();

    private int minYear;
    private boolean showUnit = true;
    @DateType.Type
    private int type = DateType.YEAR_MONTH_DAY;

    private int limit = ChoiceView.LIMIT_5;
    private int itemHeight;
    private boolean shade = true;
    private int shadeColor;
    private IDateHandler iDateHandler;
    private LinearLayout rootLayout;
    private DateShadeView shadeView;

    private OnDateChangedListener onDateChangedListener;

    public DateChoiceView(Context context) {
        this(context, null);
    }

    public DateChoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateChoiceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        itemHeight = DensityUtils.dip2Px(context, 40);
        shadeColor = getResources().getColor(R.color.color_99ffffff);
        //默认最小1900年
        minYear = 1900;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DateChoiceView);
            type = array.getInt(R.styleable.DateChoiceView_dateType, DateType.YEAR_MONTH_DAY);
            limit = array.getInt(R.styleable.DateChoiceView_choiceLimit, ChoiceView.LIMIT_5);
            itemHeight = array.getDimensionPixelSize(R.styleable.DateChoiceView_choiceItemHeight, DensityUtils.dip2Px(context, 40));
            shade = array.getBoolean(R.styleable.DateChoiceView_shade, true);
            shadeColor = array.getColor(R.styleable.DateChoiceView_shade_bg, getResources().getColor(R.color.color_99ffffff));
            showUnit = array.getBoolean(R.styleable.DateChoiceView_showUnit, true);
            array.recycle();
        }
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_date_layout, null);
        rootLayout = rootView.findViewById(R.id.date_choice_parent_layot);
        shadeView = rootView.findViewById(R.id.date_shade_view);
        initShadeView();
        addView(rootView);
        iDateHandler = createDateHandler(context, attrs);
    }

    private void initShadeView() {
        shadeView.setItemHeight(itemHeight);
        shadeView.setLimit(limit);
        shadeView.setShadeColor(shadeColor);
        hideShade();
    }

    private IDateHandler createDateHandler(Context context, AttributeSet attrs) {
        IDateHandler iDateHandler;
        switch (type) {
            case DateType.YEAR:
                iDateHandler = new DateView1();
                break;
            case DateType.YEAR_MONTH:
                iDateHandler = new DateView2();
                break;
            case DateType.YEAR_MONTH_DAY_HOUR_MINUTE:
                iDateHandler = new DateView4();
                break;
            case DateType.HOUR_MINUTE:
                iDateHandler = new DateView5();
                break;
            default:
                iDateHandler = new DateView3();
                break;
        }
        iDateHandler.init(context, attrs, this);
        return iDateHandler;
    }

    private void hideShade() {
        if (shade) {
            shadeView.setVisibility(VISIBLE);
        } else {
            shadeView.setVisibility(GONE);
        }
    }

    private LinearLayout.LayoutParams getChildParams() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.onDateChangedListener = onDateChangedListener;
    }

    public void choice(long time) {
        choice(new Date(time));
    }

    public void choice(@NonNull Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        switch (iDateHandler.getType()) {
            case DateType.YEAR:
                iDateHandler.choiceItem(getDateValue(year));
                break;
            case DateType.YEAR_MONTH:
                iDateHandler.choiceItem(getDateValue(year), getDateValue(month));
                break;
            case DateType.YEAR_MONTH_DAY:
                iDateHandler.choiceItem(getDateValue(year), getDateValue(month), getDateValue(day));
                break;
            case DateType.YEAR_MONTH_DAY_HOUR_MINUTE:
                iDateHandler.choiceItem(getDateValue(year), getDateValue(month), getDateValue(day), getDateValue(hour), getDateValue(minute));
                break;
            case DateType.HOUR_MINUTE:
                iDateHandler.choiceItem(getDateValue(hour), getDateValue(minute));
                break;
        }
    }

    @Override
    public void addChildView(@NonNull ChoiceView... choiceView) {
        for (ChoiceView view : choiceView) {
            rootLayout.addView(view, getChildParams());
            String tag = view.getChoiceTAG();
            if (!TextUtils.isEmpty(tag)) {
                rootLayout.addView(createUnitView(tag), getChildParams());
            }
        }
    }

    private TextView createUnitView(String text) {
        TextView textView = new TextView(getContext());
        textView.setTextColor(getResources().getColor(R.color.color_555555));
        textView.setTextSize(getResources().getDimension(R.dimen.textsize_12) / 2);
        textView.setText(text);
        return textView;
    }

    @Override
    public int getItemHeight() {
        return itemHeight;
    }

    @Override
    public List<String> getData(int startIndex, int endIndex) {
        List<String> data = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            data.add(i < 10 ? ("0" + i) : "" + i);
        }
        return data;
    }

    @Override
    public int getMinYear() {
        return minYear;
    }

    @Override
    public int getNowYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    @Override
    public int getNowMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    @Override
    public int getNowDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getNowHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 8;
    }

    @Override
    public int getNowMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    @Override
    public int getMaxDayofMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public int getPosition(@NonNull List<String> sources, String value) {
        return sources.indexOf(value);
    }

    @Override
    public String getDateValue(int date) {
        return date < 10 ? "0" + date : "" + date;
    }

    @Override
    public boolean isShowUnit() {
        return showUnit;
    }

    @Override
    public void setDate(int year, int month, int day, int hour, int minute) {
        if (onDateChangedListener != null) {
            onDateChangedListener.onDate(year, month, day, hour, minute);
        }
    }
}
