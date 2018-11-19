package com.assistant.ua.datasource.net.response

import com.assistant.ua.datasource.entity.UserEntity

/** Created by qinbaoyuan on 2018/11/13
 */
class LoginResponse : LFBaseResponse() {
    public lateinit var data: UserEntity
}