package com.assistant.ua

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/** Created by qinbaoyuan on 2018/11/11
 */
open class BaseObserver<T> : Observer<T> {
    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
    }
}