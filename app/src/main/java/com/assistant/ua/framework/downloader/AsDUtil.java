package com.assistant.ua.framework.downloader;

import android.app.Application;
import com.assistant.ua.framework.downloader.download.DBuilder;
import com.assistant.ua.framework.downloader.upload.DirectUploadBuilder;
import com.assistant.ua.framework.downloader.upload.FormUploadBuilder;

public class AsDUtil {

    /**
     * 下载
     * 防止弱引用导致内存泄露，采用application初始化数据库
     */
    public static DBuilder init(Application app) {
        return new DBuilder(app);
    }

    /**
     * 表单式文件上传
     *
     */
    public static FormUploadBuilder initFormUpload() {
        return new FormUploadBuilder();
    }

    /**
     * 将文件作为请求体上传
     *
     */
    public static DirectUploadBuilder initUpload() {
        return new DirectUploadBuilder();
    }
}
