package com.assistant.ua.business.service;

import android.content.Context;
import android.os.Binder;
import com.assistant.ua.business.ui.blog.NotificationUtil;
import com.assistant.ua.framework.AppExecutor;

import java.io.File;

/**
 * Created by qinbaoyuan on 2018/11/21
 */
public class DownloadBinder extends Binder {
    private Context context;

    public DownloadBinder() {
    }

    public void startDownLoad(Context context) {
        this.context = context;
        NotificationUtil.getInstance().createApkDownLoadNotification(context, "小秘书正在下载中...", "下载版本2.0.0", 1001);
        AppExecutor.getInstance().execute(new FileTask(progressListener));
    }

    private FileTask.TaskProgressListener progressListener = new FileTask.TaskProgressListener() {
        @Override
        public void onProgress(int progress) {
            NotificationUtil.getInstance().updateNotification(1001, progress);
        }

        @Override
        public void onFinished(final File file) {
            NotificationUtil.getInstance().cancelNotification(1001);
            if (context != null) {
                DownloadHelper.installApk(context, file);
            }
        }
    };

}
