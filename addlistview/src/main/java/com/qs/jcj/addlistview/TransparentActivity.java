package com.qs.jcj.addlistview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.qs.jcj.addlistview.R;

public class TransparentActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        rl = (RelativeLayout) findViewById(R.id.rl);
        rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        finish();
        //同时关闭fab菜单
       // new MainActivity().closeMenu();
    }
}
