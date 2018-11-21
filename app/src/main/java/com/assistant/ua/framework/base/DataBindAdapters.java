package com.assistant.ua.framework.base;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by qinbaoyuan on 2018/11/19
 */
public class DataBindAdapters {
    @BindingAdapter("refreshing")
    public static void setRefresh(SwipeRefreshLayout layout, boolean isLoading) {
        layout.setRefreshing(isLoading);
    }
}