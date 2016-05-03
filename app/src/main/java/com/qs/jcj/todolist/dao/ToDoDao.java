package com.qs.jcj.todolist.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qs.jcj.todolist.domain.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcj on 16/4/26.
 * 将待办事项的内容进行持久化存储
 */
public class ToDoDao {
    private ToDoDaoHelper helper;
    private SQLiteDatabase db;

    public ToDoDao(Context context) {
        helper = new ToDoDaoHelper(context);
    }

    public void insert(String text) {
        db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("content",text);
        values.put("isCompleted",0);
        db.insert("todolist",null,values);
        db.close();
    }

    public void delete(String text) {
        db = helper.getReadableDatabase();
        db.delete("todolist","content = ?",new String[]{text});
        db.close();
    }

    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        final Cursor cursor = db.query("todolist", new String[]{"content","isCompleted"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            final String text = cursor.getString(0);
            final int isCompleted = cursor.getInt(1);
            Item item = new Item(text,isCompleted);
            list.add(item);
        }
        db.close();
        return list;
    }

    public void update(String text,int isCompleted) {
        db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("isCompleted",isCompleted);
        db.update("todolist",values,"content=?",new String[]{text});
        db.close();
    }
}
