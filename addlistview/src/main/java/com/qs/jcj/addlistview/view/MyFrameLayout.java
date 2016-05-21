package com.qs.jcj.addlistview.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qs.jcj.addlistview.R;

import java.io.InputStream;

/**
 * Created by jcj on 16/5/5.
 */
public class MyFrameLayout extends FrameLayout {

    private int measuredHeight;
    private MarginLayoutParams layoutParams;
    private int downY;
    private View view;

    private VelocityTracker velocityTracker;
    public MyFrameLayout(Context context) {
        this(context,null);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }


    private void initUI() {
        velocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        velocityTracker.addMovement(ev);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getRawY();
                System.out.println("downY="+downY);
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.computeCurrentVelocity(1000);
                final float yVelocity = velocityTracker.getYVelocity();
                System.out.println("yVelocity i="+yVelocity);
                int moveY = (int) ev.getRawY();
                int detalY = moveY - downY;
                if (Math.abs(yVelocity)>7000) {
                    return true;
                }else {
                    return super.onInterceptTouchEvent(ev);
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getRawY();
                System.out.println("downY="+downY);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                int detalY = moveY - downY;
                System.out.println("detalY="+detalY);
                layoutParams.bottomMargin = detalY;
                view.requestLayout();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = getChildAt(1);
        System.out.println(view);
        layoutParams = (MarginLayoutParams) view.getLayoutParams();
        System.out.println("layoutParams="+layoutParams);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measuredHeight = getMeasuredHeight();
    }
}
