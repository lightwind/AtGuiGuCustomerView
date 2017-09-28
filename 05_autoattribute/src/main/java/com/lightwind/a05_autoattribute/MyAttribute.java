package com.lightwind.a05_autoattribute;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 功能：自定义属性
 * 作者：刘洋
 * 时间：2017/9/27
 */

public class MyAttribute extends View {

    private int age;
    private String name;
    private Bitmap bg;

    public MyAttribute(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 获取属性三种方式：
        // 1、用命名空间去获取
        String myAge = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_age");
        String myName = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_name");
        String myBg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_bg");
        Log.d("TAG", "myAge--" + myAge);
        Log.d("TAG", "myName--" + myName);
        Log.d("TAG", "myBg--" + myBg);

        // 2、遍历属性集合
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            Log.d("TAG", attrs.getAttributeName(i) + "==" + attrs.getAttributeValue(i));
        }

        // 3、使用系统工具获取属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyAttribute);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);

            switch (index) {
                case R.styleable.MyAttribute_my_age:
                    age = typedArray.getInt(index, 0);
                    break;
                case R.styleable.MyAttribute_my_name:
                    name = typedArray.getString(index);
                    break;
                case R.styleable.MyAttribute_my_bg:
                    Drawable drawable = typedArray.getDrawable(index);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bg = bitmapDrawable.getBitmap();
                    break;
            }
        }

        // 回收
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawText(name + "---" + age, 50, 50, paint);
        canvas.drawBitmap(bg, 50, 50, paint);
    }
}
