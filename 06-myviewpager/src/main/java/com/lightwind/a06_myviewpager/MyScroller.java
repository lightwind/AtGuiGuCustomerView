package com.lightwind.a06_myviewpager;

import android.os.SystemClock;

/**
 * 功能：
 * 作者：刘洋
 * 时间：2017/9/27
 */

public class MyScroller {

    // 起始坐标
    private float startX;
    private float startY;

    // 移动的距离
    private int distanceX;
    private int distanceY;

    // 开始的时间
    private long startTime;

    // 是否移动完成：false表示还没有开始移动，true表示移动结束
    private boolean isFinish;

    // 总时间
    private long totalTime = 500;

    private float currentX;

    /**
     * 得到坐标
     */
    public float getCurrentX() {
        return currentX;
    }

    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis();// 系统的开机时间
        this.isFinish = false;// 是否移动完成
    }

    /**
     * 总时间为500
     * 速度
     * 求移动一小段的距离
     * 求移动一小段对应的坐标
     * 求移动一小段对应的时间
     *
     * @return true：正在移动；false：移动结束
     */
    public boolean computeScrollOffSet() {

        if (isFinish) {
            return false;
        }

        long endTime = SystemClock.uptimeMillis();
        // 移动一小段对应的时间
        long passTime = endTime - startTime;
        if (passTime < totalTime) {
            // 还没有移动结束
            // 计算平均速度
//            float voleCity = distanceX / totalTime;
            // 移动一小段的距离
            float distanceSmallX = passTime * (distanceX / totalTime);

            // 移动一小段后的X坐标
            currentX = startX + distanceSmallX;

        } else {
            // 移动结束
            isFinish = true;
            currentX = startX + distanceX;
        }
        return true;
    }
}
