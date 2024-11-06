package com.example.myfirstapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // 数据库版本
    private static final int DATABASE_VERSION = 1;
    // 数据库名称
    private static final String DATABASE_NAME = "UserDB";
    // 用户信息表名
    private static final String TABLE_NAME = "userInfo";
    // 表中的列名
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_USERID = "userId";
    private static final String COLUMN_FOLLOWERS = "followers";
    private static final String COLUMN_FOLLOWING = "following";

    // 表创建的SQL语句
    private static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT, "
            + COLUMN_USERID + " TEXT, "
            + COLUMN_FOLLOWERS + " INTEGER, "
            + COLUMN_FOLLOWING + " INTEGER" + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //添加用户
    public void addUser(String username, String userId, int followers, int following) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_USERID, userId);
        values.put(COLUMN_FOLLOWERS, followers);
        values.put(COLUMN_FOLLOWING, following);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //更新用户信息
    public void updateUser(String userId, String username, int followers, int following) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_FOLLOWERS, followers);
        values.put(COLUMN_FOLLOWING, following);
        db.update(TABLE_NAME, values, COLUMN_USERID + " = ?", new String[]{userId});
        db.close();
    }

    //查询用户信息
    public Cursor getUser(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_USERID + "=?",
                new String[]{userId}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //删除用户信息
    public void deleteUser(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_USERID + " = ?", new String[]{userId});
        db.close();
    }
}


