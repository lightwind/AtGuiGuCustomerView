package com.lightwind.a01_youkudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.level3)
    RelativeLayout level3;
    @BindView(R.id.level2)
    RelativeLayout level2;
    @BindView(R.id.level1)
    RelativeLayout level1;

    @BindView(R.id.icon_menu)
    ImageView iconMenu;
    @BindView(R.id.icon_home)
    ImageView iconHome;

    // 是否显示level3
    private boolean isShowLevel3 = true;
    // 是否显示level2
    private boolean isShowLevel2 = true;
    // 是否显示level1
    private boolean isShowLevel1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 设置点击事件
        iconHome.setOnClickListener(this);
        iconMenu.setOnClickListener(this);
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_home:// home
                // 如果三级菜单和二级菜单都显示，就都设置隐藏
                if (isShowLevel2) {
                    // 隐藏二级菜单
                    isShowLevel2 = false;
                    Tools.hideView(level2);
                    if (isShowLevel3) {
                        // 隐藏三级菜单
                        isShowLevel3 = false;
                        Tools.hideView(level3, 200);
                    }
                } else {
                    // 显示二级菜单
                    isShowLevel2 = true;
                    Tools.showView(level2);
                }
                // 如果都是隐藏的，就显示二级菜单
                break;
            case R.id.icon_menu:// 菜单
                if (isShowLevel3) {
                    // 隐藏
                    isShowLevel3 = false;
                    Tools.hideView(level3);
                } else {
                    // 显示
                    isShowLevel3 = true;
                    Tools.showView(level3);
                }
                break;
            case R.id.level1:
                break;
            case R.id.level2:
                break;
            case R.id.level3:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // 如果一级、二级、三级菜单是显示的就全部隐藏
            if (isShowLevel1) {
                isShowLevel1 = false;
                Tools.hideView(level1);
                if (isShowLevel2) {
                    // 隐藏二级菜单
                    isShowLevel2 = false;
                    Tools.hideView(level2, 200);
                    if (isShowLevel3) {
                        // 隐藏三级菜单
                        isShowLevel3 = false;
                        Tools.hideView(level3, 400);
                    }
                }
            } else {
                // 如果一级、二级菜单是隐藏的，就显示
                // 显示一级菜单
                isShowLevel1 = true;
                Tools.showView(level1);
                // 显示二级菜单
                isShowLevel2 = true;
                Tools.showView(level2, 200);
            }
            return true;// 表示消费
        }
        return super.onKeyDown(keyCode, event);
    }
}
