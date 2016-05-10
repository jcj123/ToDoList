package com.qs.jcj.addlistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qs.jcj.addlistview.adapter.ToDoAdapter;
import com.qs.jcj.addlistview.domain.Item;

import java.util.List;

public class MonthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }
    private void initData() {
        final Intent intent = getIntent();
        final String date = intent.getStringExtra("date");
        System.out.println(date);
        final List<Item> itemList = dao.findByMonth(date);
        if (toDoAdapter==null) {
            toDoAdapter = new ToDoAdapter(itemList,this,dao);
        }
        listView.setAdapter(toDoAdapter);
        collapsingToolbar.setTitle(date.substring(0,date.length()-1));
    }
}
