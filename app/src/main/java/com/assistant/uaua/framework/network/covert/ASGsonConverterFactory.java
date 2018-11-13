package com.assistant.uaua.framework.network.covert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by qinbaoyuan on 2018/11/12
 */
public class ASGsonConverterFactory extends Converter.Factory {

    public static ASGsonConverterFactory create() {
        return create(new Gson());
    }

    public static ASGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson ==null");
        return new ASGsonConverterFactory(gson);
    }

    private final Gson gson;

    private ASGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter adapter = gson.getAdapter(TypeToken.get(type));
        return new ResponseBodyConvert<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new RequestBodyConvert<>(gson, adapter);
    }
}
