package com.qs.jcj.addlistview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.qs.jcj.addlistview.adapter.ToDoAdapter;
import com.qs.jcj.addlistview.dao.ToDoDao;
import com.qs.jcj.addlistview.domain.Item;
import com.qs.jcj.addlistview.utils.AnimationUtils;
import com.qs.jcj.addlistview.utils.ViewUtils;
import com.qs.jcj.addlistview.view.NestListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private final static int REQUEST_ADD = 1;

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
    private CollapsingToolbarLayout collapsingToolbar;//可缩放打开的工具栏
    private ViewUtils viewUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
        initLinstener();
    }

    private void initLinstener() {
        //悬浮按钮的点击时间
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行动画
                fabAnimation();
                //同时打开半透明的activity界面
//                finish();
//                startActivity(new Intent(MainActivity.this,MainActivity.class));
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
    protected void closeMenu() {
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

        viewUtils = new ViewUtils(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.CollapsingToolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.main);
        navigationView.setNavigationItemSelectedListener(this);
        myListView = (NestListView) findViewById(R.id.lv);

    }

    private void initData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String createDate = sdf.format(new Date());
        collapsingToolbar.setTitle(createDate);
        dao = new ToDoDao(this);
        itemlist = dao.findByDay(createDate);
        if (myAdapter == null) {
            myAdapter = new ToDoAdapter(itemlist, this, dao);
        }
        myListView.setAdapter(myAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD:
                    final String item = data.getStringExtra("item");
                    if (!TextUtils.isEmpty(item)) {
                        Item i = new Item(item, 0);
                        itemlist.add(i);
                        dao.insert(item);
                        if (myAdapter != null) {
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }
    }

    /**
     * fab菜单打开后各个按钮的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_item:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_ADD);
                closeMenu();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.search_day:
                drawerLayout.closeDrawers();
                viewUtils.showDatePickerDialog(true);
                break;
            case R.id.search_month:
                drawerLayout.closeDrawers();
                viewUtils.showDatePickerDialog(false);
                break;
        }
        return true;
    }
    
}
