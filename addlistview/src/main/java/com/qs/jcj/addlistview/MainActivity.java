package com.qs.jcj.addlistview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.qs.jcj.addlistview.domain.Item;
import com.qs.jcj.addlistview.fragments.DayFragment;
import com.qs.jcj.addlistview.fragments.MainFragment;
import com.qs.jcj.addlistview.fragments.MonthFragment;
import com.qs.jcj.addlistview.utils.AnimationUtils;
import com.qs.jcj.addlistview.utils.DatePickerDialogUtils;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private final static int REQUEST_ADD = 1;

    private FrameLayout fab_fl;//装载悬浮按钮的帧界面
    private FloatingActionButton fab;//加号悬浮按钮
    private Button add_item;//添加新条目
    private Button question;//报告BUG
    private Button search;//搜索条目
    private Button share;//分享软件
    private boolean isMenuOpen;//fab的menu是否已经打开
    private DrawerLayout drawerLayout;//侧拉菜单栏
    private DatePickerDialogUtils viewUtils;

    public MainFragment mainFragment;
    public DayFragment dayFragment;

    private FragmentStatus currentStatus = FragmentStatus.Main;
    public SharedPreferences sp;
    private MonthFragment monthFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initLinstener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fragmentManage();
    }

    private void initLinstener() {
        //悬浮按钮的点击时间
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //执行动画
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

        sp = getSharedPreferences("config", MODE_PRIVATE);
        fab_fl = (FrameLayout) findViewById(R.id.fab_fl);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        add_item = (Button) findViewById(R.id.add_item);
        question = (Button) findViewById(R.id.question);
        search = (Button) findViewById(R.id.search);
        share = (Button) findViewById(R.id.share);

        viewUtils = new DatePickerDialogUtils(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.main);
        navigationView.setNavigationItemSelectedListener(this);

    }

    /**
     * 根据用户点击的不用选项显示不同的Fragment界面（main,day,month）
     */
    public void fragmentManage() {
        mainFragment = new MainFragment();
        dayFragment = new DayFragment();
        monthFragment = new MonthFragment();
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        switch (currentStatus) {
            case Main:
                fab_fl.setVisibility(View.VISIBLE);
                transaction.replace(R.id.item_fl, mainFragment, "MAINFRAGMENT");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case Day:
                fab_fl.setVisibility(View.INVISIBLE);
                transaction.replace(R.id.item_fl, dayFragment, "DAYFRAGMENT");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case Month:
                fab_fl.setVisibility(View.INVISIBLE);
                transaction.replace(R.id.item_fl, monthFragment, "MONTHFRAGMENT");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD:
                    final String item = data.getStringExtra("item");
                    final String showdate = data.getStringExtra("showdate");
                    if (!TextUtils.isEmpty(item) && !TextUtils.isEmpty(showdate)) {
                        Item i = new Item(item, 0, showdate);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        final String createDate = sdf.format(new Date());
                        if (showdate.equalsIgnoreCase(createDate)) {
                            mainFragment.itemlist.add(i);
                        }
                        mainFragment.dao.insert(i);
                        if (mainFragment.toDoAdapter != null) {
                            mainFragment.toDoAdapter.notifyDataSetChanged();
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
            case R.id.main:
                currentStatus = FragmentStatus.Main;
                fragmentManage();
                drawerLayout.closeDrawers();
                break;
            case R.id.search_day:
                drawerLayout.closeDrawers();
                viewUtils.showDatePickerDialog(true);
                currentStatus = FragmentStatus.Day;
                break;
            case R.id.search_month:
                drawerLayout.closeDrawers();
                viewUtils.showDatePickerDialog(false);
                currentStatus = FragmentStatus.Month;
                break;
        }
        return true;
    }

    public static enum FragmentStatus {
        Main, Day, Month
    }

    /**
     * 点击返回键的操作
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        switch (currentStatus) {
            case Main:
                finish();
                break;
            case Day:
            case Month:
                currentStatus = FragmentStatus.Main;
                fragmentManage();
                break;
        }
    }
}
