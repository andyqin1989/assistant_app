package com.assistant.ua.framework.network.covert;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.assistant.ua.framework.network.NetWorkLog;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;

/**
 * Created by qinbaoyuan on 2018/11/12
 */
public class ResponseBodyConvert<T> implements Converter<ResponseBody, T> {
    private Gson gson;
    private TypeAdapter<T> adapter;


    public ResponseBodyConvert(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            NetWorkLog.Companion.showResponse("Response", gson.toJson(result));
            return result;
        } finally {
            value.close();
        }
    }
}
