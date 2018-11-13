package com.assistant.uaua

import com.assistant.uaua.datasource.entity.UserEntity

/** Created by qinbaoyuan on 2018/11/12
 */
class LFUserInfo private constructor() {
    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = LFUserInfo()
    }

    var userName: String = ""
    var phoneNum: String = ""
    var portraitUrl: String = ""
    var sex: Int = 0

    fun isLogin(): Boolean {
        return userName.isNotEmpty()
    }

    public fun setUserInfo(entity: UserEntity) {
        userName = entity.userName
        phoneNum = entity.phoneNum
        portraitUrl = entity.portraitUrl
        sex = entity.sex
    }

}