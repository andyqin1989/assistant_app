package com.assistant.uaua.datasource.net.response

import com.assistant.uaua.datasource.entity.UserEntity

/** Created by qinbaoyuan on 2018/11/13
 */
class LoginResponse : LFBaseResponse() {
    public lateinit var data: UserEntity
}