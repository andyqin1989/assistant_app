package com.assistant.uaua.datasource.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assistant.uaua.datasource.entity.UserEntity

/** Created by qinbaoyuan on 2018/11/13
 */
@Dao
interface UserEntityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLoginUser(userEntity: UserEntity)

    @Query("select * from  login_user")
    fun getLoginUsers(): List<UserEntity>
}