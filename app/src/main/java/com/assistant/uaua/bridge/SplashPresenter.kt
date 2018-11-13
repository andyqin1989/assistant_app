package com.assistant.uaua.bridge

import android.util.Log
import com.assistant.uaua.ASApp
import com.assistant.uaua.LFUserInfo
import com.assistant.uaua.datasource.entity.UserEntity
import com.assistant.uaua.framework.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/** Created by qinbaoyuan on 2018/11/11
 */
open class SplashPresenter : BasePresenter {
    override fun onCreate() {
        Log.e("andy", "Splash Presenter onCreate")

        var observable = Observable.create(object : ObservableOnSubscribe<UserEntity> {
            override fun subscribe(emitter: ObservableEmitter<UserEntity>) {
                var list = ASApp.instance.appDatabase.userEntityDao.getLoginUsers()
                if (list.isNotEmpty()) {
                    emitter.onNext(list[0])
                }
            }
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        var d = observable.subscribe(object : Consumer<UserEntity> {
            override fun accept(user: UserEntity) {
                Log.e("andyqin", "读取到数据库 name = " + user.userName)
                LFUserInfo.instance.userName = user.userName
                LFUserInfo.instance.phoneNum = user.phoneNum
                LFUserInfo.instance.portraitUrl = user.portraitUrl
            }
        })
    }

    override fun onStart() {
        Log.e("andy", "Splash Presenter onStart")
    }

    override fun onResume() {
        Log.e("andy", "Splash Presenter onResume")
    }

    override fun onPause() {
        Log.e("andy", "Splash Presenter onPause")
    }

    override fun onStop() {
        Log.e("andy", "Splash Presenter onStop")
    }

    override fun onDestroy() {
        Log.e("andy", "Splash Presenter onDestroy")
    }
}