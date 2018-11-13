package com.assistant.uaua.datasource.net;

import com.assistant.uaua.datasource.net.request.LoginRequest;
import com.assistant.uaua.datasource.net.request.RegisterRequest;
import com.assistant.uaua.datasource.net.response.LoginResponse;
import com.assistant.uaua.datasource.net.response.RegisterResponse;
import com.assistant.uaua.framework.network.ASNetManager;
import com.assistant.uaua.datasource.net.api.INetEngine;
import io.reactivex.Observable;

/**
 * Created by qinbaoyuan on 2018/11/12
 */
public class UserRepository {

    public static UserRepository getInstance() {
        return SingletonHolder.holder;
    }

    private static class SingletonHolder {
        static UserRepository holder = new UserRepository();
    }

    private UserRepository() {
        netEngine = ASNetManager.Companion.getInstance().getRetrofit().create(INetEngine.class);
    }

    private INetEngine netEngine;


    /**
     * 返回网络请求接口
     */
    public INetEngine getNetEngine() {
        return netEngine;
    }


    /**
     * 用户注册
     */
    public Observable<RegisterResponse> register(RegisterRequest request) {
        return netEngine.register(request);
    }

    /**
     * 用户登录
     */
    public Observable<LoginResponse> login(LoginRequest request) {
        return netEngine.login(request);
    }


    public Observable getUserList() {
        return netEngine.getUserList();
    }

}
