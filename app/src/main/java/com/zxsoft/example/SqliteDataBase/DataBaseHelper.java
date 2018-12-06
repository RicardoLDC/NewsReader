package com.zxsoft.example.SqliteDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_NEWS="create table PostMessage ("
            + "uniquekey text primary key,"
            + "title text,"
            + "date text,"
            + "category text,"
            + "author_name text,"
            + "url text,"
            + "thumbnail_pic_s text,"
            + "thumbnail_pic_s02 text,"
            + "thumbnail_pic_s03 text)";

    public static final String CREATE_STORE="create table historyNews ("
            + "uniquekey text primary key,"
            + "title text,"
            + "date text,"
            + "category text,"
            + "author_name text,"
            + "url text,"
            + "thumbnail_pic_s text,"
            + "thumbnail_pic_s02 text,"
            + "thumbnail_pic_s03 text)";
    private Context mContext;

    public DataBaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version) {
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL(CREATE_NEWS);
         db.execSQL(CREATE_STORE);
         Log.d("DataBaseHelper","表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
