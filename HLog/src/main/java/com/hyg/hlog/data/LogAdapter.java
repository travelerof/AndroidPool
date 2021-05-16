package com.hyg.hlog.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyg.hlog.R;

import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/16 0016
 * @Desc
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private Context mContext;
    private List<LogModel> data;
    private LayoutInflater mInflater;
    public LogAdapter(Context context, List<LogModel> data) {
        mContext = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        return new LogViewHolder(mInflater.inflate(R.layout.adapter_log_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull LogAdapter.LogViewHolder holder, int position) {
        LogModel logModel = data.get(position);
        if (logModel == null) {
            return;
        }
        holder.tvTag.setText(logModel.getCreateTime()+"/"+logModel.getTag()+":");
        holder.tvMessage.setText(logModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    protected static class LogViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTag;
        private TextView tvMessage;
        public LogViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.adapter_log_tag_tv);
            tvMessage = itemView.findViewById(R.id.adapter_log_message_tv);
        }
    }
}
