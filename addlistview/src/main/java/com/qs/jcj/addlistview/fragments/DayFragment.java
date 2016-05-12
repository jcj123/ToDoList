package com.qs.jcj.addlistview.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class DayFragment extends BaseFragment {


    private String day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =getView();
        day = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE).getString("date", "2015-05-08");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        itemlist = dao.findByDay(day);
        System.out.println(itemlist);
        toDoAdapter = new ToDoAdapter(itemlist, getActivity(), dao);
        myListView.setAdapter(toDoAdapter);
        collapsingToolbar.setTitle(day);
    }

}
