package com.qs.jcj.addlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qs.jcj.addlistview.view.AddView;

public class AddActivity extends AppCompatActivity implements AddView.OnCommitItemLinstener {

    private EditText addEditText;
    private AddView addView;

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
}
