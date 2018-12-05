package com.assistant.ua.business.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by qinbaoyuan on 2018/11/21
 */
public class DownloadService extends Service {
    private DownloadBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new DownloadBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
