package com.qs.jcj.addlistview.adapter;

import android.content.Context;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcj on 16/4/27.
 */
public class ToDoAdapter extends BaseAdapter {
    private List<Item> itemlist = new ArrayList<>();
    private Context context;
    private ToDoDao dao;

    public ToDoAdapter(List<Item> itemlist,Context context,ToDoDao dao) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemView view;
        if (convertView == null) {
            view = (ItemView) View.inflate(context, R.layout.item_list, null);
        } else {
            view = (ItemView) convertView;
        }
        final TextView mainTextView = (TextView) view.findViewById(R.id.main_tv);
        final String name = itemlist.get(position).getName();
        mainTextView.setText(name);
        int isCompleted = itemlist.get(position).getIsCompleted();
        mainTextView.setBackgroundColor(isCompleted == 0 ? Color.rgb(255, 95, 90) : Color.GRAY);

        view.setOnItemStatusLinstener(new ItemView.OnItemStatusLinstener() {
            @Override
            public void delete(View foreView, boolean isItemCompleted) {
                dao.delete(itemlist.get(position).getId());
                itemlist.remove(position);
                if (this != null) {
                    notifyDataSetChanged();
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
                    dao.update(itemlist.get(position).getId(), 0);
                    foreView.setBackgroundColor(Color.rgb(255, 95, 90));
                } else {
                    dao.update(itemlist.get(position).getId(), 1);
                    foreView.setBackgroundColor(Color.GRAY);
                }
            }

            @Override
            public void completed(View foreView, boolean isItemCompleted) {
                if (isItemCompleted) {
                    dao.update(itemlist.get(position).getId(), 0);

                    foreView.setBackgroundColor(Color.rgb(255, 95, 90));
                } else {
                    dao.update(itemlist.get(position).getId(), 1);

                    foreView.setBackgroundColor(Color.GRAY);
                }
            }
        });
        return view;
    }
}
