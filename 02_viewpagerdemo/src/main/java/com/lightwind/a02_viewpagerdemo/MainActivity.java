package com.lightwind.a02_viewpagerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lightwind.a02_viewpagerdemo.R.drawable.e;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_point_group)
    LinearLayout mLlPointGroup;

    // 图片资源ID
    private final int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            e};

    // 图片标题集合
    private final String[] imageDescriptions = {
            "尚硅谷波河争霸赛！",
            "凝聚你我，放飞梦想！",
            "抱歉没座位了！",
            "7月就业名单全部曝光！",
            "平均起薪11345元"};

    private ArrayList<ImageView> imageViews;

    // 上一次高亮显示的位置
    private int prePosition = 0;

    // 是否滑动
    private boolean isDragging = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int item = mViewpager.getCurrentItem() + 1;
            mViewpager.setCurrentItem(item);

            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // ViewPager的使用：
        // 1、布局文件中定义ViewPager
        // 2、在代码中实例化ViewPager
        // 3、准备数据
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);

            // 添加到集合中
            imageViews.add(imageView);

            // 添加红
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_select);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);

            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
                params.leftMargin = 8;
                params.bottomMargin = 12;
            }

            point.setLayoutParams(params);

            mLlPointGroup.addView(point);

        }
        // 4、设置适配器（PagerAdapter）-item布局-绑定数据
        mViewpager.setAdapter(new MyPagerAdapter());

        // 设置监听ViewPager页面的改变
        mViewpager.addOnPageChangeListener(new MyOnPagerChangerListener());

        // 设置中间位置
        // 要保证是imageViews的整数倍
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageViews.size();
        mViewpager.setCurrentItem(item);

        // 设置默认文本为初始位置的0
        mTvTitle.setText(imageDescriptions[prePosition]);

        handler.sendEmptyMessageDelayed(0, 3000);
    }

    private class MyOnPagerChangerListener implements ViewPager.OnPageChangeListener {

        /**
         * 当页面滚动的时候回调
         *
         * @param position             当前页面的位置
         * @param positionOffset       滑动页面的百分比
         * @param positionOffsetPixels 在屏幕上滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中的时候回调
         *
         * @param position 被选中的页面的位置
         */
        @Override
        public void onPageSelected(int position) {

            int realPosition = position % imageViews.size();

            // 设置对应页面的文本信息
            mTvTitle.setText(imageDescriptions[realPosition]);
            // 把上一个高亮的设置默认---灰色
            mLlPointGroup.getChildAt(prePosition).setEnabled(false);
            // 把当前的位置高亮---红色
            mLlPointGroup.getChildAt(realPosition).setEnabled(true);

            prePosition = realPosition;
        }

        /**
         * 当页面滚动状态变化的时候回调
         * 静止--->滑动
         * 滑动--->静止
         *
         * @param state 页面的状态
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {// 滑动
                Log.d(TAG, "SCROLL_STATE_DRAGGING---------------------");
                isDragging = true;
                handler.removeCallbacksAndMessages(null);
            } else if (state == ViewPager.SCROLL_STATE_SETTLING) {// 滚动
                Log.d(TAG, "SCROLL_STATE_SETTLING---------------------");
            } else if (state == ViewPager.SCROLL_STATE_IDLE && isDragging) {// 静止
                Log.d(TAG, "SCROLL_STATE_IDLE---------------------");
                isDragging = false;
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(0, 4000);
            }
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        /**
         * 得到图片的总数
         */
        @Override
        public int getCount() {
//            return imageViews.size();
            return Integer.MAX_VALUE;
        }

        /**
         * 相当于ListView中的getView()方法
         *
         * @param container ViewPager自身
         * @param position  当前实例化页面的位置
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int realPosition = position % imageViews.size();

            ImageView imageView = imageViews.get(realPosition);
            container.addView(imageView);// 添加到ViewPager中

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // 按下
                            Log.d(TAG, "onTouch--按下");
                            // 这个方法传入null时，表示将消息队列中的所有消息和callback移除
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            // 移动
                            Log.d(TAG, "onTouch--移动");
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            // 移动
                            Log.d(TAG, "onTouch--取消");
//                            handler.removeCallbacksAndMessages(null);
//                            handler.sendEmptyMessageDelayed(0, 4000);
                            break;
                        case MotionEvent.ACTION_UP:
                            // 离开
                            Log.d(TAG, "onTouch--离开");
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0, 4000);
                            break;
                    }
                    return false;// 触摸事件不消费，留给点击事件消费
                }
            });

            imageView.setTag(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag() % imageViews.size();// 取模，否则将越界
                    String description = imageDescriptions[position];
                    Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();
                }
            });

            return imageView;
        }

        /**
         * 比较view和object是否是同一个实例
         *
         * @param view   页面
         * @param object instantiateItem()方法中返回的结果
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
//            if (view == object) {
//                return true;
//            } else {
//                return false;
//            }
            return view == object;
        }

        /**
         * 释放资源
         *
         * @param container ViewPager
         * @param position  要释放的位置
         * @param object    要释放的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
