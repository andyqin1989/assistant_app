package com.assistant.ua.framework.permission;

import android.Manifest;

/**
 * Created by qinbaoyuan on 2018/11/20
 */
public class Permission {

    public static String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static String[] getBasePermission() {
        return new String[]{READ_PHONE_STATE, WRITE_EXTERNAL_STORAGE};
    }
}
