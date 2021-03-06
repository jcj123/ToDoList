package com.qs.jcj.addlistview.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.qs.jcj.addlistview.domain.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public void insert(Item item) {
        db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", item.getName());
        values.put("isCompleted", item.getIsCompleted());
        values.put("createdate", item.getCreateDate());
        db.insert("todolist", null, values);
        db.close();
    }

    public void delete(int id) {
        db = helper.getReadableDatabase();
        db.delete("todolist", "_id = ?", new String[]{Integer.toString(id)});
        db.close();
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        final Cursor cursor = db.query("todolist", new String[]{"_id", "content", "isCompleted", "createdate"}
                , null, null, null, null, null);
        while (cursor.moveToNext()) {
            final int id = cursor.getInt(0);
            final String text = cursor.getString(1);
            final int isCompleted = cursor.getInt(2);
            final String createDate = cursor.getString(3);
            Item item = new Item(id, text, isCompleted, createDate);
            list.add(item);
        }
        db.close();
        return list;
    }

    public void update(int id, int isCompleted) {
        db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("isCompleted", isCompleted);
        db.update("todolist", values, "_id=?", new String[]{Integer.toString(id)});
        db.close();
    }

    /**
     * 按日查询
     * @param day
     * @return
     */
    public List<Item> findByDay(String day) {
        List<Item> list = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.query("todolist", new String[]{"_id", "content", "isCompleted", "createdate"},
                "createdate=?", new String[]{day}, null, null, null);
        while (cursor.moveToNext()) {
            final int id = cursor.getInt(0);
            final String text = cursor.getString(1);
            final int isCompleted = cursor.getInt(2);
            final String createDate = cursor.getString(3);
            Item item = new Item(id, text, isCompleted, createDate);
            list.add(item);
        }
        db.close();
        return list;
    }
    public List<Item> findByMonth(String day) {
        List<Item> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        System.out.println("select * from todolist where createdate LIKE"+"'"+day+"'");
        Cursor cursor = db.rawQuery("select * from todolist where createdate LIKE"+"'"+day+"'",null);
        while (cursor.moveToNext()) {
            final int id = cursor.getInt(0);
            final String text = cursor.getString(1);
            final int isCompleted = cursor.getInt(2);
            final String createDate = cursor.getString(3);
            Item item = new Item(id, text, isCompleted, createDate);
            list.add(item);
        }
        db.close();
        return list;
    }

    public int getStatus(int id) {
        db = helper.getReadableDatabase();
        final Cursor cursor = db.rawQuery("select isCompleted from todolist where _id =?",
                new String[]{Integer.toString(id)});
        cursor.moveToNext();
        int status = cursor.getInt(0);
        return status;
    }
}
