package com.zxsoft.example.model.http;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONObject;

/**
 * OKGO2次封装
 * chenyx
 */
public class OkGoHttpUtil {

    public static void post(String url, JSONObject object, int hash, AbsCallback callback) {
        OkGo.post(url)
                .tag(hash)
                .upJson(object)
                .execute(callback);
    }

    public static void postStrig(String url, String object, int hash, AbsCallback callback) {
        OkGo.post(url)
                .tag(hash)
                .upString(object)
                .execute(callback);
    }

    public static void get(String url, int hash, AbsCallback callback) {
        OkGo.get(url)
                .tag(hash)
                .execute(callback);
    }

    public static void delete(String url, int hash, AbsCallback callback) {
        OkGo.delete(url)
                .tag(hash)
                .execute(callback);
    }


    public static void get(String url, HttpParams params, int hash, AbsCallback callback) {
        OkGo.get(url)
                .tag(hash)
                .params(params)
                .execute(callback);
    }

    public static void post(String url, HttpParams params, int hash, AbsCallback callback) {
        OkGo.post(url)
                .tag(hash)
                .params(params)
                .execute(callback);
    }

    public static void postIsMutil(String url, HttpParams params, int hash, AbsCallback callback) {
        OkGo.post(url)
                .tag(hash)
                .isMultipart(true)
                .params(params)
                .execute(callback);
    }

    public static void put(String url, JSONObject object, int hash, AbsCallback callback) {
        OkGo.put(url)
                .tag(hash)
                .upJson(object)
                .execute(callback);
    }


}
