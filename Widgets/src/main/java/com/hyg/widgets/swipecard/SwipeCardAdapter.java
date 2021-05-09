package com.hyg.widgets.swipecard;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2021/5/9 0009
 * @Desc
 */
public abstract class SwipeCardAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> data;

    public SwipeCardAdapter(List<T> data){
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
