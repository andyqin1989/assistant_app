package com.assistant.ua.bridge

import android.util.Log
import com.assistant.ua.ASApp
import com.assistant.ua.LFUserInfo
import com.assistant.ua.datasource.room.DbRepository
import com.assistant.ua.framework.base.BasePresenter

/** Created by qinbaoyuan on 2018/11/11
 */
open class SplashPresenter : BasePresenter {
    override fun onCreate() {
        DbRepository.instance.queryLoginInfo(Runnable {
            val list = ASApp.instance.appDatabase.userEntityDao.getLoginUsers()
            if (!list.isEmpty()) {
                LFUserInfo.instance.setUserInfo(list[0])
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