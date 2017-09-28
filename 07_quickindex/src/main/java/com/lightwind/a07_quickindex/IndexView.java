package com.lightwind.a07_quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 功能：自定义右边的索引
 * 作者：刘洋
 * 时间：2017/9/28
 * <p>
 * 绘制快速索引的26个字母
 * 1、将26个字母放到数组中
 * 2、在onMeasure()中计算每条的高itemWidth和宽itemHeight
 * 3、在onDraw()中计算wordWidth，wordHeight，wordX，wordY
 * <p>
 * 手指按下文字变色
 * 1、重写onTouchEvent()方法，返回true，在down和move中计算
 * ---int touchIndex = Y / itemHeight
 * ---// 强制绘制
 * 2、在onDraw()方法对应的下标设置画笔变色
 * 3、在up的时候，
 * ---touchIndex = -1
 * ---强制绘制
 */

public class IndexView extends View {

    // 每条的宽和高
    private int itemWidth;
    private int itemHeight;

    private Paint paint;

    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "v", "W", "X", "Y", "Z"};

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT_BOLD);// 设置粗体
    }

    /**
     * 测量宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        itemHeight = getMeasuredHeight() / words.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < words.length; i++) {

            if (touchIndex == i) {
                // 设置为灰色
                paint.setColor(Color.GRAY);
            } else {
                // 设置为白色
                paint.setColor(Color.WHITE);
            }

            String word = words[i];// A

            Rect rect = new Rect();
            // 画笔
            // 0-1表示取一个字母
            paint.getTextBounds(word, 0, 1, rect);

            // 字母的宽和高
            int wordWidth = rect.width();
            int wordHeight = rect.height();

            paint.setTextSize(25);

            // 计算每个字母在视图上的坐标位置
            float wordX = itemWidth / 2 - wordWidth / 2;
            float wordY = itemHeight / 2 + wordHeight / 2 + i * itemHeight;

            canvas.drawText(word, wordX, wordY, paint);
        }
    }

    // 字母的下标位置
    private int touchIndex = 1;

    /**
     * 手指按下文字变色
     * 1、重写onTouchEvent()方法，返回true，在down和move中计算
     * ---int touchIndex = Y / itemHeight
     * ---// 强制绘制
     * 2、在onDraw()方法对应的下标设置画笔变色
     * 3、在up的时候，
     * ---touchIndex = -1
     * ---强制绘制
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float Y = event.getY();
                int index = (int) (Y / itemHeight);// 得到字母索引
                if (index != touchIndex) {
                    touchIndex = index;
                    invalidate();// 强制绘制onDraw()

                    if (onIndexChangerListener != null && touchIndex < words.length) {
                        onIndexChangerListener.onIndexChanger(words[touchIndex]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 字母下标索引发生变化的监听器
     */
    public interface OnIndexChangerListener {
        /**
         * 当字母下标位置发生变化的时候回调
         *
         * @param word 字母（A~Z）
         */
        void onIndexChanger(String word);
    }

    private OnIndexChangerListener onIndexChangerListener;

    /**
     * 设置字母下标索引变化的监听
     */
    public void setOnIndexChangerListener(OnIndexChangerListener onIndexChangerListener) {
        this.onIndexChangerListener = onIndexChangerListener;
    }
}
