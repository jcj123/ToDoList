package com.qs.jcj.addlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qs.jcj.addlistview.view.AddView;


public class AddActivity extends AppCompatActivity implements AddView.OnCommitItemLinstener, NavigationView.OnNavigationItemSelectedListener {

    private EditText addEditText;
    private AddView addView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initUI();
        initData();
        initLinstener();
    }


    private void initUI() {
        addView = (AddView) findViewById(R.id.av);
        addEditText = (EditText) findViewById(R.id.et_add);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initData() {
    }

    private void initLinstener() {
        addView.setOnCommitItemLinstener(this);

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
     * 将填写内容添加到待办事项列表中
     */
    @Override
    public void commit() {
        String item = addEditText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("item", item);
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
}
