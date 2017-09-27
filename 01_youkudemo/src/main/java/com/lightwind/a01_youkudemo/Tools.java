package com.lightwind.a01_youkudemo;

import android.animation.ObjectAnimator;
import android.view.ViewGroup;

/**
 * 功能：显示和隐藏指定控件
 * 作者：刘洋
 * 时间：2017/9/26
 */
public class Tools {

    /**
     * 隐藏的方法
     */
    public static void hideView(ViewGroup view) {
        hideView(view, 0);
    }

    /**
     * 显示的方法
     */
    public static void showView(ViewGroup view) {
        showView(view, 0);
    }

    /**
     * 延迟隐藏的方法
     */
    public static void hideView(ViewGroup view, int startOffset) {
        // ---------------------使用视图动画--------------------------
//        RotateAnimation ra = new RotateAnimation(0, 180, view.getWidth() / 2, view.getHeight());
//        ra.setDuration(500);// 设置动画的播放持续的时间
//        ra.setFillAfter(true);// 动画停留在播放完成的状态
//        ra.setStartOffset(startOffset);// 延迟多久后播放动画
//        view.startAnimation(ra);
//
//        // 设置不可以点击
//        // ViewGroup，得到里面的每一个View，将里面的View的setEnabled设置为false
//        for (int i = 0; i < view.getChildCount(); i++) {
//            view.getChildAt(i).setEnabled(false);
//        }

        // ---------------------使用属性动画--------------------------
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 180);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();

        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
    }

    /**
     * 延迟显示的方法
     */
    public static void showView(ViewGroup view, int startOffset) {
        // ---------------------使用视图动画--------------------------
//        RotateAnimation ra = new RotateAnimation(180, 360, view.getWidth() / 2, view.getHeight());
//        ra.setDuration(500);// 设置动画的播放持续的时间
//        ra.setFillAfter(true);// 动画停留在播放完成的状态
//        ra.setStartOffset(startOffset);
//        view.startAnimation(ra);
//
//        for (int i = 0; i < view.getChildCount(); i++) {
//            view.getChildAt(i).setEnabled(true);
//        }

        // ---------------------使用属性动画--------------------------
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 180, 360);
        animator.setDuration(500);
        animator.setStartDelay(startOffset);
        animator.start();

        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight());
    }
}
