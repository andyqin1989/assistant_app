package com.assistant.uaua;

import com.assistant.uaua.framework.observer.ServiceObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by qinbaoyuan on 2018/11/12
 */
public interface IUi {
    void observerService(ServiceObserver observer);
    void onServiceError(String message);
    void onHoldDisposable(Disposable disposable);
}
