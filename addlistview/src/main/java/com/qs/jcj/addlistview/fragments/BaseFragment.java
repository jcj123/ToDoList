package com.qs.jcj.addlistview.fragments;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qs.jcj.addlistview.MainActivity;
import com.qs.jcj.addlistview.R;
import com.qs.jcj.addlistview.adapter.ToDoAdapter;
import com.qs.jcj.addlistview.dao.ToDoDao;
import com.qs.jcj.addlistview.domain.Item;
import com.qs.jcj.addlistview.view.NestListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    public NestListView myListView;
    public List<Item> itemlist = new ArrayList<>();
    public ToDoAdapter toDoAdapter;
    public ToDoDao dao;
    public CollapsingToolbarLayout collapsingToolbar;//可缩放打开的工具栏
    private Toolbar mToolBar;
    private MainActivity activity;

    public View getView() {
        activity = (MainActivity) getActivity();
        final View view = View.inflate(getActivity(),R.layout.fragment_main,null);
        myListView = (NestListView) view.findViewById(R.id.lv);
        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.CollapsingToolbar);
        dao = new ToDoDao(getActivity());
        mToolBar = (Toolbar) view.findViewById(R.id.tool_bar);
        activity.setSupportActionBar(mToolBar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        return view;
    }

}
