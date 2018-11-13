package com.assistant.uaua.datasource.room

import com.assistant.uaua.ASApp
import com.assistant.uaua.datasource.entity.UserEntity
import com.assistant.uaua.framework.AppExecutor

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
        AppExecutor.getInstance()
            .execute { ASApp.instance.appDatabase.userEntityDao.saveLoginUser(userEntity) }
    }

    /**
     *
     */

}