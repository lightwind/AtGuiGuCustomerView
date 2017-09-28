package com.lightwind.a06_myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * 功能：自定义ViewPager
 * 作者：刘洋
 * 时间：2017/9/27
 */

public class MyViewPager extends ViewGroup {

    /**
     * 手势识别器
     * 1.定义出来
     * 2.实例化-把想要的方法给重新
     * 3.在onTouchEvent()把事件传递给手势识别器
     */
    private GestureDetector detector;
    /**
     * 当前页面的下标位置
     */
    private int currentIndex;

    private Scroller scroller;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        scroller = new Scroller(context);
        //2.实例化手势识别器
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(context, "长按", Toast.LENGTH_SHORT).show();
            }

            /**
             * @param e1
             * @param e2
             * @param distanceX 在X轴滑动了的距离
             * @param distanceY 在Y轴滑动了的距离
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                /**
                 *x:要在X轴移动的距离
                 *y:要在Y轴移动的距离
                 */
                scrollBy((int) distanceX, 0);

                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(context, "双击", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }
        });
    }

    /**
     * @param l 图片左上角水平上距离屏幕左边的值
     * @param t 图片左上角竖直上距离屏幕上边的值
     * @param r 图片右下角水平上距离屏幕左边的值
     * @param b 图片右下角竖直上距离屏幕上边的值
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //遍历孩子，给每个孩子指定在屏幕的坐标位置
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            childView.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }

    }

    private float startX;

    private float downX;
    private float downY;

    /**
     * 如果当前方法，返回true,拦截事件，将会触发当前控件的onTouchEvent()方法
     * 如果当前方法,返回false,不拦截事件，事件继续传递给孩子
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        boolean result = false;//默认传递给孩子

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("onInterceptTouchEvent==ACTION_DOWN");
                //1.记录坐标
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("onInterceptTouchEvent==ACTION_MOVE");
                //2.记录结束值
                float endX = ev.getX();
                float endY = ev.getY();

                //3.计算绝对值
                float distanceX = Math.abs(endX - downX);
                float distanceY = Math.abs(endY - downY);

                if (distanceX > distanceY && distanceX > 5) {
                    result = true;
                } else {
                    scrollToPager(currentIndex);
                }

                break;
            case MotionEvent.ACTION_UP:
                System.out.println("onInterceptTouchEvent==ACTION_UP");

                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //3.把事件传递给手势识别器
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1.记录坐标
                startX = event.getX();
                System.out.println("onTouchEvent==ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("onTouchEvent==ACTION_MOVE");

                break;
            case MotionEvent.ACTION_UP:
                System.out.println("onTouchEvent==ACTION_UP");

                //2.来到新的坐标
                float endX = event.getX();

                //下标位置
                int tempIndex = currentIndex;

                if ((startX - endX) > getWidth() / 2) {

                    //显示下一个页面
                    tempIndex++;

                } else if ((endX - startX) > getWidth() / 2) {

                    //显示上一个页面
                    tempIndex--;
                }

                //根据下标位置移动到指定的页面
                scrollToPager(tempIndex);

                break;
        }
        return true;
    }

    /**
     * 屏蔽非法值，根据位置移动到指定页面
     */
    public void scrollToPager(int tempIndex) {

        if (tempIndex < 0) {
            tempIndex = 0;
        }

        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        //当前页面的下标位置
        currentIndex = tempIndex;

        if (mOnPagerChangListener != null) {
//            mOnPagerChangListener ==MyOnPagerChangListener;
            //MyOnPagerChangListener 里面有一个方法，onScrollToPager(int)
            mOnPagerChangListener.onScrollToPager(currentIndex);
        }

        //总距离计算出来
        int distanceX = currentIndex * getWidth() - getScrollX();
        // int distanceX = 目标 - getScrollX();
        //移动到指定的位置
//        scrollTo(currentIndex*getWidth(),getScrollY());
//        scroller.startScroll(getScrollX(),getScrollY(),distanceX,0);

        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0, Math.abs(distanceX));

        //
        invalidate();//强制绘制;//onDraw();computeScroll();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if (scroller.computeScrollOffset()) {

            //得到移动这个一小段对应的坐标
            float currX = scroller.getCurrX();

            scrollTo((int) currX, 0);

            invalidate();
        }
    }

    /**
     * 监听页面的改变
     */
    public interface OnPagerChangListener {

        /**
         * 当页面改变的时候回调这个方法
         *
         * @param position 当前页面的下标
         */
        void onScrollToPager(int position);
    }

    private OnPagerChangListener mOnPagerChangListener;

    /**
     * 设置页面改变的监听
     */
    public void setOnPagerChangListener(OnPagerChangListener l) {
        //当前的 mOnPagerChangListener==MyOnPagerChangListener；
        mOnPagerChangListener = l;
    }

    /**
     * 测量一级页面（7个页面）的子View
     * 1、测量的时候需要测量多次
     * 2、widthMeasureSpec时候父层视图给当前视图的宽和模式
     * <p>
     * MeasureSpec的模式有三种，用于传递给子View：
     * UNSPECIFIED：父容器不对view有任何限制，要多大给多大
     * EXACTLY：父容器已经检测出view需要的的大小
     * AT_MOST：父容器指定大小，view的大小不能大于这个值
     * <p>
     * 系统的onMeasure()所做的事情：
     * 1、根据widthMeasureSpec求得宽度width，和父View给的模式
     * 2、根自身的宽度width和自身的padding值，相减，求得子view可以拥有的宽度newWidth
     * 3、根据newWidth和模式求得一个新的MeasureSpec值：
     * MeasureSpec.makeMeasureSpec(newSize,mode)
     * 4、用新的MeasureSpec来计算子view
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int newSize = MeasureSpec.makeMeasureSpec(size, mode);
        System.out.println("widthMeasureSpec==" + widthMeasureSpec + "size==" + size + ",mode==" + mode);
        System.out.println("widthMeasureSpec==" + widthMeasureSpec + "sizeHeight==" + sizeHeight + "," +
                "modeHeight==" + modeHeight);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}
