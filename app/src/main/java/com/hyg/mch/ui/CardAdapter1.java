package com.hyg.mch.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyg.mch.R;
import com.hyg.widgets.swipecard.SwipeCardAdapter;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/5/9 0009
 * @Desc
 */
public class CardAdapter1 extends SwipeCardAdapter<SwipeCardBean,CardAdapter1.CardViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public CardAdapter1(Context context, List<SwipeCardBean> data) {
        super(data);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder(mLayoutInflater.inflate(R.layout.adapter_card_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter1.CardViewHolder holder, int position) {
        holder.layout.setBackgroundResource(data.get(position).resId);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.adapter_item_layout);
        }
    }
}
