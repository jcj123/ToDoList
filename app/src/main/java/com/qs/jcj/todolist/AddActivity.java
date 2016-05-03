package com.qs.jcj.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private Button addButton;
    private EditText addEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initUI();
        initData();
    }

    private void initUI() {
        addButton = (Button) findViewById(R.id.bt_add);
        addEditText = (EditText) findViewById(R.id.et_add);
    }
    private void initData() {

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = addEditText.getText().toString();
                System.out.println("item="+item);
                Intent intent = new Intent();
                intent.putExtra("item",item);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    /**
     * 监听返回键
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
