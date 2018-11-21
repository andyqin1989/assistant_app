package com.assistant.ua

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.util.concurrent.TimeoutException

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
        if (e is ConnectException || e is TimeoutException) {

        }
        e.printStackTrace()
        Log.e("network", e.message.toString())
    }
}