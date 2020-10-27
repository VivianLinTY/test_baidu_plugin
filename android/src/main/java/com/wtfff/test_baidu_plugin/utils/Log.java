package com.wtfff.test_baidu_plugin.utils;

public class Log {
    private static String TAG = "[BaiduPlugin]";

    public static boolean sIsDebug = false;

    public static void d(String tag, String content) {
        if (sIsDebug) {
            android.util.Log.d(TAG + "[" + tag + "] ", content);
        }
    }

    public static void i(String tag, String content) {
        if (sIsDebug) {
            android.util.Log.i(TAG + "[" + tag + "] ", content);
        }
    }

    public static void v(String tag, String content) {
        if (sIsDebug) {
            android.util.Log.v(TAG + "[" + tag + "] ", content);
        }
    }

    public static void w(String tag, String content) {
        android.util.Log.w(TAG + "[" + tag + "] ", content);
    }

    public static void e(String tag, String content) {
        android.util.Log.e(TAG + "[" + tag + "] ", content);
    }

    public static void wtp(String tag, String content) {
        android.util.Log.i(TAG + "[" + tag + "] ", content);
    }
}
