package com.qs.jcj.addlistview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qs.jcj.addlistview.MainActivity;
import com.qs.jcj.addlistview.R;
import com.qs.jcj.addlistview.dao.ToDoDao;
import com.qs.jcj.addlistview.domain.Item;
import com.qs.jcj.addlistview.utils.MyApplication;
import com.qs.jcj.addlistview.view.ItemView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcj on 16/4/27.
 */
public class ToDoAdapter extends BaseAdapter {
    private List<Item> itemlist = new ArrayList<>();
    private Context context;
    private ToDoDao dao;

    public ToDoAdapter(List<Item> itemlist, Context context, ToDoDao dao) {
        this.itemlist = itemlist;
        this.context = context;
        this.dao = dao;
    }

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

    /**
     * 恢复颜色
     * @param level 原本的颜色等级
     * @param view  要改变颜色的view
     */
    public void restoreColor(int level, View view) {
        switch (level) {
            case 0:
                view.setBackgroundResource(R.drawable.shape1);
                break;
            case 1:
                view.setBackgroundResource(R.drawable.shape2);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.shape3);

                break;
            case 3:
                view.setBackgroundResource(R.drawable.shape4);

                break;
            case 4:
                view.setBackgroundResource(R.drawable.shape5);

                break;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemView view;
        if (convertView == null) {
            view = (ItemView) View.inflate(context, R.layout.item_list, null);
        } else {
            view = (ItemView) convertView;
        }
        final TextView mainTextView = (TextView) view.findViewById(R.id.main_tv);
        final LevelListDrawable drawable = (LevelListDrawable) mainTextView.getBackground();
        drawable.setLevel(position);
        final String name = itemlist.get(position).getName();
        mainTextView.setText(name);

        view.setOnItemStatusLinstener(new ItemView.OnItemStatusLinstener() {
            @Override
            public void delete(View foreView) {
                dao.delete(itemlist.get(position).getId());
                itemlist.remove(position);
                if (this != null) {
                    notifyDataSetChanged();
                }
            }

            @Override
            public void changeStatus(View foreView) {
                //从数据库获取当前条目的状态，如果是0则为未完成，1为已完成
                final int isStatus = dao.getStatus(itemlist.get(position).getId());
                if (isStatus == 1) {
                    dao.update(itemlist.get(position).getId(), 0);
                    restoreColor(position,mainTextView);
                } else {
                    dao.update(itemlist.get(position).getId(), 1);
                    foreView.setBackgroundColor(Color.GRAY);
                }
            }

            @Override
            public void checkStatus(View foreView) {
                final int isStatus = dao.getStatus(itemlist.get(position).getId());
                if (isStatus == 1) {
                    System.out.println("1");
                    foreView.setBackgroundColor(Color.GRAY);
                } else {
                    System.out.println("0");
                    restoreColor(position,mainTextView);
                }
            }

        });
        return view;
    }

}
