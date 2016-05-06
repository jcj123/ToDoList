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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qs.jcj.addlistview.adapter.ToDoAdapter;
import com.qs.jcj.addlistview.dao.ToDoDao;
import com.qs.jcj.addlistview.domain.Item;
import com.qs.jcj.addlistview.utils.AnimationUtils;
import com.qs.jcj.addlistview.view.ItemView;
import com.qs.jcj.addlistview.view.NestListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private NestListView myListView;
    private List<Item> itemlist = new ArrayList<>();
    private ToDoAdapter myAdapter;
    private ToDoDao dao;

    private FloatingActionButton fab;
    private Button add_item;
    private Button question;
    private Button search;
    private Button share;
    private boolean isMenuOpen;//fab的menu是否已经打开
    private DrawerLayout drawerLayout;

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
                if (!isMenuOpen) {
                    openMenu();
                } else {
                    closeMenu();
                }
            }
        });
        animator.setDuration(300);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    /**
     * 打开fab 菜单
     */
    private void openMenu() {
        isMenuOpen = true;
        int radius = 400;
        int total = 4;
        AnimationUtils.openCircleAnimation(add_item, 0, total, radius);
        AnimationUtils.openCircleAnimation(question, 3, total, radius);
        AnimationUtils.openCircleAnimation(search, 2, total, radius);
        AnimationUtils.openCircleAnimation(share, 1, total, radius);
    }

    /**
     * 关闭fab 菜单
     */
    private void closeMenu() {
        isMenuOpen = false;
        int radius = 400;
        int total = 4;
        AnimationUtils.closeCircleAnimation(add_item, 0, total, radius);
        AnimationUtils.closeCircleAnimation(question, 3, total, radius);
        AnimationUtils.closeCircleAnimation(search, 2, total, radius);
        AnimationUtils.closeCircleAnimation(share, 1, total, radius);
    }


    /**
     * 初始化UI
     */
    private void initUI() {
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        add_item = (Button) findViewById(R.id.add_item);
        question = (Button) findViewById(R.id.question);
        search = (Button) findViewById(R.id.search);
        share = (Button) findViewById(R.id.share);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.main);
        navigationView.setNavigationItemSelectedListener(this);
        myListView = (NestListView) findViewById(R.id.lv);

    }

    private void initData() {
        dao = new ToDoDao(this);
        itemlist = dao.findAll();
        System.out.println("itemlist="+itemlist);
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
                closeMenu();
                break;
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}
