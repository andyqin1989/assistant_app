package com.assistant.ua.datasource.net.response;

import com.assistant.ua.framework.network.ApiException;
import com.assistant.ua.framework.network.DataNullException;
import io.reactivex.functions.Function;

import java.util.List;

/**
 * Created by qinbaoyuan on 2018/11/19
 */
public class HttpResultFun<T> implements Function<HttpResult<T>, T> {
    @Override
    public T apply(HttpResult<T> httpResult) throws Exception {
        if (!httpResult.succeed()) {
            throw new ApiException(httpResult.getMessage());
        }

        if (httpResult.getData() == null || (httpResult.getData() instanceof List && ((List) httpResult.getData()).size() == 0)) {
            throw new DataNullException("数据为空");
        }
        return httpResult.getData();
    }
}
