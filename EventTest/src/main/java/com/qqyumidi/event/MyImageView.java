package com.qqyumidi.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 功能：
 * 作者：刘洋
 * 时间：2017/9/28
 */

public class MyImageView extends ImageView {

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 分发事件
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("eventTest", "MyImageView | dispatchTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction
                ()));
        return super.dispatchTouchEvent(ev);
//		return true;
    }

    /**
     * 触摸事件
     */
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d("eventTest", "MyImageView | onTouchEvent --> " + TouchEventUtil.getTouchAction(ev.getAction()));
//        return super.onTouchEvent(ev);
		return true;
    }

}
