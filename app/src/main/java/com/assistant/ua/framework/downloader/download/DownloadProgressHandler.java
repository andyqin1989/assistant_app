package com.assistant.ua.framework.downloader.download;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.assistant.ua.framework.downloader.callback.DownloadCallback;
import com.assistant.ua.framework.downloader.data.DownloadData;
import com.assistant.ua.framework.downloader.db.Db;
import com.assistant.ua.framework.downloader.util.Utils;

import java.io.File;

import static com.assistant.ua.framework.downloader.data.Consts.*;


public class DownloadProgressHandler {
    private String url;
    private String path;
    private String name;
    private int childTaskCount;

    private DownloadCallback downloadCallback;
    private DownloadData downloadData;

    private FileTask fileTask;

    private int mCurrentState = NONE;

    //是否支持断点续传
    private boolean isSupportRange;

    //重新开始下载需要先进行取消操作
    private boolean isNeedRestart;

    //记录已经下载的大小
    private int currentLength = 0;
    //记录文件总大小
    private int totalLength = 0;
    //记录已经暂停或取消的线程数
    private int tempChildTaskCount = 0;

    private long lastProgressTime;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int mLastSate = mCurrentState;
            mCurrentState = msg.what;
            downloadData.setStatus(mCurrentState);

            switch (mCurrentState) {
                case START:
                    Bundle bundle = msg.getData();
                    totalLength = bundle.getInt("totalLength");
                    currentLength = bundle.getInt("currentLength");
                    String lastModify = bundle.getString("lastModify");
                    isSupportRange = bundle.getBoolean("isSupportRange");

                    if (!isSupportRange) {
                        childTaskCount = 1;
                    } else if (currentLength == 0) {
                        Db.getInstance().insertData(new DownloadData(url, path, childTaskCount, name, currentLength, totalLength, lastModify, System.currentTimeMillis()));
                    }
                    if (downloadCallback != null) {
                        downloadCallback.onStart(getDownloadDataTaskId(), currentLength, totalLength, Utils.getPercentage(currentLength, totalLength));
                    }
                    break;
                case PROGRESS:
                    synchronized (this) {
                        currentLength += msg.arg1;

                        downloadData.setPercentage(Utils.getPercentage(currentLength, totalLength));

                        if (downloadCallback != null && (System.currentTimeMillis() - lastProgressTime >= 20 || currentLength == totalLength)) {
                            downloadCallback.onProgress(getDownloadDataTaskId(), currentLength, totalLength, Utils.getPercentage(currentLength, totalLength));
                            lastProgressTime = System.currentTimeMillis();
                        }

                        if (currentLength == totalLength) {
                            mHandler.sendEmptyMessage(FINISH);
                        }
                    }
                    break;
                case CANCEL:
                    synchronized (this) {
                        tempChildTaskCount++;
                        if (tempChildTaskCount == childTaskCount || mLastSate == PAUSE || mLastSate == ERROR) {
                            tempChildTaskCount = 0;
                            if (downloadCallback != null) {
                                downloadCallback.onProgress(getDownloadDataTaskId(), 0, totalLength, 0);
                            }
                            currentLength = 0;
                            if (isSupportRange) {
                                Db.getInstance().deleteData(url);
                                Utils.deleteFile(new File(path, name + ".temp"));
                            }
                            Utils.deleteFile(new File(path, name));
                            if (downloadCallback != null) {
                                downloadCallback.onCancel(getDownloadDataTaskId());
                            }

                            if (isNeedRestart) {
                                isNeedRestart = false;
                                DownloadManger.getInstance().innerRestart(url);
                            }
                        }
                    }
                    break;
                case PAUSE:
                    synchronized (this) {
                        if (isSupportRange) {
                            Db.getInstance().updateProgress(currentLength, Utils.getPercentage(currentLength, totalLength), PAUSE, url);
                        }
                        tempChildTaskCount++;
                        if (tempChildTaskCount == childTaskCount) {
                            if (downloadCallback != null) {
                                downloadCallback.onPause(getDownloadDataTaskId());
                            }
                            tempChildTaskCount = 0;
                        }
                    }
                    break;
                case FINISH:
                    if (isSupportRange) {
                        Utils.deleteFile(new File(path, name + ".temp"));
                        Db.getInstance().deleteData(url);
                    }
                    if (downloadCallback != null) {
                        downloadCallback.onFinish(getDownloadDataTaskId(), new File(path, name));
                    }
                    DownloadManger.getInstance().destroy(url);
                    break;
                case DESTROY:
                    synchronized (this) {
                        if (isSupportRange) {
                            Db.getInstance().updateProgress(currentLength, Utils.getPercentage(currentLength, totalLength), DESTROY, url);
                        }
                    }
                    break;
                case ERROR:
                    if (isSupportRange) {
                        Db.getInstance().updateProgress(currentLength, Utils.getPercentage(currentLength, totalLength), ERROR, url);
                    }
                    if (downloadCallback != null) {
                        downloadCallback.onError(getDownloadDataTaskId(), (String) msg.obj);
                    }
                    break;
            }
            return false;
        }
    });

    public DownloadProgressHandler(DownloadData downloadData, DownloadCallback downloadCallback) {
        this.downloadCallback = downloadCallback;

        this.url = downloadData.getUrl();
        this.path = downloadData.getPath();
        this.name = downloadData.getName();
        this.childTaskCount = downloadData.getChildTaskCount();

        DownloadData dbData = Db.getInstance().getData(url);
        this.downloadData = dbData == null ? downloadData : dbData;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public int getCurrentState() {
        return mCurrentState;
    }

    public DownloadData getDownloadData() {
        return downloadData;
    }

    public void setFileTask(FileTask fileTask) {
        this.fileTask = fileTask;
    }

    /**
     * 下载中退出时保存数据、释放资源
     */
    public void destroy() {
        if (mCurrentState == CANCEL || mCurrentState == PAUSE) {
            return;
        }
        fileTask.destroy();
    }

    /**
     * 暂停（正在下载才可以暂停）
     * 如果文件不支持断点续传则不能进行暂停操作
     */
    public void pause() {
        if (mCurrentState == PROGRESS) {
            fileTask.pause();
        }
    }

    /**
     * 取消（已经被取消、下载结束则不可取消）
     */
    public void cancel(boolean isNeedRestart) {
        this.isNeedRestart = isNeedRestart;
        if (mCurrentState == PROGRESS) {
            fileTask.cancel();
        } else if (mCurrentState == PAUSE || mCurrentState == ERROR) {
            mHandler.sendEmptyMessage(CANCEL);
        }
    }

    public int getDownloadDataTaskId() {
        if (null != downloadData)
            return downloadData.getTaskId();
        return -1;
    }
}
