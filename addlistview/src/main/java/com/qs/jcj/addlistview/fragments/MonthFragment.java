package com.qs.jcj.addlistview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qs.jcj.addlistview.adapter.ToDoAdapter;

/**
 * Created by jcj on 16/5/11.
 * 按月查询
 */
public class MonthFragment extends BaseFragment{
    private String day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =getView();
        day = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE).getString("month", "2015-05");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        itemlist = dao.findByMonth(day);
        System.out.println(itemlist);
        toDoAdapter = new ToDoAdapter(itemlist, getActivity(), dao);
        myListView.setAdapter(toDoAdapter);
        collapsingToolbar.setTitle(day.substring(0,day.length()-1));
    }
}
