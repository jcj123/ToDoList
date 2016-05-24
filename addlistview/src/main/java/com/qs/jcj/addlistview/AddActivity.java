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


public class AddActivity extends AppCompatActivity implements AddView.OnCommitItemLinstener, View.OnClickListener {

    private EditText addEditText;
    @BindView(R.id.av) AddView addView;
    @BindView(R.id.iv) ImageView timeIv;
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

        addEditText = (EditText) findViewById(R.id.et_add);
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
        intent.putExtra("showdate",showdate);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onClick(View v) {
        isToday = false;
        DatePickerDialogUtils utils = new DatePickerDialogUtils(this);
        utils.showDatePickerDialog();
    }
}
