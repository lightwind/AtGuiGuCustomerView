package com.qqyumidi.event;

public class Log {
    public static void d(String tag, String msg) {
        android.util.Log.e(tag,msg);
    }

    public static void w(String tag, String msg) {
        android.util.Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        android.util.Log.i(tag, msg);
    }

}
