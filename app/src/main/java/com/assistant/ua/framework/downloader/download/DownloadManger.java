package com.assistant.ua.framework.downloader.download;


import com.assistant.ua.framework.downloader.callback.DownloadCallback;
import com.assistant.ua.framework.downloader.data.Consts;
import com.assistant.ua.framework.downloader.data.DownloadData;
import com.assistant.ua.framework.downloader.db.Db;
import com.assistant.ua.framework.downloader.net.OkHttpManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadManger {
    private Map<String, DownloadProgressHandler> progressHandlerMap = new HashMap<>();//保存任务的进度处理对象
    private Map<String, DownloadData> downloadDataMap = new HashMap<>();//保存任务数据
    private Map<String, DownloadCallback> callbackMap = new HashMap<>();//保存任务回调
    private Map<String, FileTask> fileTaskMap = new HashMap<>();//保存下载线程

    private DownloadData downloadData;

    private volatile static DownloadManger downloadManager;

    public static DownloadManger getInstance() {
        if (downloadManager == null) {
            synchronized (DownloadManger.class) {
                if (downloadManager == null) {
                    downloadManager = new DownloadManger();
                }
            }
        }
        return downloadManager;
    }

    private DownloadManger() {
    }

    /**
     * 配置线程池
     */
    public void setTaskPoolSize(int corePoolSize, int maxPoolSize) {
        if (maxPoolSize > corePoolSize && maxPoolSize * corePoolSize != 0) {
            ThreadPool.getInstance().setCorePoolSize(corePoolSize);
            ThreadPool.getInstance().setMaxPoolSize(maxPoolSize);
        }
    }

    public void init(String url, String path, String name, int childTaskCount) {
        downloadData = new DownloadData();
        downloadData.setUrl(url);
        downloadData.setPath(path);
        downloadData.setName(name);
        downloadData.setChildTaskCount(childTaskCount);
    }

    /**
     * 链式开启下载
     */
    public DownloadManger start(DownloadCallback downloadCallback) {
        execute(downloadData, downloadCallback);
        return downloadManager;
    }

    /**
     * data + callback 形式直接开始下载
     */
    public DownloadManger start(DownloadData downloadData, DownloadCallback downloadCallback) {
        execute(downloadData, downloadCallback);
        return downloadManager;
    }

    /**
     * 根据url开始下载（需先注册监听）
     */
    public DownloadManger start(String url) {
        execute(downloadDataMap.get(url), callbackMap.get(url));
        return downloadManager;
    }

    /**
     * 注册监听
     */
    public void setOnDownloadCallback(DownloadData downloadData, DownloadCallback downloadCallback) {
        downloadDataMap.put(downloadData.getUrl(), downloadData);
        callbackMap.put(downloadData.getUrl(), downloadCallback);
    }

    /**
     * 配置https证书
     */
    public void setCertificates(InputStream... certificates) {
        OkHttpManager.getInstance().setCertificates(certificates);
    }

    /**
     * 获得数据库中对应url下载数据
     */
    public DownloadData getDbData(String url) {
        return Db.getInstance().getData(url);
    }

    /**
     * 获得数据库中所有下载数据
     */
    public List<DownloadData> getAllDbData() {
        return Db.getInstance().getAllData();
    }

    /**
     * 根据url获得下载队列中的data
     */
    public DownloadData getCurrentData(String url) {
        if (progressHandlerMap.containsKey(url)) {
            return progressHandlerMap.get(url).getDownloadData();
        }
        return null;
    }

    /**
     * 执行下载任务
     */
    private void execute(DownloadData downloadData, DownloadCallback downloadCallback) {
        if (null == downloadData || null == downloadCallback) return;

        //防止同一个任务多次下载
        if (progressHandlerMap.get(downloadData.getUrl()) != null) {
            downloadCallback.onError(downloadData.getTaskId(),"下载已经开始...");
            return;
        }

        //默认每个任务不通过多个异步任务下载
        if (downloadData.getChildTaskCount() == 0) {
            downloadData.setChildTaskCount(1);
        }

        DownloadProgressHandler downloadProgressHandler = new DownloadProgressHandler(downloadData, downloadCallback);
        FileTask fileTask = new FileTask(downloadData, downloadProgressHandler.getHandler());
        downloadProgressHandler.setFileTask(fileTask);

        downloadDataMap.put(downloadData.getUrl(), downloadData);
        callbackMap.put(downloadData.getUrl(), downloadCallback);
        fileTaskMap.put(downloadData.getUrl(), fileTask);
        progressHandlerMap.put(downloadData.getUrl(), downloadProgressHandler);

        ThreadPool.getInstance().getThreadPoolExecutor().execute(fileTask);

        //如果正在下载的任务数量等于线程池的核心线程数，则新添加的任务处于等待状态
        if (ThreadPool.getInstance().getThreadPoolExecutor().getActiveCount() == ThreadPool.getInstance().getCorePoolSize()) {
            downloadCallback.onWait(downloadData.getTaskId());
        }
    }

    /**
     * 暂停
     */
    public void pause(String url) {
        if (progressHandlerMap.containsKey(url))
            progressHandlerMap.get(url).pause();
    }

    /**
     * 继续
     */
    public void resume(String url) {
        if (progressHandlerMap.containsKey(url) &&
                (progressHandlerMap.get(url).getCurrentState() == Consts.PAUSE ||
                        progressHandlerMap.get(url).getCurrentState() == Consts.ERROR)) {
            progressHandlerMap.remove(url);
            execute(downloadDataMap.get(url), callbackMap.get(url));
        }
    }

    /**
     * 重新开始
     */
    public void restart(String url) {
        //文件已下载完成的情况
        if (progressHandlerMap.containsKey(url) && progressHandlerMap.get(url).getCurrentState() == Consts.FINISH) {
            progressHandlerMap.remove(url);
            fileTaskMap.remove(url);
            innerRestart(url);
            return;
        }

        //任务已经取消，则直接重新下载
        if (!progressHandlerMap.containsKey(url)) {
            innerRestart(url);
        } else {
            innerCancel(url, true);
        }
    }

    /**
     * 实际的重新下载操作
     *
     * @param url
     */
    protected void innerRestart(String url) {
        execute(downloadDataMap.get(url), callbackMap.get(url));
    }

    /**
     * 取消
     *
     * @param url
     */
    public void cancel(String url) {
        innerCancel(url, false);
    }

    public void innerCancel(String url, boolean isNeedRestart) {
        if (progressHandlerMap.get(url) != null) {
            if (progressHandlerMap.get(url).getCurrentState() == Consts.NONE) {
                //取消缓存队列中等待下载的任务
                ThreadPool.getInstance().getThreadPoolExecutor().remove(fileTaskMap.get(url));
                callbackMap.get(url).onCancel(progressHandlerMap.get(url).getDownloadDataTaskId());
            } else {
                //取消已经开始下载的任务
                progressHandlerMap.get(url).cancel(isNeedRestart);
            }
            progressHandlerMap.remove(url);
            fileTaskMap.remove(url);
        }
    }

    /**
     * 退出时释放资源
     */
    public void destroy(String url) {
        if (progressHandlerMap.containsKey(url)) {
            progressHandlerMap.get(url).destroy();
            progressHandlerMap.remove(url);
            callbackMap.remove(url);
            downloadDataMap.remove(url);
            fileTaskMap.remove(url);
        }
    }

    public void destroy(String... urls) {
        if (urls != null) {
            for (String url : urls) {
                destroy(url);
            }
        }
    }
}
