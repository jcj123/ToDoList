package com.qs.jcj.todolist.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

/**
 * Created by jcj on 16/4/22.
 */
public class ItemView extends FrameLayout {

    private View foreView;
    private View backView;
    private int mHeight;
    private int foreWidth;
    private int backWidth;

    private boolean isItemCompleted;//此条目任务是否完成
    private ViewDragHelper mDragHelper;
    private View formerView;
    private int formerWidth;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this, new MyCallBack());
    }

    private OnItemStatusLinstener linstener;

    public void setOnItemStatusLinstener(OnItemStatusLinstener linstener) {
        this.linstener = linstener;
    }

    public interface OnItemStatusLinstener {
        void delete(View foreView, boolean isItemCompleted);

        void cancelCompleted(View foreView, boolean isItemCompleted);

        void completed(View foreView, boolean isItemCompleted);
    }

    class MyCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }
        @Override
        public int getViewHorizontalDragRange(View child) {
            // 返回拖拽的范围, 不对拖拽进行真正的限制. 仅仅决定了动画执行速度
            return (int) (foreWidth*0.6);
        }
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (backView.getLeft() < foreWidth - backWidth) {
                //在拉拽过程中将条目颜色变为灰色
                foreView.setBackgroundColor(Color.GRAY);
            }
            if (formerView.getLeft() > 0) {
                foreView.setBackgroundColor(Color.BLUE);
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == foreView) {
                backView.offsetLeftAndRight(dx);
                formerView.offsetLeftAndRight(dx);
            }
            invalidate();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (isItemCompleted) {
                //已经完成条目，再拉状态变为未完成，颜色变红
                if (formerView.getLeft() > 0) {
                    if (linstener != null) {
                        linstener.cancelCompleted(foreView, isItemCompleted);
                    }
                    isItemCompleted = false;

                } else {
                    foreView.setBackgroundColor(Color.GRAY);
                }
            } else {
                //未完成条目，再拉状态变为已完成，颜色变灰
                if (formerView.getLeft() > 0) {
                    if (linstener != null) {
                        linstener.completed(foreView, isItemCompleted);
                    }
                    isItemCompleted = true;

                } else {
                    foreView.setBackgroundColor(Color.rgb(255, 95, 90));
                }
            }

            if (backView.getLeft() < foreWidth - backWidth) {
                //在放手时删除条目
                if (linstener != null) {
                    linstener.delete(foreView, isItemCompleted);
                }
            } else if (foreView.getLeft() < 0 && backView.getLeft() > foreWidth - backWidth) {
                foreView.setBackgroundColor(Color.rgb(255, 95, 90));
            }
            layoutViewWithAnimation();
        }


    }

    private void layoutViewWithAnimation() {
        int left = 0;

        ValueAnimator animator = ValueAnimator.ofInt(foreView.getLeft(),0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                foreView.layout(value,0,value+foreWidth,0+mHeight);
            }
        });
        animator.setDuration(100);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        backView.layout(left + foreWidth, 0, left + foreWidth + backWidth, 0 + mHeight);
        formerView.layout(left - formerWidth, 0, left, 0 + mHeight);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutView();
    }

    private void layoutView() {
        int left = 0;
        foreView.layout(left, 0, left + foreWidth, 0 + mHeight);
        backView.layout(left + foreWidth, 0, left + foreWidth + backWidth, 0 + mHeight);
        formerView.layout(left - formerWidth, 0, left, 0 + mHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 当所有孩纸全部添加到此view中时调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        foreView = getChildAt(0);
        backView = getChildAt(1);
        formerView = getChildAt(2);
    }

    /**
     * 此方法执行时可以调用getMeasureHeight();
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight = foreView.getMeasuredHeight();
        foreWidth = foreView.getMeasuredWidth();

        backWidth = backView.getMeasuredWidth();
        formerWidth = formerView.getMeasuredWidth();
    }
}
