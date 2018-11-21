package com.assistant.ua.datasource.net;

import com.assistant.ua.datasource.entity.BlogEntity;
import com.assistant.ua.datasource.net.request.AddBlogRequest;
import com.assistant.ua.datasource.net.request.GetAllBlogRequest;
import com.assistant.ua.datasource.net.request.LoginRequest;
import com.assistant.ua.datasource.net.request.RegisterRequest;
import com.assistant.ua.datasource.net.response.*;
import com.assistant.ua.framework.network.ASNetManager;
import com.assistant.ua.datasource.net.api.INetEngine;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

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

    /**
     * 添加任务
     */
    public Observable<AddTaskResponse> addBlog(AddBlogRequest request) {
        return netEngine.addBlog(request);
    }

    /**
     * 返回所有对任务
     */
    public Observable<List<BlogEntity>> getAllBlog(GetAllBlogRequest request) {
        return netEngine.getAllBlog(request)
                .map(new HttpResultFun<List<BlogEntity>>())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());
    }

}
