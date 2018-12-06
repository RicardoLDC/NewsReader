package com.zxsoft.example.model.data.home;

import android.support.v7.widget.LinearLayoutManager;

import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.zxsoft.example.HomeApplication;
import com.zxsoft.example.R;
import com.zxsoft.example.api.ApiRequest;
import com.zxsoft.example.contract.HomeContract;
import com.zxsoft.example.model.bean.News;
import com.zxsoft.example.model.bean.Poi;
import com.zxsoft.example.model.bean.SearchData;
import com.zxsoft.example.model.http.OkGoHttpUtil;
import com.zxsoft.example.model.http.OssResponse;
import com.zxsoft.example.model.http.SmartResponse;
import com.zxsoft.example.model.http.callback.JsonCallback;
import com.zxsoft.example.ui.home.MainActivity;
import com.zxsoft.example.ui.home.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeRemoteDataSource implements HomeContract.HomeDataSourc{

    private static HomeRemoteDataSource INSTANCE;


    public static HomeRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HomeRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private HomeRemoteDataSource() {
    }

    /*
    @Override
    public void getWuZhen(String name, double lat, double lng, final HomeCallback callback) {
        ApiRequest.getInstance(HomeApplication.getContext()).getWuzhen(callback);

    }
    */

    @Override
    public void getNews(final HomeCallback callback,int id) {
        ApiRequest.getInstance(HomeApplication.getContext()).getNews(callback,id);
    }
}
