package com.assistant.ua.framework.downloader.upload;

import com.assistant.ua.framework.downloader.data.UploadFile;
import com.assistant.ua.framework.downloader.util.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.util.List;
import java.util.Map;

public class FileUploadRequest extends FormUploadRequest {

    private List<UploadFile> files;

    public FileUploadRequest(String url, List<UploadFile> files, Map<String, String> params, Map<String, String> headers) {
        this.url = url;
        this.files = files;
        this.params = params;
        this.headers = headers;
    }

    @Override
    protected void buildRequestBody(MultipartBody.Builder builder) {
        for (UploadFile file : files) {
            RequestBody fileBody = RequestBody.create(MediaType.parse(Utils.getMimeType(file.getName())), file.getFile());
            builder.addFormDataPart(file.getName(), file.getFilename(), fileBody);
        }
    }
}
