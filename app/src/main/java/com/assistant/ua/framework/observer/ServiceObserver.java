package com.assistant.ua.framework.observer;

import com.assistant.ua.IUi;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by qinbaoyuan on 2018/11/12
 */
public class ServiceObserver<T> implements Observer<T> {
    private IUi iUi;

    public void setiUi(IUi iUi) {
        this.iUi = iUi;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (iUi != null) {
            iUi.onHoldDisposable(d);
        }
        showProgress();
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        String errorMsg = "error:" + e.getMessage();
        if (e instanceof SocketTimeoutException || e instanceof ConnectException) {
            errorMsg = "网络异常，请检查你的网络";
        }
        if (iUi != null) {
            iUi.onServiceError(errorMsg);
        }
        dismissProgress();

    }

    @Override
    public void onComplete() {
        dismissProgress();
    }

    private void showProgress() {

    }

    private void dismissProgress() {

    }
}
