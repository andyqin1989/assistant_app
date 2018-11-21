package com.assistant.ua.framework.util;

import android.util.TypedValue;
import com.assistant.ua.ASApp;

/**
 * Created by qinbaoyuan on 2018/11/19
 */
public class AsUiOps {
    public static int dp2px(int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, ASApp.instance.getResources().getDisplayMetrics()) + 0.5f);
    }
}
