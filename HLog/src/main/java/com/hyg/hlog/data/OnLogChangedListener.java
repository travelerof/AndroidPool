package com.hyg.hlog.data;

import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/05/14
 * @Desc
 */
public interface OnLogChangedListener {
    void onChanged(List<LogModel> list);
}
