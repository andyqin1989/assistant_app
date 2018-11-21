package com.assistant.ua.framework.downloader.upload;

import android.os.Handler;
import android.os.Message;
import com.assistant.ua.framework.downloader.callback.UploadCallback;
import com.assistant.ua.framework.downloader.data.Consts;
import com.assistant.ua.framework.downloader.util.Utils;

public class UploadProgressHandler {
    private UploadCallback uploadCallback;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Consts.START:
                    uploadCallback.onStart();
                    break;
                case Consts.PROGRESS:
                    uploadCallback.onProgress(msg.arg1, msg.arg2, Utils.getPercentage(msg.arg1, msg.arg2));
                    break;
                case Consts.FINISH:
                    uploadCallback.onFinish(msg.obj.toString());
                    break;
                case Consts.ERROR:
                    uploadCallback.onError(msg.obj.toString());
                    break;
            }
        }
    };

    public UploadProgressHandler(UploadCallback uploadCallback) {
        this.uploadCallback = uploadCallback;
    }

    public Handler getHandler() {
        return mHandler;
    }
}
