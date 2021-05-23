package com.hyg.overlaylog.log;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyg.overlaylog.R;
import com.hyg.overlaylog.filter.LogFilter;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author hanyonggang
 * @Date 2021/5/23 0023
 * @Desc
 */
public class LogDialog extends Dialog {

    private RadioGroup mRgPriority;
    private TextView mTvPriority;
    private EditText mEtKeyword;
    private List<LogModel> logData;
    private LogAdapter mAdapter;
    private ValueAnimator mAnimator;
    private boolean priorityFilter;
    /**
     * 是否正在动画
     */
    private boolean isPriorityAnimator;

    public LogDialog(@NonNull Context context) {
        super(context,R.style.HDialog);
        setCanceledOnTouchOutside(true);
        init(context);
    }


    private void init(Context context) {
        logData = new ArrayList<>();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_log_layout, null);
        initView(view);
        initParams();
        setContentView(view);
        LogDataManager.getInstance().addObserve(logModels -> {
            logData.clear();
            if (logModels != null) {
                logData.addAll(logModels);
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    private void initParams() {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = displayMetrics.widthPixels;
        params.height = displayMetrics.heightPixels/3*2;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }


    private void initView(View view) {
        mTvPriority = view.findViewById(R.id.log_priority_tv);
        mEtKeyword = view.findViewById(R.id.log_keyword_search_et);
        TextView tvClear = view.findViewById(R.id.log_clear_tv);
        RecyclerView rvLog = view.findViewById(R.id.log_recyclerview);
        mRgPriority = view.findViewById(R.id.log_priority_rg);

        rvLog.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new LogAdapter(getContext(), logData);
        rvLog.setAdapter(mAdapter);
        mTvPriority.setOnClickListener(this::onClick);
        tvClear.setOnClickListener(this::onClick);
        mRgPriority.setOnCheckedChangeListener(this::onCheckedChanged);
        mEtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogDataManager.getInstance().setKeyword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void show() {
        mEtKeyword.clearFocus();
        super.show();
    }

    private void onClick(View v) {
        int id = v.getId();
        if (id == R.id.log_priority_tv) {
            if (priorityFilter) {
                priorityFilter = false;
            }else {
                priorityFilter = true;
            }
            startAnimator();
        }else if (id == R.id.log_clear_tv){
            LogDataManager.getInstance().clear();
        }
    }

    private void onCheckedChanged(RadioGroup group, int checkedId) {
        int priority = Log.VERBOSE;
        String priorityText = getContext().getString(R.string.verbose);
        if (checkedId == R.id.log_priority_verbose_rb) {
            priority = Log.VERBOSE;
            priorityText = getContext().getString(R.string.verbose);
        }else if (checkedId == R.id.log_priority_debug_rb) {
            priority = Log.DEBUG;
            priorityText = getContext().getString(R.string.debug);
        }else if (checkedId == R.id.log_priority_info_rb) {
            priority = Log.INFO;
            priorityText = getContext().getString(R.string.info);
        }else if (checkedId == R.id.log_priority_warn_rb) {
            priority = Log.WARN;
            priorityText = getContext().getString(R.string.warn);
        }else if (checkedId == R.id.log_priority_error_rb) {
            priority = Log.ERROR;
            priorityText = getContext().getString(R.string.error);
        }
        mTvPriority.setText(priorityText);
        LogDataManager.getInstance().setPriority(priority);
        priorityFilter = false;
        startAnimator();
    }

    private void startAnimator(){
        if (isPriorityAnimator) {
            return;
        }
        if (mAnimator == null) {
            int height = mRgPriority.getHeight();
            mAnimator = ValueAnimator.ofFloat(0,height);
            mAnimator.setDuration(300);
            mAnimator.addUpdateListener(animation -> {
                float value = (float) animation.getAnimatedValue();
                if (priorityFilter) {
                    mRgPriority.setTranslationY(value - height);
                }else {
                    mRgPriority.setTranslationY(0 - value);
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isPriorityAnimator = false;
                    if (!priorityFilter) {
                        mRgPriority.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    isPriorityAnimator = true;
                    if (priorityFilter) {
                        mRgPriority.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        mAnimator.start();
    }
}
