package com.qs.jcj.addlistview;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.qs.jcj.addlistview.adapter.ToDoAdapter;
import com.qs.jcj.addlistview.dao.ToDoDao;
import com.qs.jcj.addlistview.domain.Item;
import com.qs.jcj.addlistview.view.NestListView;

import java.util.List;

public class DayActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        final Intent intent = getIntent();
        final String date = intent.getStringExtra("date");
        final List<Item> itemList = dao.findByDay(date);
        if (toDoAdapter==null) {
            toDoAdapter = new ToDoAdapter(itemList,this,dao);
        }
        listView.setAdapter(toDoAdapter);
        collapsingToolbar.setTitle(date);
    }
}
