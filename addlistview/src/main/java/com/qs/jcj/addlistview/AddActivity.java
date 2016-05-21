package com.qs.jcj.addlistview;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.qs.jcj.addlistview.utils.DatePickerDialogUtils;
import com.qs.jcj.addlistview.view.AddView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddActivity extends AppCompatActivity implements AddView.OnCommitItemLinstener, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private EditText addEditText;
    @BindView(R.id.av) AddView addView;
    private DrawerLayout drawerLayout;
    private ImageView timeIv;
    private SharedPreferences sp;
    private String showdate;
    private String createDate;

    private boolean isToday = true;//是否添加当天的待办事项
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initUI();

        initData();
        initLinstener();
    }


    private void initUI() {
        sp = getSharedPreferences("config", MODE_PRIVATE);

        ButterKnife.bind(this);

     //   addView = (AddView) findViewById(R.id.av);
        addEditText = (EditText) findViewById(R.id.et_add);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        timeIv = (ImageView) findViewById(R.id.iv);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        createDate = sdf.format(new Date());
    }

    private void initData() {
    }

    private void initLinstener() {
        addView.setOnCommitItemLinstener(this);
        timeIv.setOnClickListener(this);
    }

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * 将填写内容添加到待办事项列表中，并将填写内容返回到mainActivity中
     */
    @Override
    public void commit() {
        if (!isToday) {
            showdate = sp.getString("showdate", createDate);
        }else {
            showdate = createDate;
        }
        String item = addEditText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("item", item);
        System.out.println("item="+item);
        intent.putExtra("showdate",showdate);
        System.out.println("showdate="+showdate);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main:
                Toast.makeText(this, "main", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.setting:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        isToday = false;
        DatePickerDialogUtils utils = new DatePickerDialogUtils(this);
        utils.showDatePickerDialog();
    }
}
