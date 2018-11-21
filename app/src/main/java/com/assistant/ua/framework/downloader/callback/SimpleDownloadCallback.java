package com.assistant.ua.framework.downloader.callback;

import java.io.File;

public abstract class SimpleDownloadCallback implements DownloadCallback {

    @Override
    public void onStart(int taskId, long currentSize, long totalSize, float progress) {

    }

    @Override
    public void onProgress(int taskId, long currentSize, long totalSize, float progress) {

    }

    @Override
    public void onPause(int taskId) {

    }

    @Override
    public void onCancel(int taskId) {

    }

    @Override
    public void onFinish(int taskId, File file) {

    }

    @Override
    public void onWait(int taskId) {

    }

    @Override
    public void onError(int taskId, String error) {

    }
}
