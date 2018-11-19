package com.assistant.ua.datasource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Created by qinbaoyuan on 2018/11/13
 */
@Entity(tableName = "login_user")
class UserEntity {

    constructor()

    public var guestId: Int = 0
    @PrimaryKey
    public var phoneNum: String = ""
    public var userName: String = ""
    public var passWord: String = ""
    public var createTime: String = ""
    public var sex: Int = 0//0:男，1：女
    public var portraitUrl: String? = ""
}