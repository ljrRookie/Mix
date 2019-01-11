package com.example.library_base.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.Toast;


import com.example.library_base.BaseApplication;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by jingbin on 2016/12/14.
 * 单例Toast
 * An Android library to hook and fix Toast BadTokenException
 */

public class ToastUtil {

    private static ToastCompat mToast;

    @SuppressLint("ShowToast")
    public static void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = ToastCompat.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.cancel();
                mToast = ToastCompat.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT);
            }
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
            mToast.show();
        }
    }

    @SuppressLint("ShowToast")
    public static void showToastLong(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = ToastCompat.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_LONG);
            } else {
                mToast.cancel();
                mToast = ToastCompat.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_LONG);
            }
            mToast.setDuration(Toast.LENGTH_LONG);
            mToast.setText(text);
            mToast.show();
        }
    }

}
