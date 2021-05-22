package com.hyg.hlog.data;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyg.hlog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author hanyonggang
 * @Date 2021/5/16 0016
 * @Desc
 */
public class LogShowHelper implements OnLogChangedListener {
    private Context mContext;
    private final View mRootView;
    private RadioGroup mRadioGroup;
    private EditText mEtSearch;
    private TextView mTvSearch;
    private TextView mTvClear;
    private RecyclerView mRv;
    private List<LogModel> data;
    private LogAdapter mAdapter;

    public LogShowHelper(Context context) {
        mContext = context;
        LogDataManager.getInstance().addListener(this);
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.item_log_dialog_layout, null);
        initView();
        setListener();
        LogDataManager.getInstance().find("");
    }

    private void initView() {
        data = new ArrayList<>();
        mRadioGroup = mRootView.findViewById(R.id.log_info_choice_rg);
        mEtSearch = mRootView.findViewById(R.id.log_info_search_edit);
        mTvSearch = mRootView.findViewById(R.id.log_info_search_tv);
        mTvClear = mRootView.findViewById(R.id.log_info_clear_tv);
        mRv = mRootView.findViewById(R.id.log_info_recyclerview);
        mAdapter = new LogAdapter(mContext, data);
        mRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRv.setAdapter(mAdapter);
    }

    private void setListener() {
        mRadioGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        mTvSearch.setOnClickListener(this::onClick);
        mTvClear.setOnClickListener(this::onClick);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    mTvSearch.setVisibility(View.GONE);
                } else {
                    mTvSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public View getRootView() {
        return mRootView;
    }

    @Override
    public void onChanged(List<LogModel> list) {
        if (list == null) {
            return;
        }
        data.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    private void onClick(View view) {
        int id = view.getId();
        if (id == R.id.log_info_search_tv) {

        } else if (id == R.id.log_info_clear_tv) {
            LogDataManager.getInstance().clear();
        }
    }
}
