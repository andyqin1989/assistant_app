package com.assistant.ua.framework.downloader.callback;

import java.io.File;

public interface DownloadCallback extends FileCallback {
    void onStart(int taskId, long currentSize, long totalSize, float progress);

    void onProgress(int taskId, long currentSize, long totalSize, float progress);

    void onPause(int taskId);

    void onCancel(int taskId);

    void onFinish(int taskId, File file);

    void onWait(int taskId);

    void onError(int taskId, String error);
}
