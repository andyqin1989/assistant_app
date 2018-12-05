package com.assistant.ua

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.assistant.ua.datasource.room.AppDatabase
import com.tencent.smtt.sdk.QbSdk

/** Created by qinbaoyuan on 2018/11/13
 */
class ASApp : Application() {
    lateinit var appDatabase: AppDatabase

    companion object {
        lateinit var instance: ASApp
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appDatabase = AppDatabase.getInstance(this)

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
    }
}