package com.example.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @class describe:描述
 * @anthor chenyx
 * @time 2018/04/25 17:53
 * @chang 2018/04/25 17:53
 */
public class AppBitmapUtil {

    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 保存图片
     *
     * @param oldbitmap
     * @param
     * @return
     */
    public static boolean saveBitmap(Bitmap oldbitmap, String packageUrl, String bitmapPath) {

        try {
            File file = new File(packageUrl);
            if (!file.exists()) {
                file.mkdirs();
            }
            final FileOutputStream fileout = new FileOutputStream(bitmapPath);
            oldbitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileout);
            fileout.flush();
            fileout.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.gc();
            return false;
        }
    }


}
