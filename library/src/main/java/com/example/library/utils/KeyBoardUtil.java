package com.example.library.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author chenyx
 * @date create 2017/9/11
 * @description 键盘开关
 */
public class KeyBoardUtil {
    private static InputMethodManager imm;

    /**
     * 关闭键盘
     */
    public static void closeKeyBoard(Activity activity) {
        try {
            if (imm == null) {
                imm = (InputMethodManager) activity
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    /**
     * 开启键盘
     */
    public static void openKeyBoard(Context context) {
        if (imm == null) {
            imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 开启键盘
     */
    public static void openKeyBoard(View view) {
        if (imm == null) {
            imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.restartInput(view);
    }
}
