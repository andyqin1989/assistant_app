package com.assistant.ua.framework.recycler;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by qinbaoyuan on 2018/9/10
 */
public class LCSwipeRefreshLayout extends SwipeRefreshLayout {
    public LCSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public LCSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }
}
