package com.qs.jcj.addlistview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qs.jcj.addlistview.adapter.ToDoAdapter;
import com.qs.jcj.addlistview.dao.ToDoDao;
import com.qs.jcj.addlistview.domain.Item;
import com.qs.jcj.addlistview.view.ItemView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ListView myListView;
    private List<Item> itemlist = new ArrayList<>();
    private ToDoAdapter myAdapter;
    private ToDoDao dao;

    private FloatingActionButton fab;
    private Button add_item;
    private Button question;
    private Button search;
    private Button share;
    private boolean isMenuOpen;//fab的menu是否已经打开

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
        initLinstener();
    }

    private void initLinstener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabAnimation();
            }
        });
        add_item.setOnClickListener(this);
        question.setOnClickListener(this);
        search.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    /**
     * floating的动画效果
     */
    private void fabAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "rotation", 0f, -45f, 0f);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int radius = 400;
                int total = 4;
                if (!isMenuOpen) {
                    isMenuOpen = true;
                    openCircleAnimation(add_item, 0, total, radius);
                    openCircleAnimation(question, 3, total, radius);
                    openCircleAnimation(search, 2, total, radius);
                    openCircleAnimation(share, 1, total, radius);
                } else {
                    isMenuOpen = false;
                    closeCircleAnimation(add_item, 0, total, radius);
                    closeCircleAnimation(question, 3, total, radius);
                    closeCircleAnimation(search, 2, total, radius);
                    closeCircleAnimation(share, 1, total, radius);
                }
            }
        });
        animator.setDuration(400);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    /**
     * 点击悬浮的fab按钮后，关闭圆弧形动画，包括执行的各种按钮
     */
    private void closeCircleAnimation(View view, int index, int total, int radius) {
        double degree = 90 * index / (total - 1);
        final double radians = Math.toRadians(degree);
        final float translationY = (float) (radius * Math.cos(radians));
        final float translationX = (float) (radius * Math.sin(radians));
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", -translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", -translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0));
        //动画周期为 500ms
        set.setDuration(1 * 500).start();
    }

    /**
     * 点击悬浮的fab按钮后，弹出圆弧形动画，包括执行的各种按钮
     */
    private void openCircleAnimation(View view, int index, int total, int radius) {
        double degree = 90 * index / (total - 1);
        final double radians = Math.toRadians(degree);
        final float translationY = (float) (radius * Math.cos(radians));
        final float translationX = (float) (radius * Math.sin(radians));
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, -translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, -translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为 500ms
        set.setDuration(1 * 500).start();

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        add_item = (Button) findViewById(R.id.add_item);
        question = (Button) findViewById(R.id.question);
        search = (Button) findViewById(R.id.search);
        share = (Button) findViewById(R.id.share);

        myListView = (ListView) findViewById(R.id.lv);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.main);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initData() {
        dao = new ToDoDao(this);
        itemlist = dao.findAll();

        if (myAdapter == null) {
            myAdapter = new ToDoAdapter(itemlist, this, dao);
        }
        myListView.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            final String item = data.getStringExtra("item");
            if (!TextUtils.isEmpty(item)) {
                Item i = new Item(item, 0);
                itemlist.add(i);
                dao.insert(item);
                if (myAdapter != null) {
                    myAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main:
                break;

        }
        return false;
    }

    /**
     * fab菜单打开后各个按钮的点击时间
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_item:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }
}
