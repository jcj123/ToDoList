package com.qs.jcj.addlistview.view;

import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qs.jcj.addlistview.R;

/**
 * Created by jcj on 16/4/28.
 * 自定义下拉添加view
 */
public class AddView extends LinearLayout {
    private final static int RELEASE_ADD = 0;//释放添加状态
    private final static int PULL_ADD = 1;//继续下拉添加状态

    private static int currentStatus = PULL_ADD;
    private View headView;
    private int headerHeight;
    private TextView addTextView;
    /**
     * 是否已加载果layout，这里onLayout()初始化只需加载一次
     */
    private boolean loadOnce;
    private MarginLayoutParams params;
    private float downY;
    private ImageView imageView;
    private float detalY;
    private TextView tv;

    public AddView(Context context) {
        this(context, null);
    }

    public AddView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        headView = View.inflate(getContext(), R.layout.header_view, null);
        addTextView = (TextView) headView.findViewById(R.id.tv_add);
        headView.measure(0, 0);
        headerHeight = headView.getMeasuredHeight();
      //  ViewConfiguration.get(getContext()).getScaledTouchSlop();
        addView(headView, 0);
        setOrientation(VERTICAL);
    }

    /**
     * 当所有子视图加载完成时调用此方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = (ImageView) findViewById(R.id.iv);
        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed && !loadOnce) {
            params = (MarginLayoutParams) headView.getLayoutParams();
            params.topMargin = -headerHeight;
            headView.setLayoutParams(params);
            loadOnce = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY =  event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY =  event.getRawY();
                detalY = moveY - downY;
                if (detalY > 0) {
                    float topMargin = -headerHeight + detalY / 2;
                    params.topMargin = (int) topMargin;
                    headView.setLayoutParams(params);
                    float percent = detalY/headerHeight/2;
                    animAddText(percent);

                    if (currentStatus == PULL_ADD && topMargin >= 0) {
                        currentStatus = RELEASE_ADD;
                        refreshView();
                    } else if (currentStatus == RELEASE_ADD && topMargin < 0) {
                        currentStatus = PULL_ADD;
                        refreshView();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentStatus == RELEASE_ADD) {
                    //将输入条目进行提交
                    if (linstener != null) {
                        linstener.commit();
                    }
                }
                params.topMargin = -headerHeight;
                headView.setLayoutParams(params);
                currentStatus = PULL_ADD;
                break;
        }
        return true;
    }

    private void refreshView() {
        addTextView.setText(currentStatus == PULL_ADD ? "继续下拉提交增加条目" : "释放进行提交");
        beginAnimation();
    }


    private void beginAnimation() {
    //    ObjectAnimator animator = getAddTextAnimator();

        ObjectAnimator iv_animator =  getImageAnimator();
  //      ObjectAnimator tv_animator = getTextAnimator();

        AnimatorSet set = new AnimatorSet();
        set.playTogether( iv_animator);
        set.setDuration(400);
        set.start();
    }

    /**
     * 文字的动画
     * @return
     */
//    @NonNull
//    private ObjectAnimator getTextAnimator() {
//        ObjectAnimator tv_animator = ObjectAnimator.ofFloat(tv,"translationY",0,300);
//        tv_animator.setInterpolator(new AccelerateInterpolator());
//        return tv_animator;
//    }

    /**
     * 图片的动画
     * @return
     */
    private ObjectAnimator getImageAnimator() {
        Keyframe frame = Keyframe.ofFloat(0f, 0f);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, 20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 0f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 0f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, 20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 0f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 0f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, 20f);
        Keyframe frame10 = Keyframe.ofFloat(1.0f, 0f);

        PropertyValuesHolder holder1 = PropertyValuesHolder.ofKeyframe("rotation", frame, frame1, frame2
                , frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);
        /**
         * scaleX放大1.1倍
         */
        Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleXframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleXframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleXframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleXframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleXframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofKeyframe("ScaleX", scaleXframe0, scaleXframe1, scaleXframe2, scaleXframe3, scaleXframe4,
                scaleXframe5, scaleXframe6, scaleXframe7, scaleXframe8, scaleXframe9, scaleXframe10);

        /**
         * scaleY放大1.1倍
         */
        Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleYframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleYframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleYframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleYframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleYframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleYframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleYframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleYframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleYframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofKeyframe("ScaleY", scaleYframe0, scaleYframe1, scaleYframe2, scaleYframe3, scaleYframe4,
                scaleYframe5, scaleYframe6, scaleYframe7, scaleYframe8, scaleYframe9, scaleYframe10);

        return ObjectAnimator.ofPropertyValuesHolder(imageView, holder1, holder2, holder3);
    }

    /**
     * 文字的伴随动画
     * @param percent
     */
    private void animAddText(float percent) {
        addTextView.setScaleX(0.5f+0.5f*percent);
        addTextView.setScaleY(0.5f+0.5f*percent);
        addTextView.setAlpha(evaluate(percent,0f,1.0f));

        tv.setTranslationY(0f+100*percent);
        tv.setScaleX(0.5f+0.5f*percent);
        tv.setScaleY(0.5f+0.5f*percent);
        tv.setAlpha(evaluate(percent,0f,1.0f));
    }
    /**
     * 估值器
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }
    private OnCommitItemLinstener linstener;

    public void setOnCommitItemLinstener(OnCommitItemLinstener linstener) {
        this.linstener = linstener;
    }

    public interface OnCommitItemLinstener {
        void commit();
    }
}
