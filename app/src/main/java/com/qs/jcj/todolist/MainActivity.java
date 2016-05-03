package com.qs.jcj.todolist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.qs.jcj.todolist.dao.ToDoDao;
import com.qs.jcj.todolist.domain.Item;
import com.qs.jcj.todolist.view.ItemView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    private List<Item> itemlist = new ArrayList<>();
    private MyAdapter myAdapter;
    private ToDoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
    }



    private void initUI() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        myListView = (ListView) findViewById(R.id.lv);
    }

    private void initData() {
        dao = new ToDoDao(this);
        itemlist = dao.findAll();

        if (myAdapter == null) {
            myAdapter = new MyAdapter();
        }
        myListView.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final String item = data.getStringExtra("item");
            if (!TextUtils.isEmpty(item)) {
                Item i = new Item(item,0);
                itemlist.add(i);
                dao.insert(item);
                if (myAdapter != null) {
                    myAdapter.notifyDataSetChanged();
                }
            }
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ItemView view;
            if (convertView == null) {
                view = (ItemView) View.inflate(MainActivity.this, R.layout.item_list, null);
            } else {
                view = (ItemView) convertView;
            }
            final TextView mainTextView = (TextView) view.findViewById(R.id.main_tv);
            final String name = itemlist.get(position).getName();
            mainTextView.setText(name);
            int isCompleted = itemlist.get(position).getIsCompleted();
            mainTextView.setBackgroundColor(isCompleted==0?Color.rgb(255, 95, 90):Color.GRAY);
            view.setOnItemStatusLinstener(new ItemView.OnItemStatusLinstener() {
                @Override
                public void delete(View foreView, boolean isItemCompleted) {
                    dao.delete(itemlist.get(position).getName());
                    itemlist.remove(position);
                    if (myAdapter != null) {
                        myAdapter.notifyDataSetChanged();
                    }
                    if (isItemCompleted) {
                        foreView.setBackgroundColor(Color.GRAY);
                    } else {
                        foreView.setBackgroundColor(Color.rgb(255, 95, 90));
                    }
                }
                @Override
                public void cancelCompleted(View foreView, boolean isItemCompleted) {
                    if (isItemCompleted) {
                        dao.update(name,0);
                        foreView.setBackgroundColor(Color.rgb(255, 95, 90));
                    } else {
                        dao.update(name,1);
                        foreView.setBackgroundColor(Color.GRAY);
                    }
                }

                @Override
                public void completed(View foreView, boolean isItemCompleted) {
                    if (isItemCompleted) {
                        dao.update(name,0);

                        foreView.setBackgroundColor(Color.rgb(255, 95, 90));
                    } else {
                        dao.update(name,1);

                        foreView.setBackgroundColor(Color.GRAY);
                    }
                }
            });
            return view;
        }
    }

}
