package com.assistant.ua.framework.permission;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import androidx.core.app.AppOpsManagerCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by qinbaoyuan on 2018/11/20
 */
public class PermissionHelper {

    public static String getPermissionMsg(String permission) {
        String result = "在设置-应用-小秘书-权限打开相应权限，以正常使用小秘书";
        /*if (Permission.READ_PHONE_STATE.equals(permission) || .equals(permission)) {
            result = "在设置-应用-悟空找房-权限中开启拨打电话权限，以正常使用联系经纪人或者咨询客服等功能";
        } else if (Permission.ACCESS_FINE_LOCATION.equals(permission) || Permission.ACCESS_COARSE_LOCATION.equals(permission)) {
            result = "在设置-应用-悟空找房-权限中开启定位权限，以正常使用地图找房等功能";
        } else if (Permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
            result = "在设置-应用-悟空找房-权限中开启存储空间权限，以正常使用悟空找房";
        } else if (Permission.RECORD_AUDIO.equals(permission)) {
            result = "在设置-应用-悟空找房-权限中开启麦克风权限，以正常使用语音聊天等功能";
        } else if (Permission.CAMERA.equals(permission)) {
            result = "在设置-应用-悟空找房-权限中开启相机权限，以正常使用拍照、扫描二维码等功能";
        } else if (Permission.READ_CONTACTS.equals(permission)) {
            result = "在设置-应用-悟空找房-权限中开启通讯录权限，以正常使用邀请他人入群功能";
        } else if (Permission.READ_SMS.equals(permission)) {
            result = "在设置-应用-悟空找房-权限中开启读取短信权限，以正常使用获取验证码功能";
        }*/
        return result;
    }

    /**
     * 检查多个权限
     */
    public static boolean hasPermission(Context context, String[] permission) {
        if (permission.length == 0) return true;
        boolean result = true;
        for (String permissionItem : permission) {
            if (!hasPermission(context, permissionItem)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 检查是否已经对该权限授权
     */
    public static boolean hasPermission(Context context, String permission) {
        boolean result = false;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
                    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
                } else {
                    AppOpsManager opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                    String ops = AppOpsManagerCompat.permissionToOp(permission);
                    if (ops != null && opsManager != null) {
                        int checkOp = opsManager.noteProxyOp(ops, context.getPackageName());
                        if (checkOp == AppOpsManager.MODE_ALLOWED) {
                            result = true;
                        }
                    }
                    return result;
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }

        int auth = ContextCompat.checkSelfPermission(context, permission);
        AppOpsManager opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        String ops = AppOpsManagerCompat.permissionToOp(permission);
        if (ops != null && opsManager != null) {
            int checkOp = opsManager.checkOp(ops, Process.myUid(), context.getPackageName());
            if (auth == PackageManager.PERMISSION_GRANTED && checkOp == AppOpsManager.MODE_ALLOWED) {
                result = true;
            }
        }
        return result;
    }
}
