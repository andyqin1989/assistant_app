package com.assistant.ua.business.service;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by qinbaoyuan on 2018/11/21
 */
public class DownloadNetManager {
    private OkHttpClient.Builder builder;

    public static DownloadNetManager getInstance() {
        return Holder.holder;
    }

    private static class Holder {
        private static DownloadNetManager holder = new DownloadNetManager();
    }

    private DownloadNetManager() {
        builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);
    }


    public Response initRequest(String url, Callback callback) {
        Response response = null;
        Request request = new Request.Builder().url(url).build();
        try {
            response = builder.build().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
