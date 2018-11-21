package com.assistant.ua.framework.downloader.upload;

import android.text.TextUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.File;

public class DirectUploadRequest extends BaseUploadRequest {
    private File file;
    private byte[] bytes;
    private String type;

    public DirectUploadRequest(String url, File file, byte[] bytes, String type) {
        this.url = url;
        this.file = file;
        this.bytes = bytes;
        this.type = type;
    }

    @Override
    protected RequestBody initRequestBody() {
        type = TextUtils.isEmpty(type) ? "application/octet-stream" : type;
        if (file != null) {
            return RequestBody.create(MediaType.parse(type), file);
        }
        return RequestBody.create(MediaType.parse(type), bytes);
    }
}
