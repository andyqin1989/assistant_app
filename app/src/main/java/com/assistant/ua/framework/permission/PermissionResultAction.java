package com.assistant.ua.framework.permission;

import java.util.ArrayList;

/**
 * Created by qinbaoyuan on 2018/11/20
 */
public interface PermissionResultAction {
    void onGranted();

    void onDenied(ArrayList<String> permissions);
}
