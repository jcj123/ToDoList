package com.qs.jcj.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager splashViewPager;
    private int[] imageResoureces = new int[]{R.mipmap.splash1, R.mipmap.splash2,
            R.mipmap.splash3, R.mipmap.splash4, R.mipmap.splash5
            , R.mipmap.splash6};
    private SplashPagerAdapter splashPagerAdapter;
    private LinearLayout splashLinearLayout;
    private ImageView redDot;
    private int dotWidth;//两个引导圆的距离
    private Button beginButton;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initData();
        initListener();
    }


    private void initUI() {
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences("config", MODE_PRIVATE);

        if (sp.getBoolean("is_guide_ended",false)) {
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        splashViewPager = (ViewPager) findViewById(R.id.vp_splash);
        splashLinearLayout = (LinearLayout) findViewById(R.id.ll_dots);
        redDot = (ImageView) findViewById(R.id.red_dot);
        beginButton = (Button) findViewById(R.id.bt_begin);
        initdots();
    }

    private void initdots() {
        for (int i = 0; i < imageResoureces.length; i++) {
            View dot = new View(this);
            dot.setBackgroundResource(R.drawable.dot);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            if (i > 0) {
                params.leftMargin = 10;
            }
            dot.setLayoutParams(params);
            splashLinearLayout.addView(dot);
        }
    }

    private void initData() {

        if (splashPagerAdapter == null) {
            splashPagerAdapter = new SplashPagerAdapter();
        }
        splashViewPager.setAdapter(splashPagerAdapter);
        splashLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                dotWidth = splashLinearLayout.getChildAt(1).getLeft() - splashLinearLayout.getChildAt(0).getLeft();
                splashLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initListener() {
        splashViewPager.addOnPageChangeListener(this);
        beginButton.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float moveDistance = position * dotWidth + positionOffset * dotWidth;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redDot.getLayoutParams();
        params.leftMargin = (int) moveDistance;
        redDot.setLayoutParams(params);

    }

    @Override
    public void onPageSelected(int position) {
        if (position == imageResoureces.length - 1) {
            beginButton.setVisibility(View.VISIBLE);
        } else {
            beginButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        sp.edit().putBoolean("is_guide_ended", true).commit();
        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class SplashPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResoureces.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setImageResource(imageResoureces[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
