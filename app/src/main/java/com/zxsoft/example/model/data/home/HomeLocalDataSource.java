package com.zxsoft.example.model.data.home;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zxsoft.example.contract.HomeContract;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class HomeLocalDataSource implements HomeContract.HomeDataSourc{

    private static HomeLocalDataSource INSTANCE;


    private HomeLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }


    public static HomeLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new HomeLocalDataSource(context);
        }
        return INSTANCE;
    }

    /*
    @Override
    public void getWuZhen(String name, double lat, double lng,HomeCallback callback) {

    }
    */
    @Override
    public void getNews(HomeCallback callback,int id) {

    }
}
