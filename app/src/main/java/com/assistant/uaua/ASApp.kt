package com.assistant.uaua

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.assistant.uaua.datasource.room.AppDatabase

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
    }
}