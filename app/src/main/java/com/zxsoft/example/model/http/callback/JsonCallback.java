package com.zxsoft.example.model.http.callback;

import android.content.Context;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Response;

/**
 * @author chenyx
 * @date create 2017/7/24
 * @description
 */
public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Type type;
    private Class<T> clazz;
    protected String msg;
    private Context mContext;

    private static final String SP_TOKEN = "sp_token";
    private static final String SP_UPHONE = "sp_uphone";
    private static final String SP_PWD = "sp_pwd";

    public JsonCallback(Context context) {
        this.mContext = context;
    }

    public JsonCallback(Type type) {
        this.type = type;
    }

    public JsonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现

//        request.headers("header1", "HeaderValue1")//
//                .params("params1", "ParamsValue1")//
//                .params("token", "3215sdf13ad1f65asd4f3ads1f");

        request.headers("header1", "HeaderValue1");
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用

        //详细自定义的原理和文档，看这里： https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback

        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                JsonConvert<T> convert = new JsonConvert<>(clazz);
                return convert.convertResponse(response);
            }
        }

        JsonConvert<T> convert = new JsonConvert<>(type);
        return convert.convertResponse(response);
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);

        Throwable throwable = response.getException();

        int code = response.code();
        if (throwable != null) {
            throwable.printStackTrace();
        }

        if (throwable instanceof UnknownHostException || throwable instanceof ConnectException) {
            msg = "网络连接失败，请连接网络！";
        } else if (throwable instanceof SocketTimeoutException) {
            msg = "网络连接超时";
        } else if (throwable instanceof IllegalStateException) {
            msg = null;
        }
    }

//    public String getToken() {
//        String userToken = (String) AppSharePreferenceMgr.get(mContext, SP_TOKEN, "");
//        String uPhone = (String) AppSharePreferenceMgr.get(mContext, SP_UPHONE, "");
//        return MD5Utils.encryptMD5(uPhone + userToken);
//    }
}
