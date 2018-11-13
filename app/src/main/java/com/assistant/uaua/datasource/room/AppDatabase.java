package com.assistant.uaua.datasource.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.assistant.uaua.datasource.entity.UserEntity;

/**
 * Created by qinbaoyuan on 2018/11/13
 */
@Database(entities = {UserEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static String DB_NAME = "app_db";
    private volatile static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
                }
            }
        }
        return instance;
    }

    public abstract UserEntityDao getUserEntityDao();

}