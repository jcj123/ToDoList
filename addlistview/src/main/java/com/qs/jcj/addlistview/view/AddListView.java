package com.qs.jcj.addlistview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.qs.jcj.addlistview.R;

/**
 * Created by jcj on 16/4/27.
 */
public class AddListView extends ListView {

    private int headerHeight;

    private final static int RELEASH_ADD = 0;//释放添加
    private final static int PULL_ADD = 1;//下拉添加
    //   private final static int RELEASH_REFRESH = 0;//释放刷新

    private int currentState = PULL_ADD;

    int downY;
    int paddingTop;
    private View headerView;
    private TextView addTextView;

    public AddListView(Context context) {
        this(context, null);
    }

    public AddListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        headerView = View.inflate(getContext(), R.layout.header_view, null);
        addTextView = (TextView) headerView.findViewById(R.id.tv_add);
        headerView.measure(0, 0);
        headerHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerHeight, 0, 0);
        addHeaderView(headerView);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                refreshHeaderView();

                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = (int) (ev.getY() - downY);
                paddingTop = -headerHeight + deltaY;

                headerView.setPadding(0, paddingTop, 0, 0);

                if (paddingTop >= 0 && currentState == PULL_ADD) {
                    //从下拉刷新进入松开刷新状态
                    currentState = RELEASH_ADD;
                    refreshHeaderView();
                } else if (paddingTop < 0 && currentState == RELEASH_ADD) {
                    //进入下拉刷新状态
                    currentState = PULL_ADD;
                    refreshHeaderView();
                }
                break;
            case MotionEvent.ACTION_UP:
                headerView.setPadding(0, -headerHeight, 0, 0);
                if (currentState == RELEASH_ADD) {
                    currentState = PULL_ADD;
                    if (listener != null) {
                        listener.add();
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    private void refreshHeaderView() {
        switch (currentState) {
            case RELEASH_ADD:
                addTextView.setText("松开添加条目");
                break;
            case PULL_ADD:
                addTextView.setText("继续下拉增加条目");
                break;
        }
    }

    private OnToDoListLinstener listener;

    public void setOnToDoListLinstener(OnToDoListLinstener listener) {
        this.listener = listener;
    }

    public interface OnToDoListLinstener {
        void add();
    }
}
