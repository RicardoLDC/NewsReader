package com.zxsoft.example.model.http.callback;

import com.zxsoft.example.model.http.OssResponse;
import com.zxsoft.example.model.http.SimpleResponse;
import com.zxsoft.example.model.http.SmartResponse;
import com.zxsoft.example.util.Convert;
import com.example.library.utils.SmartLog;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.convert.Converter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author chenyx
 * @date create 2017/7/24
 * @description
 */
public class JsonConvert<T> implements Converter<T> {

    private Type type;
    private Class<T> clazz;

    public JsonConvert() {
    }

    public JsonConvert(Type type) {
        this.type = type;
    }

    public JsonConvert(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象，生成onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用

        // 如果你对这里的代码原理不清楚，可以看这里的详细原理说明: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
        // 如果你对这里的代码原理不清楚，可以看这里的详细原理说明: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback
        // 如果你对这里的代码原理不清楚，可以看这里的详细原理说明: https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback

        if (type == null) {
            if (clazz == null) {
                // 如果没有通过构造函数传进来，就自动解析父类泛型的真实类型（有局限性，继承后就无法解析到）
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                return parseClass(response, clazz);
            }
        }

        if (type instanceof ParameterizedType) {
            return parseParameterizedType(response, (ParameterizedType) type);
        } else if (type instanceof Class) {
            return parseClass(response, (Class<?>) type);
        } else {
            return parseType(response, type);
        }
    }

    private T parseClass(Response response, Class<?> rawType) throws Exception {
        if (rawType == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        if (rawType == String.class) {
            //noinspection unchecked
            return (T) body.string();
        } else if (rawType == JSONObject.class) {
            //noinspection unchecked
            return (T) new JSONObject(body.string());
        } else if (rawType == JSONArray.class) {
            //noinspection unchecked
            return (T) new JSONArray(body.string());
        } else {
            T t = Convert.fromJson(jsonReader, rawType);
            response.close();
            return t;
        }
    }

    private T parseType(Response response, Type type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        // 泛型格式如下： new JsonCallback<任意JavaBean>(this)
        T t = Convert.fromJson(jsonReader, type);
        response.close();
        return t;
    }

    private T parseParameterizedType(Response response, ParameterizedType type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        Type rawType = type.getRawType();                     // 泛型的实际类型
        Type typeArgument = type.getActualTypeArguments()[0]; // 泛型的参数
        if (rawType == OssResponse.class) {//OssResponse
            if (typeArgument == Void.class) {
                // 泛型格式如下： new JsonCallback<LzyResponse<Void>>(this)
                SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
                response.close();
                //noinspection unchecked
                return (T) simpleResponse.toResponse();
            } else {
                // 泛型格式如下： new JsonCallback<LzyResponse<内层JavaBean>>(this)
                OssResponse ossResponse = Convert.fromJson(jsonReader, type);
                response.close();
                int code = ossResponse.error_code;
                //这里的0是以下意思
                //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
                if (code == 0 || code == 200) {
                    //noinspection unchecked
                    return (T) ossResponse;
                }
                else if (code == 104) {
                    throw new IllegalStateException("用户授权信息无效");
                } else if (code == 105) {
                    throw new IllegalStateException("用户授权信息已过期");
                }else{
                    throw new IllegalStateException("失败");
                }
            }
        } else if (rawType == SmartResponse.class){//SmartResponse
            if (typeArgument == Void.class) {
                // 泛型格式如下： new JsonCallback<LzyResponse<Void>>(this)
                SimpleResponse simpleResponse = Convert.fromJson(jsonReader, SimpleResponse.class);
                response.close();
                //noinspection unchecked
                return (T) simpleResponse.toResponse();
            } else {
                // 泛型格式如下： new JsonCallback<LzyResponse<内层JavaBean>>(this)
                SmartResponse monCityResponse = Convert.fromJson(jsonReader, type);
                response.close();
                int code = monCityResponse.status;
                //这里的0是以下意思
                //一般来说服务器会和客户端约定一个数表示成功，其余的表示失败，这里根据实际情况修改
                if (code == 1 || code == 200) {
                    //noinspection unchecked
                    return (T) monCityResponse;
                }
//                else if (code == 104) {
//                    throw new IllegalStateException("用户授权信息无效");
//                } else if (code == 105) {
//                    throw new IllegalStateException("用户授权信息已过期");
//                }
                else if (code == -1) {
                    throw new IllegalStateException("失败");
                } else if (code == -2) {
                    throw new IllegalStateException("该任务已被抢拍~");
                } else if (code == 0) {
                    throw new IllegalStateException("系统故障");
                } else if (code == 2) {
                    throw new IllegalStateException("用户密码不正确");
                } else if (code == 3) {
                    throw new IllegalStateException("验证码为空");
                } else if (code == 4) {
                    throw new IllegalStateException("验证码有误，请重新输入");
                } else if (code == 5) {
                    throw new IllegalStateException("用户授权信息已过期,请重新登录");
                } else if (code == 6) {
                    throw new IllegalStateException("已经申请过地主，后台正在审核中~");
                } else if (code == 7) {
                    throw new IllegalStateException("未检索到输入名称点，抱歉该区域暂不开放新增！");
                } else if (code == 8) {
                    throw new IllegalStateException("绑定的手机号码未注册");
                } else if (code == 9) {
                    throw new IllegalStateException("已经是地主");
                } else if (code == 10) {
                    throw new IllegalStateException("等级没有达到申请地主等级需求");
                } else if (code == 11) {
                    throw new IllegalStateException("该地区已存在类似新增任务");
                } else if (code == 12) {
                    throw new IllegalStateException("该用户已被禁用");
                } else if (code == 99) {
                    throw new IllegalStateException("请求失败");
                } else if (code == 601) {
                    throw new IllegalStateException("账号禁用");
                } else if (code == 603) {
                    throw new IllegalStateException("手机号或验证吗不正确");
                } else if (code == 604) {
                    throw new IllegalStateException("登录超时");
                } else if (code == 605) {
                    throw new IllegalStateException("验证吗超时");
                } else {
                    //直接将服务端的错误信息抛出，onError中可以获取
                    SmartLog.i("错误代码：" + code + "，错误信息：" + monCityResponse.msg);
//                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + monCityResponse.msg);
                    throw new IllegalStateException(monCityResponse.msg);
                }
            }
        }else{
            // 泛型格式如下： new JsonCallback<外层BaseBean<内层JavaBean>>(this)
            T t = Convert.fromJson(jsonReader, type);
            response.close();
            return t;
        }
    }
}