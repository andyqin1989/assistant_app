package com.assistant.ua.business.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

/**
 * Created by qinbaoyuan on 2018/11/22
 */
public class DownloadHelper {
    public static void installApk(Context context, File file) {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, "com.assistant.ua.FileProvider",file);
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            installIntent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        if (isApkInstallAvailable(installIntent, context)) {
            context.startActivity(installIntent);
        }
    }

    public static boolean isApkInstallAvailable(Intent intent, Context context) {
        if (intent == null) return false;
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
