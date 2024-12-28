package com.example.myfirstapplication.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myfirstapplication.pojo.NoteInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class NoteDbHelper extends SQLiteOpenHelper {

    private static NoteDbHelper sHelper;
    private static final String DB_NAME = "note_info.db";   //数据库名
    private static final int VERSION = 1;    //版本号

    //必须实现其中一个构方法
    public NoteDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //创建单例，供使用调用该类里面的的增删改查的方法
    public synchronized static NoteDbHelper getInstance(Context context) {
        if (null == sHelper) {
            sHelper = new NoteDbHelper(context, DB_NAME, null, VERSION);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建note_table表
        db.execSQL("create table note_table(id integer primary key autoincrement, " +
                "userName text," +       //用户名
                "title text," +      //标题
                "mainBody text," +      //正文
                "likesNumber integer," +       // 获赞数
                "savesNumber integer," +       // 获赞数
                "commentsNumber integer," +       // 获赞数
                "img text," +       // 注册类型
                "createdAt text," +       // 注册类型
                "updatedAt text" +       // 注册类型
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*新增笔记*/
    public int addNote(String username, String title, String mainBody,String img) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //填充占位符
        values.put("username", username);
        values.put("title", title);
        values.put("mainBody", mainBody);
        values.put("likesNumber", 0);
        values.put("savesNumber", 0);
        values.put("commentsNumber", 0);
        values.put("img", img);
        values.put("createdAt", getCurrentTime());
        values.put("updatedAt", getCurrentTime());
        String nullColumnHack = "values(null,?,?,?,?,?,?,?,?,?)";
        //执行
        int insert = (int) db.insert("note_table", nullColumnHack, values);
        db.close();
        return insert;
    }

    /*删除笔记*/
    public int deleteNote(int noteId) {
        // 获取SQLiteDatabase实例
        SQLiteDatabase db = getWritableDatabase();
        // 构建删除条件
        String whereClause = "id = ?";
        // 执行删除操作
        int delete = db.delete("note_table", whereClause, new String[]{String.valueOf(noteId)});
        db.close();
        return delete;
    }

    public String getCurrentTime() {
        // 获取当前时间
        Date now = new Date();

        // 创建一个SimpleDateFormat对象，指定日期时间格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 指定时区为东八区（北京时间）
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        // 格式化当前时间为指定格式的字符串
        return formatter.format(now);
    }

    /**
     * 根据用户名查询笔记
     */
    @SuppressLint("Range")
    public List<NoteInfo> queryNoteListData(String username) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getReadableDatabase();
        List<NoteInfo> list = new ArrayList<>();
        String sql = "select id,title,mainBody,username,likesNumber,savesNumber,commentsNumber,img,createdAt,updatedAt  from note_table where username=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            int note_id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String mainBody = cursor.getString(cursor.getColumnIndex("mainBody"));
            String name = cursor.getString(cursor.getColumnIndex("userName"));
            int likesNumber = cursor.getInt(cursor.getColumnIndex("likesNumber"));
            int savesNumber = cursor.getInt(cursor.getColumnIndex("savesNumber"));
            int commentsNumber = cursor.getInt(cursor.getColumnIndex("commentsNumber"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String createdAt = cursor.getString(cursor.getColumnIndex("createdAt"));
            String updatedAt = cursor.getString(cursor.getColumnIndex("updatedAt"));
            list.add(new NoteInfo(note_id, title,mainBody,name, likesNumber, savesNumber,commentsNumber,img,createdAt,updatedAt));
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 根据用户名查询最新笔记
     */
    @SuppressLint("Range")
    public NoteInfo queryNewNoteData(String username) {
        //获取SQLiteDatabase实例
        SQLiteDatabase db = getReadableDatabase();

        String sql = "select id,title,mainBody,username,likesNumber,savesNumber,commentsNumber,img,createdAt,updatedAt  from note_table where username=? ORDER BY createdAt DESC LIMIT 1";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor != null && cursor.moveToFirst()) {
            int note_id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String mainBody = cursor.getString(cursor.getColumnIndex("mainBody"));
            String name = cursor.getString(cursor.getColumnIndex("userName"));
            int likesNumber = cursor.getInt(cursor.getColumnIndex("likesNumber"));
            int savesNumber = cursor.getInt(cursor.getColumnIndex("savesNumber"));
            int commentsNumber = cursor.getInt(cursor.getColumnIndex("commentsNumber"));
            String img = cursor.getString(cursor.getColumnIndex("img"));
            String createdAt = cursor.getString(cursor.getColumnIndex("createdAt"));
            String updatedAt = cursor.getString(cursor.getColumnIndex("updatedAt"));
            NoteInfo latestNote = new NoteInfo(note_id, title,mainBody,name, likesNumber, savesNumber,commentsNumber,img,createdAt,updatedAt);
            cursor.close();
            db.close();
            return latestNote;
        }else{
            cursor.close();
            db.close();
            return null; // 或者抛出异常，根据您应用的需求
        }
    }
}
