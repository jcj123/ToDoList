package com.qs.jcj.addlistview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.qs.jcj.addlistview.adapter.ToDoAdapter;
import com.qs.jcj.addlistview.dao.ToDoDao;
import com.qs.jcj.addlistview.view.NestListView;

/**
 * Created by jcj on 16/5/4.
 */
public class BaseActivity extends AppCompatActivity  {
     NestListView listView;
     ToDoDao dao;
     ToDoAdapter toDoAdapter;
     CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        listView = (NestListView) findViewById(R.id.lv);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbar);
        dao = new ToDoDao(this);
    }

}
