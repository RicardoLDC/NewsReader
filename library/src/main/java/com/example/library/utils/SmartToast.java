package com.example.library.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * @author chenyx
 * @date create 2017/9/11
 * @description
 */
public class SmartToast {
    private static Toast toast;
    private static Toast toastcenter;

    public static void show(Context context, String string) {

        try {
            if (string != null & string.trim().equals(""))
                return;
            string = checkToasString(context, string);
            if (toast == null) {
                toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
            }
            toast.setText(string);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void show(Context context, int stringId) {

        try {
            if (stringId == 0)
                return;
            String string = checkToasString(context, context.getString(stringId));
            if (toast == null) {
                toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
            }
            toast.setText(string);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCenter(Context context, String string) {
        if (string.trim().equals(""))
            return;
        string = checkToasString(context, string);
        try {
            if (toastcenter == null) {
                toastcenter = Toast.makeText(context, string, Toast.LENGTH_SHORT);
                toastcenter.setGravity(Gravity.CENTER, 0, -50);
            }
            toastcenter.setText(string);
            toastcenter.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String checkToasString(Context context, String str) {

        String temp = str.toLowerCase();
        if (temp.contains("timeout")) {
            temp = "网络请求超时,请重试";
        } else if (temp.contains("connect") || temp.contains("host")) {
            temp = "连接服务器失败,请稍后重试";
        }
        return temp;
    }
}
