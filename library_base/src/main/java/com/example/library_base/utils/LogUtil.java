package com.example.library_base.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class LogUtil {
    /**
     * 初始化log工具，在app入口处调用
     *
     */
    public static void init() {

      Logger.addLogAdapter(new AndroidLogAdapter());


    }

    public static void d(String message) {
        Logger.d(message);
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void w(String message, Throwable e) {
        String info = e != null ? e.toString() : "null";
        Logger.w(message + "：" + info);
    }

    public static void e(String message, Throwable e) {
        Logger.e(e, message);
    }
    public static void e(String message) {
        Logger.e(message);
    }
    public static void json(String json) {
        Logger.json(json);
    }

}
