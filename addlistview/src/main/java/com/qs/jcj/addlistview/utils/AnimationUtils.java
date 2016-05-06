package com.qs.jcj.addlistview.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by jcj on 16/5/3.
 */
public class AnimationUtils {
    /**
     * 点击悬浮的fab按钮后，关闭圆弧形动画，包括执行的各种按钮
     */
    public static void closeCircleAnimation(View view, int index, int total, int radius) {
        double degree = 90 * index / (total - 1);
        final double radians = Math.toRadians(degree);
        final float translationY = (float) (radius * Math.cos(radians));
        final float translationX = (float) (radius * Math.sin(radians));
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", -translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", -translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0));
        //动画周期为 500ms
        set.setDuration(1 * 400).start();
    }

    /**
     * 点击悬浮的fab按钮后，弹出圆弧形动画，包括执行的各种按钮
     */
    public static void openCircleAnimation(View view, int index, int total, int radius) {
        double degree = 90 * index / (total - 1);
        final double radians = Math.toRadians(degree);
        final float translationY = (float) (radius * Math.cos(radians));
        final float translationX = (float) (radius * Math.sin(radians));
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, -translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, -translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为 500ms
        set.setDuration(1 * 400).start();

    }
}
