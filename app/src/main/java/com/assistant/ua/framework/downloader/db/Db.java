package com.assistant.ua.framework.downloader.db;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.assistant.ua.framework.downloader.data.DownloadData;

import java.util.ArrayList;
import java.util.List;

import static com.assistant.ua.framework.downloader.data.Consts.PROGRESS;


public class Db {
    /**
     * 数据库名
     */
    private static final String DB_NAME = "assistant_download_db";

    /**
     * 数据库版本
     */
    private static final int VERSION = 1;

    private String TABLE_NAME_DOWNLOAD = "download_info";

    private static Db db;

    private SQLiteDatabase mSQLiteDatabase = null;

    public static Db getInstance() {
        if (db == null) {
            synchronized (Db.class) {
                if (db == null) {
                    db = new Db();
                }
            }
        }
        return db;
    }

    public void initDB(Application app) {
        if (null != mSQLiteDatabase) return;
        DbOpenHelper dbHelper = new DbOpenHelper(app, DB_NAME, null, VERSION);
        mSQLiteDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * 保存下载信息
     */
    public void insertData(DownloadData data) {
        if (null == mSQLiteDatabase) return;
        ContentValues values = new ContentValues();
        values.put("url", data.getUrl());
        values.put("path", data.getPath());
        values.put("name", data.getName());
        values.put("child_task_count", data.getChildTaskCount());
        values.put("current_length", data.getCurrentLength());
        values.put("total_length", data.getTotalLength());
        values.put("percentage", data.getPercentage());
        values.put("status", data.getStatus());
        values.put("last_modify", data.getLastModify());
        values.put("date", data.getDate());
        values.put("task_id", data.getTaskId());
        mSQLiteDatabase.insert(TABLE_NAME_DOWNLOAD, null, values);
    }

    public void insertDatas(List<DownloadData> dataList) {
        for (DownloadData data : dataList) {
            insertData(data);
        }
    }

    /**
     * 获得url对应的下载数据
     */
    public DownloadData getData(String url) {
        if (null == mSQLiteDatabase) return null;
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME_DOWNLOAD, null, "url = ?",
                new String[]{url}, null, null, null);

        if (!cursor.moveToFirst()) {
            return null;
        }

        DownloadData data = new DownloadData();

        data.setUrl(cursor.getString(cursor.getColumnIndex("url")));
        data.setPath(cursor.getString(cursor.getColumnIndex("path")));
        data.setName(cursor.getString(cursor.getColumnIndex("name")));
        data.setChildTaskCount(cursor.getInt(cursor.getColumnIndex("child_task_count")));
        data.setCurrentLength(cursor.getInt(cursor.getColumnIndex("current_length")));
        data.setTotalLength(cursor.getInt(cursor.getColumnIndex("total_length")));
        data.setPercentage(cursor.getFloat(cursor.getColumnIndex("percentage")));
        data.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
        data.setLastModify(cursor.getString(cursor.getColumnIndex("last_modify")));
        data.setDate(cursor.getInt(cursor.getColumnIndex("date")));
        data.setTaskId(cursor.getInt(cursor.getColumnIndex("task_id")));

        cursor.close();

        return data;
    }

    /**
     * 获得全部下载数据
     *
     * @return
     */
    public List<DownloadData> getAllData() {
        List<DownloadData> list = new ArrayList<>();
        if (null == mSQLiteDatabase) return list;
        Cursor cursor = mSQLiteDatabase.query(TABLE_NAME_DOWNLOAD, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                DownloadData data = new DownloadData();
                data.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                data.setPath(cursor.getString(cursor.getColumnIndex("path")));
                data.setName(cursor.getString(cursor.getColumnIndex("name")));
                data.setChildTaskCount(cursor.getInt(cursor.getColumnIndex("child_task_count")));
                data.setCurrentLength(cursor.getInt(cursor.getColumnIndex("current_length")));
                data.setTotalLength(cursor.getInt(cursor.getColumnIndex("total_length")));
                data.setPercentage(cursor.getFloat(cursor.getColumnIndex("percentage")));
                data.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                data.setLastModify(cursor.getString(cursor.getColumnIndex("last_modify")));
                data.setDate(cursor.getInt(cursor.getColumnIndex("date")));
                data.setTaskId(cursor.getInt(cursor.getColumnIndex("task_id")));

                list.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /**
     * 更新下载信息
     */
    public void updateProgress(int currentSize, float percentage, int status, String url) {
        if (null == mSQLiteDatabase) return;
        ContentValues values = new ContentValues();
        if (status != PROGRESS) {
            values.put("current_length", currentSize);
            values.put("percentage", percentage);
        }
        values.put("status", status);
        mSQLiteDatabase.update(TABLE_NAME_DOWNLOAD, values, "url = ?", new String[]{url});
    }

    /**
     * 删除下载信息
     */
    public void deleteData(String url) {
        if (null == mSQLiteDatabase) return;
        mSQLiteDatabase.delete(TABLE_NAME_DOWNLOAD, "url = ?", new String[]{url});
    }
}
