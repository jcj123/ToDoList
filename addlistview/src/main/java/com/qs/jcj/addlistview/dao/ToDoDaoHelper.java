package com.qs.jcj.addlistview.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jcj on 16/4/26.
 */
public class ToDoDaoHelper extends SQLiteOpenHelper{
    public ToDoDaoHelper(Context context) {
        super(context, "toDo.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table todolist (_id integer primary key autoincrement,content char," +
                "isCompleted integer,createdate char)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table todolist");
        onCreate(db);
    }
}
