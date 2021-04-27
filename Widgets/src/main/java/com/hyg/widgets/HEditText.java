package com.hyg.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.hyg.utils.DensityUtils;

/**
 * @Author 韩永刚
 * @Date 2021/02/22
 * @Desc
 */
public class HEditText extends AppCompatEditText {

    public static final int NONE = 0;
    public static final int SEARCH = 1;
    public static final int DELETE = 2;
    public static final int BOTH = 3;
    private int searchIconId;
    private int deleteIconId;
    private int hType = SEARCH;
    private int MARGIN = 10;
    private BitmapModel searchModel;

    public HEditText(Context context) {
        this(context, null);
    }

    public HEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        MARGIN = DensityUtils.dip2Px(context,10);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.HEditText);
            searchIconId = array.getResourceId(R.styleable.HEditText_searchSrc,0);
            deleteIconId = array.getResourceId(R.styleable.HEditText_deleteSrc,0);
            hType = array.getInt(R.styleable.HEditText_hType,0);
            array.recycle();
        }else{

        }
        initBitmap();
    }

    private void initBitmap() {
        switch (hType) {
            case SEARCH:
                Bitmap searchBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_default_search);
                if (searchBitmap != null) {
                    searchModel = new BitmapModel();
                    searchModel.bitmap = searchBitmap;
                    setPadding(MARGIN+getPaddingLeft()+searchBitmap.getWidth(),getPaddingTop(),getPaddingRight(),getPaddingBottom());
                    searchModel.startX = MARGIN;
                    searchModel.btWidth = searchBitmap.getWidth();
                    searchModel.btHeight = searchBitmap.getHeight();
                }
                break;
            case DELETE:
                break;
            case BOTH:
                break;
        }
    }

    private Bitmap getSearchBitmap(int resId){
        return BitmapFactory.decodeResource(getResources(),resId);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (searchModel != null) {
            canvas.drawBitmap(searchModel.bitmap,searchModel.startX,height/2 - searchModel.btHeight/2,null);
        }

    }

    private static class BitmapModel{

        public int startX;
        public int btWidth;
        public int btHeight;
        public Bitmap bitmap;
    }

}
