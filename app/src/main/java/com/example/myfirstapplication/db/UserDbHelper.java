package com.example.myfirstapplication.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myfirstapplication.pojo.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserDbHelper extends SQLiteOpenHelper {

    private static UserDbHelper sHelper;
    private static final String DB_NAME = "user.db";   //数据库名
    private static final int VERSION = 1;    //版本号

    //必须实现其中一个构方法
    public UserDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //创建单例，供使用调用该类里面的的增删改查的方法
    public synchronized static UserDbHelper getInstance(Context context) {
        if (null == sHelper) {
            sHelper = new UserDbHelper(context, DB_NAME, null, VERSION);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建user_table表
        db.execSQL("create table user_table(user_id integer primary key autoincrement, " +
                "username text," +       //用户名
                "password text" +      //密码
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*注册*/
    public int register(String username, String password) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //填充占位符
        values.put("username", username);
        values.put("password", password);
        String nullColumnHack = "values(null,?,?)";
        //执行
        int insert = (int) db.insert("user_table", nullColumnHack, values);
        db.close();
        return insert;
    }
    /*登录*/
    @SuppressLint("Range")
    public UserInfo login(String username) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getReadableDatabase();
        UserInfo userInfo = null;
        String sql = "select user_id,username,password from user_table where username=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToNext()) {
            int user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
            String name = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            userInfo = new UserInfo(user_id, name, password);
        }
        cursor.close();
        db.close();
        return userInfo;
    }
    /**
     * 查询所有用户
     */
    @SuppressLint("Range")
    public List<UserInfo> queryUserListData() {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getReadableDatabase();
        List<UserInfo> list = new ArrayList<>();
        // 定义列名
        String[] columns = {
                "user_id", // 用户ID
                "username", // 用户名
                "password"  // 密码
        };
        // 查询条件
        String selection = null; // 查询所有记录，所以不需要条件
        String[] selectionArgs = null; // 没有查询条件，所以不需要条件参数
        // 排序方式
        String sortOrder = "user_id ASC"; // 按用户ID升序排序
        // 执行查询
        Cursor cursor = db.query("user_table", columns, selection, selectionArgs, null, null, sortOrder);
        while (cursor.moveToNext()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            userInfo.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            list.add(userInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username =?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query("user_table", columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

}
