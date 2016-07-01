package com.qs.jcj.addlistview.view;

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

    /**
     * 条目状态监听接口
     */
    public interface OnItemStatusLinstener {
        void delete(View foreView);
        //改变foreview的状态，即颜色
        void changeStatus(View foreView);
        void checkStatus(View foreView);
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
                foreView.setBackgroundColor(Color.GREEN);
            }
            return left;
        }
        //当view的位置发生变化时调用
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == foreView) {
                backView.offsetLeftAndRight(dx);
                formerView.offsetLeftAndRight(dx);
            }
            invalidate();
        }
        //当释放view时调用此方法
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (formerView.getLeft() > 0) {
                System.out.println("hhhh");
                if (linstener!=null) {
                    linstener.changeStatus(foreView);
                }
            }
            if (backView.getLeft() < foreWidth || formerView.getLeft()<0) {
                if (linstener != null) {
                    linstener.checkStatus(foreView);
                }
            }
            if (backView.getLeft() < foreWidth - backWidth) {
                System.out.println("delete");
                //在放手时删除条目
                if (linstener != null) {
                    linstener.delete(foreView);
                }
            }
            layoutViewWithAnimation();
        }


    }

    /**
     * 通过属性动画完成弹性滑动的效果
     */
    private void layoutViewWithAnimation() {
        mDragHelper.smoothSlideViewTo(foreView,0,0);
        postInvalidateOnAnimation();
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            postInvalidateOnAnimation();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutView();
    }

    /**
     * 进行layout布局
     */
    private void layoutView() {
        int left = 0;
        foreView.layout(left, 0, left + foreWidth, 0 + mHeight);
        backView.layout(left + foreWidth, 0, left + foreWidth + backWidth, 0 + mHeight);
        formerView.layout(left - formerWidth, 0, left, 0 + mHeight);
    }

    /**
     * 有ViewDragHelper接管触摸事件所进行的配置
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
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
