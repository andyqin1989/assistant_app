package com.assistant.ua.framework.downloader.download;

import android.app.Application;
import com.assistant.ua.framework.downloader.db.Db;

public class DBuilder {
    private String url;//下载链接
    private String path;//保存路径
    private String name;//文件名
    private int childTaskCount;//单个任务采用几个线程下载

    public DBuilder(Application app) {
        Db.getInstance().initDB(app);
    }

    public DBuilder url(String url) {
        this.url = url;
        return this;
    }

    public DBuilder path(String path) {
        this.path = path;
        return this;
    }

    public DBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DBuilder childTaskCount(int thread) {
        this.childTaskCount = thread;
        return this;
    }

    public DownloadManger build() {
        DownloadManger downloadManger = DownloadManger.getInstance();
        downloadManger.init(url, path, name, childTaskCount);
        return downloadManger;
    }
}
