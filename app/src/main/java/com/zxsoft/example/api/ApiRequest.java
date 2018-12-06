package com.zxsoft.example.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zxsoft.example.HomeApplication;
import com.zxsoft.example.SqliteDataBase.DataBaseHelper;
import com.zxsoft.example.contract.HomeContract;
import com.zxsoft.example.model.bean.News;
import com.zxsoft.example.model.bean.Poi;
import com.zxsoft.example.model.bean.SearchData;
import com.zxsoft.example.model.http.OkGoHttpUtil;
import com.zxsoft.example.model.http.OssResponse;
import com.zxsoft.example.model.http.SmartResponse;
import com.zxsoft.example.model.http.callback.JsonCallback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.zxsoft.example.HomeApplication.getContext;

public class ApiRequest {
    private static ApiRequest mInstance;
    private Context mContext;
    private DataBaseHelper dbHelper=new DataBaseHelper(getContext(),"NewsBase.db",null,3);

    public ApiRequest(Context context) {
        this.mContext = context;
    }

    /**
     * 单例模式，双重锁，保证唯一性
     *
     * @return
     */
    public static ApiRequest getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ApiRequest.class) {
                if (mInstance == null) {
                    mInstance = new ApiRequest(context);
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    /**
     * 模拟数据
     *
     * @param
     * @param callback
     */

    /**
     * 模拟数据
     *
     * @param
     * @param callback
     */
    public void getNews(final HomeContract.HomeDataSourc.HomeCallback callback, final int id) {
        HttpParams params = new HttpParams();
//        String url="http://v.juhe.cn/toutiao/index?type=top&key=c1f646b699352b695a35dc81a52fc996";
        String url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=top";
        switch(id) {
            case 0:
//              url="http://v.juhe.cn/toutiao/index?type=top&key=c1f646b699352b695a35dc81a52fc996";
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=top";
                break;
            case 1:
//                url="http://v.juhe.cn/toutiao/index?type=shehui&key=c1f646b699352b695a35dc81a52fc996";
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=shehui";
                break;
            case 2:
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=guonei";
//                url="http://v.juhe.cn/toutiao/index?type=guonei&key=c1f646b699352b695a35dc81a52fc996";
                break;
            case 3:
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=guoji";
//                url="http://v.juhe.cn/toutiao/index?type=guoji&key=c1f646b699352b695a35dc81a52fc996";
                break;
            case 4:
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=yule";
//                url="http://v.juhe.cn/toutiao/index?type=yule&key=c1f646b699352b695a35dc81a52fc996";
                break;
            case 5:
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=tiyu";
//                url="http://v.juhe.cn/toutiao/index?type=tiyu&key=c1f646b699352b695a35dc81a52fc996";
                break;
            case 6:
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=junshi";
//                url="http://v.juhe.cn/toutiao/index?type=junshi&key=c1f646b699352b695a35dc81a52fc996";
                break;
            case 7:
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=keji";
//                url="http://v.juhe.cn/toutiao/index?type=keji&key=c1f646b699352b695a35dc81a52fc996";
                break;
            case 8:
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=caijing";
//                url="http://v.juhe.cn/toutiao/index?type=caijing&key=c1f646b699352b695a35dc81a52fc996";
                break;
            case 9:
                url="http://api.avatardata.cn/TouTiao/Query?key=73d034daceb54ee18c922e7ca2a68ca5&type=shishang";
//                url="http://v.juhe.cn/toutiao/index?type=shishang&key=c1f646b699352b695a35dc81a52fc996";
                break;
            default:
                break;
        }
        OkGoHttpUtil.get(url, params, hashCode(), new JsonCallback<OssResponse<News>>(getContext()) {
            @Override
            public void onSuccess(Response<OssResponse<News>> response) {
                List<News.DataBean> strings=new ArrayList<>();
                
                for (News.DataBean dataBean:response.body().result.getData()){
                    strings.add(dataBean);
                }
                //增加
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                for(int i=0;i<strings.size();i++) {
                    values.put("title", strings.get(i).getTitle());
                    values.put("uniqueKey", strings.get(i).getUniquekey());
                    values.put("author_name", strings.get(i).getAuthor_name());
                    values.put("category", strings.get(i).getCategory());
                    values.put("date", strings.get(i).getDate());
                    values.put("url", strings.get(i).getUrl());
                    values.put("thumbnail_pic_s", strings.get(i).getThumbnail_pic_s());
                    values.put("thumbnail_pic_s02", strings.get(i).getThumbnail_pic_s02());
                    values.put("thumbnail_pic_s03", strings.get(i).getThumbnail_pic_s03());
                    db.delete("PostMessage","uniquekey like ?",new String[]{strings.get(i).getUniquekey()});
                    db.replace("historyNews",null,values);
                    values.clear();
                }
                
                
                
                //分界线
                callback.getNewsSuccess(strings,id);
            }

            @Override
            public void onError(Response<OssResponse<News>> response) {
                super.onError(response);
                callback.error(msg);
            }
        });

    }

}
