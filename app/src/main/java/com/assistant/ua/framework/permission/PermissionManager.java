package com.assistant.ua.framework.permission;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import com.assistant.ua.framework.base.ui.BaseActivity;

/**
 * Created by qinbaoyuan on 2018/11/20
 */
public class PermissionManager {
    private static class Holder {
        private static PermissionManager holder = new PermissionManager();
    }

    public static PermissionManager getInstance() {
        return Holder.holder;
    }

    public boolean hasPermission(Context context, String permission) {
        return PermissionHelper.hasPermission(context, permission);
    }

    public boolean hasPermission(Context context, String[] permission) {
        return PermissionHelper.hasPermission(context, permission);
    }

    public void applyPermission(Activity activity, String[] permission, PermissionResultAction action) {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).setResultAction(action);
            ((BaseActivity) activity).askPermission(permission);
        }
    }

    public void showPermissionDialog(Activity activity, String permission, @Nullable PermissionDialogListener listener) {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).showPermissionDialog(permission, listener);
        }
    }
}