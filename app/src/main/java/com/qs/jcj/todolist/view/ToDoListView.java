package com.qs.jcj.todolist.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.qs.jcj.todolist.R;

/**
 * Created by jcj on 16/4/25.
 */
public class ToDoListView extends ListView {

    private View headerView;
    private int headerHeight;

    int downY = -1;
    int paddingTop = -1;


    public ToDoListView(Context context) {
        this(context, null);
    }

    public ToDoListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToDoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                downY = (int) event.getRawY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int moveY = (int) event.getRawY();
//                int detalY = moveY - downY;
//                if (detalY > 50) {
//                    System.out.println(123);
//                    paddingTop = -headerHeight + detalY;
//                    if (paddingTop > 0) paddingTop = 0;
//                    headerView.setPadding(0, paddingTop, 0, 0);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                if (paddingTop < 0) {
//                    headerView.setPadding(0, -headerHeight, 0, 0);
//                    invalidate();
//                }
//                if (paddingTop == 0) {
//                    if (linstener != null) {
//                        linstener.add();
//                    }
//                    headerView.setPadding(0, -headerHeight, 0, 0);
//                    invalidate();
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    private void initUI() {
        initHeaderView();
        //    this.setOnTouchListener(this);
    }

    /**
     * 初始化头布局，实现下拉增加
     */
    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.header_item, null);
        headerView.measure(0, 0);
        headerHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerHeight, 0, 0);
        addHeaderView(headerView);
    }

    int dX;
    int dY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                dX = (int) ev.getRawX();
                dY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                int moveY = (int) ev.getRawY();
                int detalX = moveX - dX;
                int detalY = moveY - dY;
                if (Math.abs(detalX) > Math.abs(detalY)) {
                    return super.onInterceptTouchEvent(ev);
                } else {
                    return true;
                }

        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (downY == -1) {
                    downY = (int) ev.getRawY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                int detalY = moveY - downY;
                paddingTop = -headerHeight + detalY/2;
                headerView.setPadding(0, paddingTop, 0, 0);
                break;
            case MotionEvent.ACTION_UP:
                if (paddingTop < 0) {
                    headerView.setPadding(0, -headerHeight, 0, 0);
                    invalidate();
                } else {
                    if (linstener != null) {
                        linstener.add();
                    }
                    headerView.setPadding(0, -headerHeight, 0, 0);
                    invalidate();
                }

                paddingTop = -1;
                downY = -1;
                break;
        }
        return super.onTouchEvent(ev);
    }

    private OnToDoListViewAddLinstener linstener;

    public void setOnToDoListViewAddLinstener(OnToDoListViewAddLinstener linstener) {
        this.linstener = linstener;
    }

    public interface OnToDoListViewAddLinstener {
        void add();
    }
}
