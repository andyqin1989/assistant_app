package com.assistant.ua.framework.permission;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import com.assistant.ua.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by qinbaoyuan on 2018/11/20
 */
public class PermissionSettings {
    private static final String MANU_XIAOMI = "Xiaomi";


    public static void goSetting(Activity activity) {
        switch (Build.MANUFACTURER) {
            case MANU_XIAOMI:
                MiUi(activity);
                break;
        }
    }

    public static void MiUi(Activity activity) {
        try {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.setComponent(componentName);
            intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
            activity.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
            SystemConfig(activity);
        }
    }

    /**
     * 系统设置界面
     */
    private static void SystemConfig(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
        activity.startActivity(intent);
    }
}
