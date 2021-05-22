package com.hyg.overlaylog.filter;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @Author 韩永刚
 * @Date 2021/05/23
 * @Desc
 */
interface IFilter<T> {

    @NonNull
    List<T> filter(@NonNull List<T> list);
}
