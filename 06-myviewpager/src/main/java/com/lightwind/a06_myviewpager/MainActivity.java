package com.lightwind.a06_myviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.myViewPager)
    MyViewPager mMyViewPager;
    @BindView(R.id.rg_main)
    RadioGroup mRgMain;

    private int[] ids = {R.drawable.a1, R.drawable.a2, R.drawable.a3,
            R.drawable.a4, R.drawable.a5, R.drawable.a6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 添加页面
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            // 添加到MyViewPager中
            mMyViewPager.addView(imageView);
        }

        // 添加测试页面
        View testView = View.inflate(this, R.layout.test, null);

        mMyViewPager.addView(testView, 2);

        for (int i = 0; i < mMyViewPager.getChildCount(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);// 0-5

            if (i == 0) {
                radioButton.setChecked(true);
            }

            // 添加到RadioGroup
            mRgMain.addView(radioButton);
        }

        // 设置RadioGroup选中状态的变化
        mRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * @param group
             * @param checkedId 0-5之间
             */
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                mMyViewPager.scrollToPager(checkedId);// 根据下标的位置定位到具体的某个页面
            }
        });

        // 设置监听页面的改变
        mMyViewPager.setOnPagerChangListener(new MyViewPager.OnPagerChangListener() {
            @Override
            public void onScrollToPager(int position) {
                mRgMain.check(position);
            }
        });
    }
}
