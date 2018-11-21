package com.assistant.ua.datasource.room

import android.util.Log
import com.assistant.ua.ASApp
import com.assistant.ua.datasource.entity.UserEntity
import com.assistant.ua.framework.AppExecutor

/** Created by qinbaoyuan on 2018/11/13
 */
class DbRepository {
    companion object {
        val instance = DbRepository()
    }

    /**
     * 将数据存到数据库中
     */
    fun saveLoginInfo(userEntity: UserEntity) {
        deleteLoginInfo()
        AppExecutor.getInstance().executeIoTask {
            ASApp.instance.appDatabase.userEntityDao.saveLoginUser(userEntity)
            Log.e("andyqin", "登录用户保存成功")
        }
    }

    /**
     *读取登录用户信息
     */
    fun queryLoginInfo(runnable: Runnable) {
        AppExecutor.getInstance().executeIoTask(runnable)
    }

    /**
     * 删除所以用户
     */
    fun deleteLoginInfo() {
        AppExecutor.getInstance().executeIoTask {
            ASApp.instance.appDatabase.userEntityDao.deleteLoginUsers()
        }
        Log.e("andyqin", "数据库删除成功")
    }

}