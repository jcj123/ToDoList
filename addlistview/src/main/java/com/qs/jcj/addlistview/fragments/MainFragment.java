package com.qs.jcj.addlistview.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.qs.jcj.addlistview.R;
import com.qs.jcj.addlistview.adapter.ToDoAdapter;
import com.qs.jcj.addlistview.dao.ToDoDao;
import com.qs.jcj.addlistview.domain.Item;
import com.qs.jcj.addlistview.view.NestListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 首页
 */
public class MainFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return getView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String createDate = sdf.format(new Date());
        collapsingToolbar.setTitle(createDate);

        itemlist = dao.findByDay(createDate);
        if (toDoAdapter == null) {
            toDoAdapter = new ToDoAdapter(itemlist, getActivity(), dao);
        }
        myListView.setAdapter(toDoAdapter);
    }
}
