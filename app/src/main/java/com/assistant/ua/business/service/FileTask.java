package com.assistant.ua.business.service;

import com.assistant.ua.ASApp;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by qinbaoyuan on 2018/11/21
 */
public class FileTask implements Runnable {
    TaskProgressListener progressListener;

    public FileTask(TaskProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public void run() {
        Response response = DownloadNetManager.getInstance().initRequest("http://123.57.242.138:1109/app/app_1.0.apk", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

        if (response != null) {
            String length = response.headers().get("Content-Length");
            if (response.body() != null) {
                try {
                    String path = ASApp.instance.getExternalCacheDir().getPath() + File.separator + "new_version.apk";
                    File saveFile = new File(path);
                    if (saveFile.exists()) {
                        saveFile.delete();
                    }
                    InputStream inputStream = response.body().byteStream();

                    FileOutputStream fileOutputStream = new FileOutputStream(saveFile);


                    int currentLength = 0;
                    int totalLength = Integer.parseInt(length);

                    byte[] buffer = new byte[1024 * 10];
                    int everyLength;
                    while ((everyLength = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, everyLength);
                        currentLength += everyLength;
                        progressListener.onProgress((int) (((float) currentLength / totalLength) * 100));
                    }
                    response.body().close();
                    fileOutputStream.flush();
                    fileOutputStream.close();

                    if (currentLength == totalLength) {
                        progressListener.onFinished(saveFile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public interface TaskProgressListener {
        void onProgress(int progress);

        void onFinished(File file);
    }
}
