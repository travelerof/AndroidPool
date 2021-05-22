package com.hyg.overlaylog.log;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyg.overlaylog.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/22 0022
 * @Desc
 */
public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private Context mContext;
    private List<LogModel> data;
    private LayoutInflater mInflater;
    private final SimpleDateFormat mFormat;

    public LogAdapter(Context context, List<LogModel> data) {
        mContext = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
        mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    }

    @NonNull
    @NotNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new LogViewHolder(mInflater.inflate(R.layout.adapter_log_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LogAdapter.LogViewHolder holder, int position) {
        LogModel logModel = data.get(position);
        if (logModel == null) {
            return;
        }
        holder.tvTag.setTextColor(getColor(logModel.priority));
        holder.tvMessage.setTextColor(getColor(logModel.priority));
        holder.tvTag.setText(mFormat.format(logModel.createTime)+"  "+logModel.tag);
        holder.tvMessage.setText(logModel.message);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private int getColor(int priority){
        switch (priority) {
            case Log.DEBUG:
                return mContext.getColor(R.color.color_3700B3);
            case Log.INFO:
                return mContext.getColor(R.color.color_3700B3);
            case Log.WARN:
                return mContext.getColor(R.color.color_3700B3);
            case Log.ERROR:
                return mContext.getColor(R.color.color_error);
            default:
                return mContext.getColor(R.color.color_verbose);
        }
    }
    static class LogViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout rootLayout;
        private TextView tvTag;
        private TextView tvMessage;
        private View line;

        public LogViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.adapter_log_root_layout);
            tvTag = itemView.findViewById(R.id.adapter_log_tag_tv);
            tvMessage = itemView.findViewById(R.id.adapter_log_message_tv);
            line = itemView.findViewById(R.id.adapter_log_line);
        }
    }
}
