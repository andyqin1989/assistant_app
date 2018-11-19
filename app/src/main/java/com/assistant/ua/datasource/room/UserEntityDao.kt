package com.assistant.ua.datasource.room

import androidx.room.*
import com.assistant.ua.datasource.entity.UserEntity

/** Created by qinbaoyuan on 2018/11/13
 */
@Dao
interface UserEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLoginUser(userEntity: UserEntity)

    @Query("select * from  login_user")
    fun getLoginUsers(): List<UserEntity>

    @Query("delete from login_user")
    fun deleteLoginUsers()
}